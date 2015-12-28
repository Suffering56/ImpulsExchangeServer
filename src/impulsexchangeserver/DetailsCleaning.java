package impulsexchangeserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;

public class DetailsCleaning extends Thread {

    public DetailsCleaning(Options options, ActiveDepartment activeDepartment) {
        this.options = options;
        this.activeDepartment = activeDepartment;
        departmentName = activeDepartment.getDepartmentName();
    }

    @Override
    public void run() {
        try {
            updateDetails();
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Отдел №" + departmentName + ". Другая ошибка (FTP).\r\nКод ошибки: " + ex.toString());
        } catch (IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл обмена отсутствует, либо указан неверный путь.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpProtocolException")) {
                errorMsg = "Ошибка FTP. Отсутствует каталог для отдела №" + departmentName + " на FTP-сервере"
                        + "\r\nЛибо отсутствует файл деталей обмена (orders.txt)";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }
            JOptionPane.showMessageDialog(null, "Отдел №" + departmentName + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());                              //Вывод уведомления об ошибке на экран
        }
    }

    private void updateDetails() throws MalformedURLException, IOException {
        URL ur = new URL("ftp://" + options.getFtpLogin() + ":" + options.getFtpPass() + "@" + options.getFtpAddress()
                + ":/" + departmentName + "/orders.txt");
        URLConnection urlConnection = ur.openConnection();
        BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
        
        if (activeDepartment.getDetailsList().isEmpty()) {                          //Если residualList (список оставшихся заказов) пустой...
            out.write(("")                                                          //... (т.е. были отмечены на удаление ВСЕ заказы текущего отдела)...
                    .getBytes());                                                   //... то удаляем все заказы из orders.txt
        } else {                                                                    //... ИНАЧЕ (если были отмечены НЕ ВСЕ заказы)...
            for (int i = 0; i < activeDepartment.getDetailsList().size(); i++) {
                out.write((activeDepartment.getDetailsList().get(i) + "\r\n")       //... очищаем orders.txt (не в этой строке - оно само)
                        .getBytes());                                               //... записываем ОСТАВШИЕСЯ заказы в orders.txt
            }                                                                       
        }
        out.close();
    }

    private final Options options;
    private final ActiveDepartment activeDepartment;
    private final String departmentName;
}
