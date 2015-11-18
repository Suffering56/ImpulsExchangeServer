package impulsexchangeserver;

import java.util.LinkedList;

public class ImpulsExchangeServer {

    public static void main(String[] args) {
        LinkedList <String> orders = new LinkedList();
        orders.add("68");
        orders.add("71");
        orders.add("73");
        orders.add("74");
        orders.add("79");
//        orders.add("33");
//        orders.add("44");
//        orders.add("55");
//        orders.add("66");
        MainFrame mainFrame = new MainFrame(orders);
        mainFrame.setVisible(true);
    }
}
