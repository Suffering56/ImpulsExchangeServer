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
    }

    public void getOptions() throws IOException {                                       //получение настроек из реестра
        String optionsReadQuery[] = {ftpAddressReadQuery, ftpLoginReadQuery, ftpPasswordReadQuery, departmentsListReadQuery,
            exchangePathReadQuery, exchangeFileNameReadQuery, downloadPathReadQuery};   //инициализация запросов к реестру
        LinkedList<String> optionsList = new LinkedList();
        int nullOptionsCounter = 0;

        for (String query : optionsReadQuery) {
            Process process = Runtime.getRuntime().exec(query);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));    //чтение данных реестра
            String line;
            String parseLine = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains("REG_SZ")) {             //извлечение нужной строки потока реестра
                    parseLine = line.trim();               //PS: там почему-то не в одной строке все хранится
                }
            }
            reader.close();
            process.destroy();

            Matcher m = p.matcher(parseLine);
            if (m.matches()) {
                optionsList.add(m.group(1));               //извлечение нужного значения ключа реестра
            } else {                                       //либо если ключ(значение) отсутствует (не соответствует шаблону)...
                optionsList.add("");                       //...извлечение пустого значения (для избежания ошибки)             
                nullOptionsCounter++;
            }
        }

        if (nullOptionsCounter == 7) {
            firstStart();                                  //загрузка значений по-умолчанию при первом запуске программы
            setOptions();
        } else {
            importOptionsIntoProgramm(optionsList);        //запись извлеченных параметров в класс Options
        }
    }

    private void firstStart() {
        ftpAddress = "5.101.156.8";
        ftpLogin = "mailru5o_login";
        ftpPass = "im699000pass";
        exchangePath = "C:\\";
        exchangeFileName = "swnd5.arc";
        downloadPath = "C:\\DealerDataExchange";
    }

    private void importOptionsIntoProgramm(LinkedList<String> optionsList) {
        ftpAddress = optionsList.get(0);
        ftpLogin = optionsList.get(1);
        ftpPass = optionsList.get(2);
        departmentsList = strToList(optionsList.get(3));
        exchangePath = optionsList.get(4);
        exchangeFileName = optionsList.get(5);
        downloadPath = optionsList.get(6);
    }

    private DefaultListModel strToList(String str) {
        DefaultListModel list = new DefaultListModel();
        String tempStr = "";
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != '_') {
                tempStr = tempStr + ch[i];
            } else {
                if (!tempStr.isEmpty()) {           //если список отделов пуст
                    list.addElement(tempStr);
                }
                tempStr = "";
            }
        }
        return list;
    }

    private String listToStr(DefaultListModel list) {
        String str = "";
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                str = str + list.get(i) + "_";
            }
            System.out.println("str = " + str);
            return str;
        } else {
            return "_";
        }
    }

    public void setOptions() throws IOException {
        String ftpAddressWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpAddress /t REG_SZ /d " + ftpAddress + " /f";
        String ftpLoginWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpLogin /t REG_SZ /d " + ftpLogin + " /f";
        String ftpPassWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpPass /t REG_SZ /d " + ftpPass + " /f";
        String departmentsListWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v departmentsListString /t REG_SZ /d " + listToStr(departmentsList) + " /f";
        String exchangePathWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v exchangePath /t REG_SZ /d " + exchangePath + " /f";
        String exchangeFileNameWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v exchangeFileName /t REG_SZ /d " + exchangeFileName + " /f";
        String downloadPathWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v downloadPath /t REG_SZ /d " + downloadPath + " /f";

        String optionsWriteQuery[] = {ftpAddressWriteQuery, ftpLoginWriteQuery, ftpPassWriteQuery, departmentsListWriteQuery,
            exchangePathWriteQuery, exchangeFileNameWriteQuery, downloadPathWriteQuery}; //инициализация запросов на изменение реестра

        for (String query : optionsWriteQuery) {
            Process process = Runtime.getRuntime().exec(query);
            while (process.isAlive()) {
            }
            process.destroy();                  //!!!!!!!!!Добавить в клиентскую версию
        }
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

    public DefaultListModel <String> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(DefaultListModel <String> departmentsList) {
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
    private DefaultListModel <String> departmentsList;
    private String exchangePath;
    private String exchangeFileName;
    private String downloadPath;

    private final Pattern p = Pattern.compile("\\w+\\p{Space}+REG_SZ\\p{Space}+(.+)");    //Шаблон для извлечения параметра ключа реестра

    private final String ftpAddressReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpAddress";
    private final String ftpLoginReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpLogin";
    private final String ftpPasswordReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpPass";
    private final String departmentsListReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v departmentsListString";
    private final String exchangePathReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v exchangePath";
    private final String exchangeFileNameReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v exchangeFileName";
    private final String downloadPathReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v downloadPath";
}
