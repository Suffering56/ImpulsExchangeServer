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

public class FtpDownload extends Thread {

    public FtpDownload(JProgressBar progressBar, String depNum) throws IOException {
        this.options = new Options();
        this.progressBar = progressBar;
        this.depNum = depNum;
        swndFullPath = new File("F:\\DealerDataExchange\\" + depNum + "\\swnd5.arc");
    }

    @Override
    public void run() {
        try {
            progressBar.setString(null);
            progressBar.setValue(0);
            boolean onUpdate = downloadDetails();

            if (onUpdate == true) {
                downloadFile();
                progressBar.setValue(100);
                progressBar.setString("Загружено");
            } else {
                progressBar.setValue(100);
                progressBar.setString("Нет новых данных");
            }

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Отдел №" + depNum + ". Другая ошибка (FTP).\r\nКод ошибки: " + ex.toString());
            progressBar.setValue(0);
            progressBar.setString("Ошибка");

        } catch (InterruptedException | IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл обмена отсутствует, либо указан неверный путь.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpProtocolException")) {
                errorMsg = "Ошибка FTP. Отсутствует каталог для отдела №" + depNum + " на FTP-сервере"
                        + "\r\nЛибо отсутствует файл деталей обмена (details.txt)"; 
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }
            JOptionPane.showMessageDialog(null, "Отдел №" + depNum + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());                              //Вывод уведомления об ошибке на экран
            progressBar.setValue(0);
            progressBar.setString("Ошибка");
        }
    }

    private boolean downloadDetails() throws MalformedURLException, IOException {
//        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
//                + ":/" + options.getDepartmentNumber() + "/info.txt");

        URL ur = new URL("ftp://mailru5o_login:im699000pass@5.101.156.8:/" + depNum + "/info.txt"); //oldFTPServerURL
        URLConnection urlConnection = ur.openConnection();
        System.out.println("ContentLength[" + depNum + "] = " + urlConnection.getContentLength());

        if (urlConnection.getContentLength() != 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            getDetails(in);
            in.close();
            return true;
        } else {                                                                //Файл info.txt пуст --> отсутствует информация обмена
            return false;
        }
    }

    private void getDetails(BufferedReader in) throws IOException {
        String line;
        detailsList = new LinkedList();
        while ((line = in.readLine()) != null) {
            detailsList.add(line);
        }
    }

    private void downloadFile() throws MalformedURLException, IOException, InterruptedException {
//         URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
//                + ":/" + options.getDepartmentNumber() + "/" + options.getSwndFileName());

        URL ur = new URL("ftp://mailru5o_login:im699000pass@5.101.156.8:/" + depNum + "/swnd5.arc"); //oldFTPServerURL
        URLConnection urlConnection = ur.openConnection();

        BufferedInputStream getFileInputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedOutputStream localFileOutputStream = new BufferedOutputStream(new FileOutputStream(swndFullPath));

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
    private final File swndFullPath;
    private final String depNum;
    private Department department;// = new Department(depNum, DepartmentStatus.Update);
}
