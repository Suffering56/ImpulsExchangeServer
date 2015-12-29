package impulsexchangeserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

public class DataImport extends Thread {

    public DataImport(JProgressBar progressBar, JToggleButton toExchangeBtn,
            ActiveDepartment activeDepartment) throws IOException {
        this.progressBar = progressBar;             //передаем активный progressBar - для плавного изменения его состояния в процессе импорта
        this.toExchangeBtn = toExchangeBtn;         //передаем кнопку "на обмен", чтобы передать ей нужное состояние положение по окончанию импорта
        this.activeDepartment = activeDepartment;   //передаем информацию отдела (номер, список заказов)

        departmentName = activeDepartment.getDepartmentName();
        downloadPath = new File(System.getProperty("user.dir") + "\\"
                + departmentName + "\\" + Options.exchangeFileName);            //полный путь к swnd5.arc (включая имя и расширение)
    }

    @Override
    public void run() {
        try {
            progressBar.setString(null);
            progressBar.setValue(0);
            toExchangeBtn.setEnabled(false);
            toExchangeBtn.setSelected(false);

            DirectoryHandler.checkFtpDirectory(departmentName);                 //проверяем наличие папки "номер_отдела" на FTP сервере
            DirectoryHandler.checkLocalDirectory(departmentName);
            
            boolean isUpdate = downloadOrders();                                //загружаем информацию о новых заказах

            if (isUpdate == true) {                                             //если новые заказы есть:
                downloadFile();                                                 //загружаем swnd5.arc
                activeDepartment.setOrdersList(ordersList);                     //прикрепляем список новых заказов к отделу
                toExchangeBtn.setEnabled(true);
                progressBar.setString("Загружено");
            } else {                                                            //если нет новых заказов:
                toExchangeBtn.setEnabled(false);                                //блокируем кнопку "на обмен"
                progressBar.setString("Нет новых данных");
            }

        } catch (InterruptedException | IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл обмена отсутствует, либо указан неверный путь.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }

            JOptionPane.showMessageDialog(null, "Отдел №" + departmentName + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());
            progressBar.setString("Ошибка");
            toExchangeBtn.setEnabled(false);
        } finally {
            progressBar.setValue(100);
        }
    }

    private boolean downloadOrders() throws IOException {
        URL ur = new URL("ftp://" + Options.ftpLogin + ":" + Options.ftpPass + "@" + Options.ftpAddress
                + ":/" + departmentName + "/orders.txt");
        URLConnection urlConnection = ur.openConnection();

        if (urlConnection.getContentLength() > 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            ordersList = new LinkedList();
            while ((line = in.readLine()) != null) {
                ordersList.add(line);
            }
            in.close();
            return true;
        } else {
            return false;
        }
    }

    private void downloadFile() throws MalformedURLException, IOException, InterruptedException {
        URL ur = new URL("ftp://" + Options.ftpLogin + ":" + Options.ftpPass + "@" + Options.ftpAddress
                + ":/" + departmentName + "/" + Options.exchangeFileName);
        URLConnection urlConnection = ur.openConnection();

        if (urlConnection.getContentLength() > 0) {
            BufferedInputStream getFileInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream localFileOutputStream = new BufferedOutputStream(new FileOutputStream(downloadPath));

            int line, progressValue;
            int i = 0, oldProgressValue = 0;
            double onePercent = urlConnection.getContentLength() / 100.0;

            while ((line = getFileInputStream.read()) != -1) {
                i++;
                progressValue = (int) (i / onePercent);
                localFileOutputStream.write(line);

                if ((progressValue != oldProgressValue) && (progressValue != 100)) {
                    Thread.sleep(0);
                    progressBar.setValue(progressValue);
                }
                oldProgressValue = progressValue;
            }

            getFileInputStream.close();
            localFileOutputStream.close();
        } else {
            progressBar.setString("Ошибка");
            toExchangeBtn.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Отдел №" + departmentName + ". Отсутствует файл обмена (swnd5.arc) на FTP-сервере");
        }
    }

    private LinkedList<String> ordersList;
    private final JProgressBar progressBar;
    private final JToggleButton toExchangeBtn;
    private final File downloadPath;
    private final String departmentName;
    private final ActiveDepartment activeDepartment;
}
