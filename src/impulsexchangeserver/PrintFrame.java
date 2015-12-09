package impulsexchangeserver;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(LinkedList printOrders) {
        this.printOrders = printOrders;

        initComponents();
        setLocationRelativeTo(null);

        globalPanel.setLayout(null);
        initPanelComponents();
    }

    private void initPanelComponents() {
        departmentBoxList = new LinkedList<>();
        headerCBox = new JCheckBox[printOrders.size()];
        
        int yGlobal = 0;            //Начальная y-координата localPanel
        int yLocal;
        JPanel localPanel;
        
        for (int i = 0; i < printOrders.size(); i++) {
            localPanel = new JPanel();
            localPanel.setLayout(null);
            localPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            yLocal = ELEMENT_PADDING;
            headerCBox[i] = new JCheckBox(printOrders.get(i).getDepartmentNumber());
            headerCBox[i].setSize(85, CHBX_HEIGHT);
            headerCBox[i].setLocation(5, yLocal);
            headerCBox[i].setActionCommand(String.valueOf(i));
            headerCBox[i].addActionListener(this::headerCBoxActionPerformed);
            yLocal = yLocal + CHBX_HEIGHT + ELEMENT_PADDING;
            localPanel.add(headerCBox[i]);
            
            orderCBox = new JCheckBox[printOrders.get(i).getDetailsList().size()];
            for (int j = 0; j < printOrders.get(i).getDetailsList().size(); j++) {
                
                orderCBox[j] = new JCheckBox(printOrders.get(i).getDetailsList().get(j));
                orderCBox[j].setSize(85, CHBX_HEIGHT);
                orderCBox[j].setLocation(25, yLocal);
                orderCBox[j].setActionCommand("checkBox_" + printOrders.get(i).getDetailsList().get(j));
                orderCBox[j].addActionListener(this::cbxActionPerformed);
                yLocal = yLocal + CHBX_HEIGHT + ELEMENT_PADDING;
                localPanel.add(orderCBox[j]);
            }
            departmentBoxList.add(orderCBox);

            localPanel.setSize(290, yLocal);
            localPanel.setLocation(0, yGlobal);
            yGlobal = yGlobal + yLocal + ELEMENT_PADDING;
            globalPanel.add(localPanel);
        }
        //globalPanel.setSize(globalPanel.getSize().width, yGlobal - ELEMENT_PADDING );
        globalPanel.repaint();
        this.setSize(this.getSize().width, yGlobal + 39 + 28 - ELEMENT_PADDING);
        this.repaint();
    }

    private void cbxActionPerformed(ActionEvent evt) {
        System.out.println("clicked = " + evt.getActionCommand());
    }

    private void headerCBoxActionPerformed(ActionEvent evt) {
        int i = Integer.valueOf(evt.getActionCommand());
        
        for (int j = 0; j < departmentBoxList.get(i).length; j++) {
            JCheckBox[] tempCBox = departmentBoxList.get(i);
            if (tempCBox[j].isSelected() != headerCBox[i].isSelected()) {
                tempCBox[j].setSelected(!tempCBox[j].isSelected());
            }
        }
        System.out.println("clicked = " + evt.getActionCommand());
        System.out.println("headerBox = " + headerCBox[i].isSelected());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        globalPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("printFrame"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        globalPanel.setPreferredSize(new java.awt.Dimension(290, 10));

        javax.swing.GroupLayout globalPanelLayout = new javax.swing.GroupLayout(globalPanel);
        globalPanel.setLayout(globalPanelLayout);
        globalPanelLayout.setHorizontalGroup(
            globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        globalPanelLayout.setVerticalGroup(
            globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Завершить обмен");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(3, 3, 3)
                .addComponent(globalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//        System.out.println("frameSize = " + this.getSize());
//        System.out.println("panelSize = " + globalPanel.getSize());
    }//GEN-LAST:event_formComponentResized

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private final LinkedList<ActiveOrders> printOrders;
    private static final int CHBX_HEIGHT = 23;
    private static final int ELEMENT_PADDING = 3;

    private JCheckBox[] orderCBox;
    private JCheckBox[] headerCBox;
    private LinkedList <JCheckBox[]> departmentBoxList;

    private int cbxTotal;
    private int cbxCounter;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel globalPanel;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
