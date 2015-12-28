package impulsexchangeserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.net.ftp.FTPClient;

public class DirectoryHandler {

    public static void checkLocalDirectory(String departmentName) throws IOException {
        String path = System.getProperty("user.dir") + "\\" + departmentName;
        File directory = new File(path);
        if (!Files.exists(directory.toPath())) {
            Files.createDirectory(directory.toPath());
        }
    }

    public static void checkFtpDirectory(String departmentName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(Options.ftpAddress);
        ftpClient.login(Options.ftpLogin, Options.ftpPass);
        ftpClient.enterLocalPassiveMode();
        boolean exist = ftpClient.changeWorkingDirectory(departmentName);
        if (!exist) {
            ftpClient.makeDirectory(departmentName);
            ftpClient.changeWorkingDirectory(departmentName);
        }
        ftpClient.disconnect();
    }
}
