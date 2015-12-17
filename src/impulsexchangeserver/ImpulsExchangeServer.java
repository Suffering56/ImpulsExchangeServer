package impulsexchangeserver;

import java.io.IOException;

public class ImpulsExchangeServer {

    public static void main(String[] args) throws IOException {       
        Options options = new Options();
        options.getOptions();                                                       //Чтение настроек из реестра 
        MainFrame mainFrame = new MainFrame(options);
        mainFrame.setVisible(true);
    }
}
