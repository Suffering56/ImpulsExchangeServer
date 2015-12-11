package impulsexchangeserver;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(LinkedList printList) {
        this.printList = printList;

        initComponents();
        setLocationRelativeTo(null);

        globalPanel.setLayout(null);

//        scrollPane = new JScrollPane();
//        scrollPane.setViewportView(globalPanel);
//        
//        this.add(scrollPane);
        initPanelComponents();
    }

    private void initPanelComponents() {
        singleBoxList = new LinkedList<>();
        headerBox = new JCheckBox[printList.size()];

        int yGlobal = 0;            //Начальная y-координата localPanel
        int yLocal;
        JPanel localPanel;

        for (int i = 0; i < printList.size(); i++) {
            localPanel = new JPanel();
            localPanel.setLayout(null);
            localPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            yLocal = ELEMENT_PADDING;
            headerBox[i] = new JCheckBox(printList.get(i).getDepartmentNumber());
            headerBox[i].setSize(85, CHBX_HEIGHT);
            headerBox[i].setLocation(5, yLocal);
            headerBox[i].setActionCommand(String.valueOf(i));
            headerBox[i].addActionListener(this::headerBoxActionPerformed);
            yLocal = yLocal + CHBX_HEIGHT + ELEMENT_PADDING;
            localPanel.add(headerBox[i]);

            singleBox = new JCheckBox[printList.get(i).getDetailsList().size()];
            for (int j = 0; j < printList.get(i).getDetailsList().size(); j++) {

                singleBox[j] = new JCheckBox(printList.get(i).getDetailsList().get(j));
                singleBox[j].setSize(85, CHBX_HEIGHT);
                singleBox[j].setLocation(25, yLocal);
                singleBox[j].setActionCommand("checkBox_" + printList.get(i).getDetailsList().get(j));
                singleBox[j].addActionListener(this::singleBoxActionPerformed);
                yLocal = yLocal + CHBX_HEIGHT + ELEMENT_PADDING;
                localPanel.add(singleBox[j]);
            }
            singleBoxList.add(singleBox);

            localPanel.setSize(290, yLocal);
            localPanel.setLocation(0, yGlobal);
            yGlobal = yGlobal + yLocal + ELEMENT_PADDING;
            globalPanel.add(localPanel);
        }
        exitBtn = new JButton("Отмена");
        exitBtn.setFont(new Font("Times New Roman", 0, 14));
        exitBtn.setSize(115, CHBX_HEIGHT);
        exitBtn.setLocation(5, yGlobal);
        exitBtn.addActionListener(this::exitBtnActionPerformed);
        globalPanel.add(exitBtn);
        completeBtn = new JButton("Завершить обмен");
        completeBtn.setFont(new Font("Times New Roman", 1, 14));
        completeBtn.setSize(155, CHBX_HEIGHT);
        completeBtn.setLocation(130, yGlobal);
        completeBtn.addActionListener(this::completeBtnActionPerformed);
        globalPanel.add(completeBtn);

        this.setSize(this.getSize().width, yGlobal + 39 + 28 - ELEMENT_PADDING);
        //scrollPane.setSize(this.getSize().width, yGlobal + 39 + 28 - ELEMENT_PADDING - 200);
        this.repaint();
    }

    private void headerBoxActionPerformed(ActionEvent evt) {
        int i = Integer.valueOf(evt.getActionCommand());

        for (int j = 0; j < singleBoxList.get(i).length; j++) {
            JCheckBox[] tempSingleBox = singleBoxList.get(i);
            if (tempSingleBox[j].isSelected() != headerBox[i].isSelected()) {   // Задаем соответствие главного checkBox - дочерним
                tempSingleBox[j].setSelected(!tempSingleBox[j].isSelected());
            }
        }
        System.out.println("clicked = " + evt.getActionCommand());
        System.out.println("headerBox = " + headerBox[i].isSelected());
    }

    private void singleBoxActionPerformed(ActionEvent evt) {
        System.out.println("clicked = " + evt.getActionCommand());
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void completeBtnActionPerformed(ActionEvent evt) {
//        new DetailsCleaning(cleaningList.get(Integer.valueOf(evt.getActionCommand())), "partial").start();
//        new DetailsCleaning(cleaningList.get(Integer.valueOf(evt.getActionCommand())), "total").start();
        
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        globalPanel = new javax.swing.JPanel();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(globalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

    }//GEN-LAST:event_formComponentResized

    private final LinkedList<ActiveDepartment> printList;
    private final LinkedList<ActiveDepartment> cleaningList = new LinkedList();
    private static final int CHBX_HEIGHT = 23;
    private static final int ELEMENT_PADDING = 3;

    private JCheckBox[] singleBox;
    private JCheckBox[] headerBox;
    private LinkedList<JCheckBox[]> singleBoxList;
    private JButton completeBtn, exitBtn;

    private JScrollPane scrollPane;

    private int cbxTotal;
    private int cbxCounter;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel globalPanel;
    // End of variables declaration//GEN-END:variables
}
