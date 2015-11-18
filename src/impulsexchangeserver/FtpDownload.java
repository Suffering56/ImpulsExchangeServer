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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class FtpDownload extends Thread {

    public FtpDownload(JProgressBar progressBar, String depNum) throws IOException {
        this.options = new Options();
        this.progressBar = progressBar;
        this.depNum = depNum;
        swndFullPath = new File("F:\\DealerDataExchange\\" + depNum + "\\swnd5.arc");
    }
    

//    public FtpDownload(JProgressBar sendProgress, DefaultListModel dm, String depNum) {
//        this.sendProgress = sendProgress;
//        this.dm = dm;
//        this.depNum = depNum;
//        //this.options = options;
//        //downloadFile = new File(options.getSwndFileFullPath());
//    }

    @Override
    public void run() {
        try {
            downloadFile();
            
//            uploadInfo();                                                                   //загрузка информации о заказах на сервер
//
            progressBar.setValue(100);                 //установка значения 100% на прогресс баре
//            JOptionPane.showMessageDialog(null, "Файл успешно загружен на сервер");         //вывод сообщения об успешной загрузке
//            sendProgress.setVisible(false);                                                 //прячем прогресс бар
//            dm.clear();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Другая ошибка (FTP): " + ex.toString()); //Вывод уведомления об ошибке на экран 
            progressBar.setVisible(false);

        } catch (InterruptedException | IOException ex) { //
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл обмена отсутствует, либо указан неверный к нему путь.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpProtocolException")) {
                errorMsg = "Ошибка FTP. Создайте папку с номером вашего отдела на FTP сервере в каталоге /DealerDataExchange";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }
            JOptionPane.showMessageDialog(null, errorMsg + " Ex: " + ex.toString());                              //Вывод уведомления об ошибке на экран
            progressBar.setVisible(false);
        }
    }

    private void downloadFile() throws MalformedURLException, IOException, InterruptedException {
        URL ur = new URL("ftp://mailru5o_login:im699000pass@5.101.156.8:/" + depNum + "/swnd5.arc"); //oldFTPServerURL
//        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
//                + ":/" + options.getDepartmentNumber() + "/" + options.getSwndFileName());
        URLConnection urlConnection = ur.openConnection();

        
        //BufferedInputStream localFileInputStream = new BufferedInputStream(new FileInputStream(sendFile));
        BufferedInputStream getFileInputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedOutputStream localFileOutputStream = new BufferedOutputStream(new FileOutputStream(swndFullPath));
        
        //BufferedOutputStream sendFileOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
        
        int line;
        int i = 0;
        int progressValue;
        int oldProgressValue = 0;
        System.out.println("ContentLength[" + depNum + "] = " + urlConnection.getContentLength());
        double onePercent = urlConnection.getContentLength() / 100.0;
//        
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

    private void uploadInfo() throws MalformedURLException, IOException {
        if (dm.getSize() != 0) {
            URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                    + ":/" + options.getDepartmentNumber() + "/info.txt");
            URLConnection urlConnection = ur.openConnection();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                getPrevInfo(in);
                in.close();
            } catch (IOException ex) {
            } finally {
                urlConnection = ur.openConnection();
                BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                for (int i = 0; i < dm.getSize(); i++) {
                    out.write((options.getDepartmentNumber() + "/" + dm.getElementAt(i).toString() + "\r\n").getBytes());
                }
                out.close();
            }
        }
    }

    private void getPrevInfo(BufferedReader in) throws IOException {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                line = line.replace(options.getDepartmentNumber() + "/", "");
                dm.addElement(line);
            }
            in.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Создайте в каталоге с номером вашего отдела файл info.txt. Ex: " + ex.toString());
            in.close();
        }
    }

    private final DefaultListModel dm = new DefaultListModel();
    private final Options options;
    private final JProgressBar progressBar;
    private final File swndFullPath;
    private String depNum;
}
