package impulsexchangeserver;

import java.util.LinkedList;

public class DetailsCleaning extends Thread {

    public DetailsCleaning(ActiveDepartment activeDepartment, String status) {
        this.status = status;
        this.activeDepartment = activeDepartment;
        this.department = activeDepartment.getDepartmentNumber();
        this.cleaningList = activeDepartment.getDetailsList();
    }
//    this.options  = options;

    
//    downloadPath  = new File(options.getDownloadPath() + "\\" + department + "\\" + options.getExchangeFileName());

    @Override
    public void run() {
//        try {

//            boolean onUpdate = downloadDetails();
//        } catch (MalformedURLException ex) {
//            JOptionPane.showMessageDialog(null, "Отдел №" + department + ". Другая ошибка (FTP).\r\nКод ошибки: " + ex.toString());
//        } catch (InterruptedException | IOException ex) {
//            String errorMsg;
//            if (ex.toString().contains("FileNotFoundException")) {
//                errorMsg = "Файл обмена отсутствует, либо указан неверный путь.";
//            } else if (ex.toString().contains("NoRouteToHostException")) {
//                errorMsg = "Ошибка соединения с интернетом.";
//            } else if (ex.toString().contains("FtpProtocolException")) {
//                errorMsg = "Ошибка FTP. Отсутствует каталог для отдела №" + department + " на FTP-сервере"
//                        + "\r\nЛибо отсутствует файл деталей обмена (details.txt)";                     //
//            } else if (ex.toString().contains("FtpLoginException")) {
//                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
//            } else if (ex.toString().contains("UnknownHostException")) {
//                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
//            } else {
//                errorMsg = "Другая ошибка.";
//            }
//            JOptionPane.showMessageDialog(null, "Отдел №" + department + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());                              //Вывод уведомления об ошибке на экран
//        }
    }

//        private boolean downloadDetails() throws MalformedURLException, IOException {
//        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
//                + ":/" + department + "/info.txt");
//        URLConnection urlConnection = ur.openConnection();
//
//        if (urlConnection.getContentLength() != 0) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            extractDetails(in);
//            in.close();
//            return true;
//        } else {        //Файл info.txt пуст --> отсутствует информация обмена
//            return false;
//        }
//    }
//
//    private void extractDetails(BufferedReader in) throws IOException {
//        String line;
//        detailsList = new LinkedList();
//        while ((line = in.readLine()) != null) {
//            detailsList.add(line);
//        }
//    }
    
    private final ActiveDepartment activeDepartment;
    private final String status;
    private final LinkedList<String> cleaningList;
    private final String department;
}
