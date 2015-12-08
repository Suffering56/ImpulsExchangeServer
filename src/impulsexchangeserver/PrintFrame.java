package impulsexchangeserver;

import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(LinkedList printOrders) {
        this.count = printOrders.size();
        this.printOrders = printOrders;
        
        initComponents();
        mainPanel.setLayout(null);
        setLocationRelativeTo(null);
        initPanelComponents();
    }

    private void initPanelComponents() {
        panel = new JPanel[count];
        int x = 0;
        int y = 0;
        for (int i = 0; i < count; i++) {
            panel[i] = new JPanel();
            panel[i].setSize(300, 100);
            panel[i].setLocation(x, y);
            panel[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            mainPanel.add(panel[i]);
            panel[i].add(new JLabel(printOrders.get(i).getDepartmentNumber()));
            y = y + 105;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("printFrame"); // NOI18N

        mainPanel.setName(""); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 651, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final int count;
    private JPanel panel[];
    private final LinkedList <ActiveOrders> printOrders;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
