package impulsexchangeserver;

import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(LinkedList printOrders) {
        this.printOrders = printOrders;

        initComponents();

        //setLocationRelativeTo(null);
        mainPanel.setLayout(null);
        initPanelComponents();
        System.out.println("print orders size = " + printOrders.size());
    }

    private void initPanelComponents() {
        panel = new JPanel[printOrders.size()];
        int x = 0;
        int y = 0;
        JButton jl;
        for (int i = 0; i < printOrders.size(); i++) {
            panel[i] = new JPanel();
            panel[i].setLayout(null);
            panel[i].setSize(300, 100);
            panel[i].setLocation(x, y);
            panel[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jl = new JButton(printOrders.get(i).getDepartmentNumber());
            jl.setLocation(0, 0);
            jl.setSize(200, 80);
      //      jl.setVisible(true);
            panel[i].add(jl);
            mainPanel.add(panel[i]);
//            jl = new JButton(printOrders.get(i).getDepartmentNumber());
//            jl.setLocation(x, y);
//            jl.setSize(200, 80);
//            jl.setVisible(true);
//            mainPanel.add(jl);
//            System.out.println("!!! " + printOrders.get(i).getDepartmentNumber());
//            System.out.println("jlLocation" + jl.getLocation());
//            System.out.println("jlSize" + jl.getSize());
            y = y + 105;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("printFrame"); // NOI18N

        jButton1.setText("jButton1");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(219, 219, 219))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(211, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(123, 123, 123))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private JPanel panel[];
    private final LinkedList<ActiveOrders> printOrders;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
