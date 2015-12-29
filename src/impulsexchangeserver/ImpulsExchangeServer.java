package impulsexchangeserver;

import java.io.IOException;
import javax.swing.JOptionPane;

public class ImpulsExchangeServer {

    public static void main(String[] args) {
        try {
            Options.getOptions();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения реестра. Код ошибки:\r\n" + ex.toString(), "Options.error", JOptionPane.ERROR_MESSAGE);
        }
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
