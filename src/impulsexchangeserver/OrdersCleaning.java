package impulsexchangeserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class OrdersCleaning extends Thread {

    public OrdersCleaning(ActiveDepartment activeDepartmentData, LinkedList <String> removeList) {
        this.activeDepartmentData = activeDepartmentData;
        this.removeList = removeList;
        departmentName = activeDepartmentData.getDepartmentName();
    }

    @Override
    public void run() {
        try {
            DirectoryHandler.checkFtpDirectory(departmentName);                 //проверяем наличие папки "номер_отдела" на FTP сервере
            updateDetails();
            updateArchive();

        } catch (IOException ex) {
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
            JOptionPane.showMessageDialog(null, "Отдел №" + departmentName + ". " + errorMsg + "\r\nКод ошибки: " + ex.toString());                              //Вывод уведомления об ошибке на экран
        }
    }

    private void updateDetails() throws MalformedURLException, IOException {
        URL ur = new URL("ftp://" + Options.ftpLogin + ":" + Options.ftpPass + "@" + Options.ftpAddress
                + ":/" + departmentName + "/orders.txt");
        URLConnection urlConnection = ur.openConnection();
        BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

        if (activeDepartmentData.getOrdersList().isEmpty()) {
            out.write(("")
                    .getBytes());                                                   //Очищаем orders.txt
        } else {
            for (int i = 0; i < activeDepartmentData.getOrdersList().size(); i++) {
                out.write((activeDepartmentData.getOrdersList().get(i) + "\r\n")    //Очищаем orders.txt (не в этой строке - оно само)...
                        .getBytes());                                               //... и записываем ОСТАВШИЕСЯ заказы в orders.txt
            }
        }
        out.close();
    }

    private void updateArchive() throws FileNotFoundException, IOException {
        File archive = new File(System.getProperty("user.dir") + "\\" + departmentName + "\\archive.bin");
        if (!Files.exists(archive.toPath())) {
            Files.createFile(archive.toPath());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(archive)));
        LinkedList<String> existingArchiveList = getExistingOrders(in);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(archive));
        for (String tempList : removeList) {
            out.write((tempList + "\r\n").getBytes());
        }
        for (String tempList : existingArchiveList) {
            out.write((tempList + "\r\n").getBytes());
        }
        out.close();
        removeList.clear();
    }

    private LinkedList<String> getExistingOrders(BufferedReader in) throws IOException {
        String line;
        LinkedList<String> existingOrdersList = new LinkedList();
        while ((line = in.readLine()) != null) {
            existingOrdersList.add(line);
        }
        in.close();
        return existingOrdersList;
    }

    private final ActiveDepartment activeDepartmentData;
    private final LinkedList <String> removeList;
    private final String departmentName;
}
