package impulsexchangeserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;

public class Options {

    public static boolean getOptions() throws IOException {                               //Чтение настроек реестра

        String optionsReadQuery[] = {ftpAddressReadQuery, ftpLoginReadQuery, ftpPasswordReadQuery, 
            departmentsListReadQuery, exchangePathReadQuery, exchangeFileNameReadQuery};   //инициализация запросов к реестру
        LinkedList<String> optionsList = new LinkedList();
        int nullOptionsCounter = 0;

        for (String query : optionsReadQuery) {
            Process process = Runtime.getRuntime().exec(query);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));           //Чтение ключей/значений реестра
            String line;
            String parseLine = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains("REG_SZ")) {             //Извлечение нужной строки потока реестра
                    parseLine = line.trim();               //PS: там почему-то не в одной строке все хранится
                }
            }
            reader.close();
            process.destroy();

            Matcher m = p.matcher(parseLine);
            if (m.matches()) {
                optionsList.add(m.group(1));               //Извлечение нужного значения ключа реестра...
            } else {                                       //... либо если ключ(значение) отсутствует (не соответствует шаблону)...
                optionsList.add("");                       //... извлечение пустого значения (для избежания ошибки)             
                nullOptionsCounter++;
            }
        }

        if (nullOptionsCounter == 6) {
            firstStart();                                  //Загрузка значений по-умолчанию при первом запуске программы
            setOptions();
            return true;
        } else {
            importOptionsIntoProgramm(optionsList);        //Запись извлеченных параметров в класс Options
            return false;
        }
    }

    private static void firstStart() {
        ftpAddress = "5.101.156.8";
        ftpLogin = "mailru5o_login";
        ftpPass = "im699000pass";
        exchangePath = "C:\\";
        exchangeFileName = "swnd5.arc";
    }

    private static void importOptionsIntoProgramm(LinkedList<String> optionsList) {
        ftpAddress = optionsList.get(0);
        ftpLogin = optionsList.get(1);
        ftpPass = optionsList.get(2);
        departmentsList = strToList(optionsList.get(3));
        exchangePath = optionsList.get(4);
        exchangeFileName = optionsList.get(5);
    }

    private static DefaultListModel strToList(String str) {    //Преобразование строки типа: "100_122_73_74..." в список отделов.
        DefaultListModel list = new DefaultListModel();
        String tempStr = "";
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != '_') {
                tempStr = tempStr + ch[i];
            } else {
                if (!tempStr.isEmpty()) {
                    list.addElement(tempStr);
                }
                tempStr = "";
            }
        }
        return list;
    }

    private static String listToStr(DefaultListModel list) {   //Преобразование списка отделов в строку типа: "100_122_73_74..."
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

    public static void setOptions() throws IOException {
        String ftpAddressWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpAddress /t REG_SZ /d " + ftpAddress + " /f";
        String ftpLoginWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpLogin /t REG_SZ /d " + ftpLogin + " /f";
        String ftpPassWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v ftpPass /t REG_SZ /d " + ftpPass + " /f";
        String departmentsListWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v departmentsListString /t REG_SZ /d " + listToStr(departmentsList) + " /f";
        String exchangePathWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v exchangePath /t REG_SZ /d " + exchangePath + " /f";
        String exchangeFileNameWriteQuery = "REG ADD HKCU\\Software\\ImpulsExchangeServer /v exchangeFileName /t REG_SZ /d " + exchangeFileName + " /f";

        String optionsWriteQuery[] = {ftpAddressWriteQuery, ftpLoginWriteQuery, ftpPassWriteQuery, 
            departmentsListWriteQuery, exchangePathWriteQuery, exchangeFileNameWriteQuery}; //инициализация запросов на изменение реестра

        for (String query : optionsWriteQuery) {
            Process process = Runtime.getRuntime().exec(query);
            while (process.isAlive()) {
            }
            process.destroy();
        }
    }

    public static String ftpLogin;
    public static String ftpPass;
    public static String ftpAddress;
    public static DefaultListModel<String> departmentsList  = new DefaultListModel();
    public static String exchangePath;
    public static String exchangeFileName;

    private static final Pattern p = Pattern.compile("\\w+\\p{Space}+REG_SZ\\p{Space}+(.+)");    //Шаблон для извлечения параметра ключа реестра
    private static final String ftpAddressReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpAddress";
    private static final String ftpLoginReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpLogin";
    private static final String ftpPasswordReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v ftpPass";
    private static final String departmentsListReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v departmentsListString";
    private static final String exchangePathReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v exchangePath";
    private static final String exchangeFileNameReadQuery = "REG QUERY HKCU\\Software\\ImpulsExchangeServer /v exchangeFileName";
}
