package impulsexchangeserver;

import java.io.IOException;
import java.util.LinkedList;

public class ImpulsExchangeServer {

    public static void main(String[] args) throws IOException {
        LinkedList<String> orders = new LinkedList();
        orders.add("68");
        orders.add("71");
        orders.add("73");
        orders.add("74");
        orders.add("79");
        
        Options options = new Options();
        options.getOptions();                                                   //чтение настроек из реестра
        MainFrame mainFrame = new MainFrame(orders, options);
        mainFrame.setVisible(true);
    }
}
