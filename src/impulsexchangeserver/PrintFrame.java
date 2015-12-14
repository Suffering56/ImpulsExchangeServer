package impulsexchangeserver;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PrintFrame extends javax.swing.JFrame {

    public PrintFrame(Options options, LinkedList printList) {
        this.options = options;
        this.printList = printList;

        residualListInit();         //residualList.addAll(printList);
        initComponents();
        setLocationRelativeTo(null);

        globalPanel.setLayout(null);

//        scrollPane = new JScrollPane();
//        scrollPane.setViewportView(globalPanel);
//        this.add(scrollPane);
        initPanelComponents();
    }

    private void residualListInit() {
        for (int i = 0; i < printList.size(); i++) {
            residualList.add(new ActiveDepartment());
            residualList.getLast().setDepartmentNumber(printList.get(i).getDepartmentNumber());
            residualList.getLast().getDetailsList().addAll(printList.get(i).getDetailsList());
        }
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
                singleBox[j].setActionCommand(String.valueOf(i) + "_" + String.valueOf(j) + "_" + String.valueOf(printList.get(i).getDetailsList().get(j)));
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
        JCheckBox[] tempSingleBoxList = singleBoxList.get(i);

        for (int j = 0; j < tempSingleBoxList.length; j++) {                            //Выделяем (или наоборот) дочерние singleBox так, чтобы...
            if (tempSingleBoxList[j].isSelected() != headerBox[i].isSelected()) {       //... их значение соответствовало родительскому headerBox
                tempSingleBoxList[j].setSelected(!tempSingleBoxList[j].isSelected());
            }
        }

        if (headerBox[i].isSelected()) {
            residualList.get(i).getDetailsList().clear();                                   //Очищаем residualList если выбраны все заказы
        } else {
            residualList.get(i).getDetailsList().clear();
            residualList.get(i).getDetailsList().addAll(printList.get(i).getDetailsList()); //Добавляем все заказы в residualList если выбраны все заказы
        }
    }

    private void singleBoxActionPerformed(ActionEvent evt) {
        Pattern p = Pattern.compile("(\\d+)_(\\d+)_(\\d+/\\d+)");
        Matcher m = p.matcher(evt.getActionCommand());
        if (m.matches()) {
            int i = Integer.valueOf(m.group(1));
            int j = Integer.valueOf(m.group(2));
            String order = m.group(3);
            JCheckBox[] tempSingleBox = singleBoxList.get(i);

            if (tempSingleBox[j].isSelected()) {
                residualList.get(i).getDetailsList().remove(order);
            } else {
                int index = j + 1 - (singleBoxList.get(i).length
                        - residualList.get(i).getDetailsList().size());    //    это, чтобы элемент ...
                if (index < 0) {                                             //... после восстановления вставал на свое место ...
                    index = 0;                                               //... а это защита от IndexBoundException
                }
                residualList.get(i).getDetailsList().add(index, order);
            }
        } else {
            JOptionPane.showMessageDialog(null, "no matches");
        }
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void completeBtnActionPerformed(ActionEvent evt) {
//        System.out.println("=============НАЧАЛО================");
//        for (int i = 0; i < residualList.size(); i++) {
//            System.out.println("Department = " + residualList.get(i).getDepartmentNumber());
//            for (int j = 0; j < residualList.get(i).getDetailsList().size(); j++) {
//                System.out.println("Order: " + residualList.get(i).getDetailsList().get(j));
//            }
//        }
//        System.out.println("===============КОНЕЦ==============");
        printList.clear();
        printList.addAll(residualList);
        
        for (int i = 0; i < residualList.size(); i++) {
            new DetailsCleaning(options, residualList.get(i))
                    .start();
        }
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

    private final Options options;
    private final LinkedList<ActiveDepartment> printList;
    private final LinkedList<ActiveDepartment> residualList = new LinkedList();
    private static final int CHBX_HEIGHT = 23;
    private static final int ELEMENT_PADDING = 3;

    private JCheckBox[] singleBox;
    private JCheckBox[] headerBox;
    private LinkedList<JCheckBox[]> singleBoxList;
    private JButton completeBtn, exitBtn;

    private JScrollPane scrollPane;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel globalPanel;
    // End of variables declaration//GEN-END:variables
}
