package impulsexchangeserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DirectoryHandler {
  
    public static void checkLocalDirectory(String departmentName) throws IOException {
        String path = System.getProperty("user.dir") + "\\" + departmentName;
        File directory = new File(path);
        if (!Files.exists(directory.toPath())) {
            Files.createDirectory(directory.toPath());
        }
    }
}
