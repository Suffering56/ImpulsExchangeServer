package impulsexchangeserver;

import java.io.IOException;
import java.util.LinkedList;

public class ImpulsExchangeServer {

    public static void main(String[] args) throws IOException {        
        Options options = new Options();
        options.getOptions();                                                   //чтение настроек из реестра 
        MainFrame mainFrame = new MainFrame(options);
        mainFrame.setVisible(true);
    }
}
