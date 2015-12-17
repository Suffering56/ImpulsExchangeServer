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

public class FtpDownload extends Thread {

    public FtpDownload(Options options, JProgressBar progressBar,
            JToggleButton toExchangeBtn, ActiveDepartment activeDepartment) throws IOException {
        this.options = options;
        this.progressBar = progressBar;
        this.toExchangeBtn = toExchangeBtn;
        this.activeDepartment = activeDepartment;

        department = activeDepartment.getDepartmentNumber();
        downloadPath = new File(options.getDownloadPath() + "\\" + department + "\\" + options.getExchangeFileName());
    }

    @Override
    public void run() {
        try {
            progressBar.setString(null);
            progressBar.setValue(0);
            toExchangeBtn.setEnabled(false);
            toExchangeBtn.setSelected(false);
            boolean onUpdate = downloadDetails();

            if (onUpdate == true) {                                             //Если информация активного отдела была обновлена, то ...
                downloadFile();                                                 //... загружаем swnd5.arc
                progressBar.setValue(100);
                activeDepartment.setDetailsList(detailsList);
                progressBar.setString("Загружено");
                toExchangeBtn.setEnabled(true);
            } else {                                                            //если нет новых данныхх
                progressBar.setValue(100);
                progressBar.setString("Нет новых данных");
                toExchangeBtn.setEnabled(false);
            }

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Отдел №" + department + ". Другая ошибка (FTP).\r\nКод ошибки: " + ex.toString());
            progressBar.setValue(100);                                           //Возможно стоит ставить 0 вместо 100, для отладки
            progressBar.setString("Ошибка");
            toExchangeBtn.setEnabled(false);

        } catch (InterruptedException | IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл обмена отсутствует, либо указан неверный путь.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpProtocolException")) {
                errorMsg = "Ошибка FTP. Отсутствует каталог для отдела №" + department + " на FTP-сервере"
                        + "\r\nЛибо отсутствует файл деталей обмена (details.txt)";                     //
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }
            JOptionPane.showMessageDialog(null, "Отдел №" + department + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());                              //Вывод уведомления об ошибке на экран
            progressBar.setValue(100);        //Возможно стоит ставить 0 вместо 100, для отладки
            progressBar.setString("Ошибка");
            toExchangeBtn.setEnabled(false);
        }
    }

    private boolean downloadDetails() throws MalformedURLException, IOException {
        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                + ":/" + department + "/orders.txt");
        URLConnection urlConnection = ur.openConnection();

        if (urlConnection.getContentLength() != 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            extractDetails(in);
            in.close();
            return true;
        } else {                                 //Файл orders.txt пуст --> Нет новых данных
            return false;
        }
    }

    private void extractDetails(BufferedReader in) throws IOException {
        String line;
        detailsList = new LinkedList();
        while ((line = in.readLine()) != null) {
            detailsList.add(line);
        }
    }

    private void downloadFile() throws MalformedURLException, IOException, InterruptedException {
        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                + ":/" + department + "/" + options.getExchangeFileName());
        URLConnection urlConnection = ur.openConnection();

        BufferedInputStream getFileInputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedOutputStream localFileOutputStream = new BufferedOutputStream(new FileOutputStream(downloadPath));

        int line;
        int i = 0;
        int progressValue;
        int oldProgressValue = 0;
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
    }

    private LinkedList<String> detailsList;
    private final Options options;
    private final JProgressBar progressBar;
    private final JToggleButton toExchangeBtn;
    private final File downloadPath;
    private final String department;
    private final ActiveDepartment activeDepartment;
}
