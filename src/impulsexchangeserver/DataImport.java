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
import java.nio.file.Files;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import org.apache.commons.net.ftp.FTPClient;

public class DataImport extends Thread {

    public DataImport(Options options, JProgressBar progressBar,
            JToggleButton toExchangeBtn, ActiveDepartment activeDepartment) throws IOException {
        this.options = options;                     //передаем параметры программы
        this.progressBar = progressBar;             //передаем активный progressBar - для плавного изменения его состояния в процессе импорта
        this.toExchangeBtn = toExchangeBtn;         //передаем кнопку "на обмен", чтобы передать ей нужное состояние положение по окончанию импорта
        this.activeDepartment = activeDepartment;   //передаем информацию отдела (номер, список заказов)

        departmentNumber = activeDepartment.getDepartmentNumber();
        downloadPath = new File(options.getDownloadPath() + "\\"
                + departmentNumber + "\\" + options.getExchangeFileName());     //полный путь к swnd5.arc (включая имя и расширение)
    }

    @Override
    public void run() {
        try {
            progressBar.setString(null);
            progressBar.setValue(0);
            toExchangeBtn.setEnabled(false);
            toExchangeBtn.setSelected(false);

            ftpDirectoryExistCheck();                                           //проверяем наличие папки "номер_отдела" на FTP сервере
            localDirectoryExistCheck();
            boolean isUpdate = extractDetails();                                //загружаем информацию о новых заказах

            if (isUpdate == true) {                                             //если новые заказы есть:
                downloadFile();                                                 //загружаем swnd5.arc
                activeDepartment.setDetailsList(detailsList);                   //прикрепляем список новых заказов к отделу
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

            JOptionPane.showMessageDialog(null, "Отдел №" + departmentNumber + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());
            progressBar.setString("Ошибка");
            toExchangeBtn.setEnabled(false);
        } finally {
            progressBar.setValue(100);
        }
    }

    private void ftpDirectoryExistCheck() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(options.getFtpAddress());
        ftpClient.login(options.getFtpLogin(), options.getFtpPass());
        ftpClient.enterLocalPassiveMode();
        boolean exist = ftpClient.changeWorkingDirectory(departmentNumber);
        if (!exist) {
            ftpClient.makeDirectory(departmentNumber);
            ftpClient.changeWorkingDirectory(departmentNumber);
        }
    }

    private void localDirectoryExistCheck() throws IOException {
        File directory = new File(options.getDownloadPath() + "\\"
                + departmentNumber); 
        if (!Files.exists(directory.toPath())) {
            Files.createDirectory(directory.toPath());
        }
    }

    private boolean extractDetails() throws IOException {
        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                + ":/" + departmentNumber + "/orders.txt");
        URLConnection urlConnection = ur.openConnection();

        if (urlConnection.getContentLength() > 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            detailsList = new LinkedList();
            while ((line = in.readLine()) != null) {
                detailsList.add(line);
            }
            in.close();
            return true;
        } else {
            return false;
        }
    }

    private void downloadFile() throws MalformedURLException, IOException, InterruptedException {
        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                + ":/" + departmentNumber + "/" + options.getExchangeFileName());
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
            JOptionPane.showMessageDialog(null, "Отдел №" + departmentNumber + ". Отсутствует файл обмена (swnd5.arc) на FTP-сервере");
        }
    }

    private LinkedList<String> detailsList;
    private final Options options;
    private final JProgressBar progressBar;
    private final JToggleButton toExchangeBtn;
    private final File downloadPath;
    private final String departmentNumber;
    private final ActiveDepartment activeDepartment;
}
