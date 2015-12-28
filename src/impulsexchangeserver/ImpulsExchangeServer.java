package impulsexchangeserver;

import java.io.IOException;

public class ImpulsExchangeServer {

    public static void main(String[] args) throws IOException {
        Options.getOptions();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
