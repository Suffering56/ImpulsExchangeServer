package impulsexchangeserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;

public class Options {

    public Options() throws IOException {
        this.departmentsList = new DefaultListModel();
        firstStart();
    }

    public void setOptions() throws IOException {
        //String departmentNumberWQuery = "REG ADD HKCU\\Software\\ImpulsDataExchange /v departmentNumber /t REG_SZ /d " + departmentNumber + " /f"; 
//        String swndFileFullPathWQuery = "REG ADD HKCU\\Software\\ImpulsDataExchange /v swndFileFullPath /t REG_SZ /d " + swndFileFullPath + " /f";
//        String swndFileNameWQuery = "REG ADD HKCU\\Software\\ImpulsDataExchange /v swndFileName /t REG_SZ /d " + swndFileName + " /f";
        String ftpAddressWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpAddress /t REG_SZ /d " + ftpAddress + " /f";
        String ftpLoginWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpLogin /t REG_SZ /d " + ftpLogin + " /f";
        String ftpPassWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpPass /t REG_SZ /d " + ftpPass + " /f";
        
//        String ftpAddressWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpAddress /t REG_SZ /d " + ftpAddress + " /f";
//        String ftpLoginWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpLogin /t REG_SZ /d " + ftpLogin + " /f";
//        String ftpPassWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpPass /t REG_SZ /d " + ftpPass + " /f";

        //Runtime.getRuntime().exec(departmentNumberWQuery);
//        Runtime.getRuntime().exec(swndFileFullPathWQuery);
//        Runtime.getRuntime().exec(swndFileNameWQuery);
        Runtime.getRuntime().exec(ftpAddressWQuery);
        Runtime.getRuntime().exec(ftpLoginWQuery);
        Runtime.getRuntime().exec(ftpPassWQuery);
    }

    public void getOptions() throws IOException {                                           //получение настроек из реестра
        String dataOptionsQuery[] = {depNum, filePath, fileName, address, login, password}; //инициализация запросов к реестру
        LinkedList<String> optionsList = new LinkedList();
        int nullOptionsCounter = 0;

        for (String query : dataOptionsQuery) {
            Process process = Runtime.getRuntime().exec(query);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));    //чтение данных реестра
            String line;
            String parseLine = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains("REG_SZ")) {                                                              //извлечение нужной строки потока реестра
                    parseLine = line.trim();                                                                //PS: там почему-то не в одной строке все хранится
                }
            }
            reader.close();

            Matcher m = p.matcher(parseLine);
            if (m.matches()) {
                optionsList.add(m.group(1));                                                                //извлечение нужного значения ключа реестра
            } else {                                                                        //либо если ключ(значение) отсутствует (не соответствует шаблону)
                optionsList.add("");                                                                        //извлечение пустого значения (для избежания ошибки)             
                nullOptionsCounter++;
            }
        }

        if (nullOptionsCounter == 6) {
            firstStart();                                                                   //загрузка значений по-умолчанию при первом запуске программы
            setOptions();
        } else {
            importOptionsIntoProgramm(optionsList);                                         //запись извлеченных параметров в класс Options
        }
    }

    private void importOptionsIntoProgramm(LinkedList<String> optionsList) {
        //departmentNumber = optionsList.get(0);
//        swndFileFullPath = optionsList.get(1);
//        swndFileName = optionsList.get(2);
        ftpAddress = optionsList.get(3);
        ftpLogin = optionsList.get(4);
        ftpPass = optionsList.get(5);
    }

    private void firstStart() {
        ftpAddress = "5.101.156.8";
        ftpLogin = "mailru5o_login";
        ftpPass = "im699000pass";
        
        departmentsList.add(0, "68");
        departmentsList.add(1, "71");
        departmentsList.add(2, "73");
        departmentsList.add(3, "74");
        departmentsList.add(4, "79");

        exchangePath = "C:\\";
        exchangeFileName = "swnd5.arc";
        downloadPath = "C:\\";
    }

    public String getFtpLogin() {
        return ftpLogin;
    }

    public void setFtpLogin(String ftpLogin) {
        this.ftpLogin = ftpLogin;
    }

    public String getFtpPass() {
        return ftpPass;
    }

    public void setFtpPass(String ftpPass) {
        this.ftpPass = ftpPass;
    }

    public String getFtpAddress() {
        return ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public DefaultListModel getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(DefaultListModel departmentsList) {
        this.departmentsList = departmentsList;
    }

    public String getExchangePath() {
        return exchangePath;
    }

    public void setExchangePath(String exchangePath) {
        this.exchangePath = exchangePath;
    }

    public String getExchangeFileName() {
        return exchangeFileName;
    }

    public void setExchangeFileName(String exchangeFileName) {
        this.exchangeFileName = exchangeFileName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    private String ftpLogin;
    private String ftpPass;
    private String ftpAddress;

    private DefaultListModel departmentsList;
    private String exchangePath;
    private String exchangeFileName;
    private String downloadPath;

    private final Pattern p = Pattern.compile("\\w+\\p{Space}+REG_SZ\\p{Space}+(.+)");                      //Шаблон для извлечения параметра ключа реестра
    private final String depNum = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v departmentNumber";
    private final String filePath = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v swndFileFullPath";
    private final String fileName = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v swndFileName";
    private final String address = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v ftpAddress";
    private final String login = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v ftpLogin";
    private final String password = "REG QUERY HKCU\\Software\\ImpulsDataExchange /v ftpPass";
}
