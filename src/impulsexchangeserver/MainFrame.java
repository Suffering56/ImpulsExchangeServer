package impulsexchangeserver;

import javax.swing.JButton;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        btn = new JButton();
    }
    
    private JButton btn;
}
