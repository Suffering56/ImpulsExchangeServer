package impulsexchangeserver;

import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(LinkedList printOrders) {
        this.printOrders = printOrders;

        initComponents();
        setLocationRelativeTo(null);
        mainPanel.setLayout(null);

        initPanelComponents();
        System.out.println("print orders size = " + printOrders.size());
    }

    private void initPanelComponents() {
        panel = new JPanel[printOrders.size()];
        int y = 0;
        int cbxHeight = 23;
        int cbxBetweenSpacer = 3;
        int cbxFirstLastSpacer = 5;
        int panelBetweenSpacer = 5;
        JCheckBox jl;
        for (int i = 0; i < printOrders.size(); i++) {
            int dListSize = printOrders.get(i).getDetailsList().size();
            int panelHeight;// = 26 * dListSize + 10;
            int yy = cbxFirstLastSpacer;
            panel[i] = new JPanel();
            panel[i].setLayout(null);
            panel[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            
            for (int j = 0; j < dListSize; j++) {
                jl = new JCheckBox(printOrders.get(i).getDetailsList().get(j));
                jl.setSize(85, cbxHeight);
                jl.setLocation(5, yy);
                yy = yy + cbxHeight + cbxBetweenSpacer;
                panel[i].add(jl);
                System.out.println("yy = " + yy);
            }
            panelHeight = yy - cbxBetweenSpacer + cbxFirstLastSpacer;
            panel[i].setSize(300, panelHeight);
            panel[i].setLocation(0, y);
            y = y + panelHeight + panelBetweenSpacer;
            System.out.println("y = " + y);
            mainPanel.add(panel[i]);
        }
        this.setSize(this.getSize().width, y + 39 - panelBetweenSpacer);
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("printFrame"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        mainPanel.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        System.out.println("frameSize = " + this.getSize());
        System.out.println("panelSize = " + mainPanel.getSize());
    }//GEN-LAST:event_formComponentResized

    private JPanel panel[];
    private final LinkedList<ActiveOrders> printOrders;
    private int mainPanelHeight;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
