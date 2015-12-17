package impulsexchangeserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImpulsExchangeServer {

    public static void main(String[] args) throws IOException {
        Options options = new Options();
        boolean isFirstStart = options.getOptions();                                //Чтение настроек реестра

        if (!isFirstStart) {                                                        //Не проверяем папки при первом запуске
            File directory = new File(options.getDownloadPath());
            if (!Files.exists(directory.toPath())) {                                //Проверяем наличие корневой папки (ImpulsDataExchange)
                Files.createDirectory(directory.toPath());                          //Если её нет - создаем
            }
            checkingDirectories(options);
        }

        MainFrame mainFrame = new MainFrame(options);
        mainFrame.setVisible(true);
    }

    private static void checkingDirectories(Options options) throws IOException {

        for (int i = 0; i < options.getDepartmentsList().size(); i++) {
            File directory = new File(options.getDownloadPath() + "\\" + options.getDepartmentsList().get(i));
            if (!Files.exists(directory.toPath())) {
                Files.createDirectory(directory.toPath());
            }
        }
    }
}
