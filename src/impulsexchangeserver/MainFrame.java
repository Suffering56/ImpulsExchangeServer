package impulsexchangeserver;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame(Options options) {
        this.options = options;
        this.departmentsList = options.getDepartmentsList();

        initComponents();
        initPanelComponents();

        this.setSize(this.getWidth(), (26 + 7) * departmentsList.size() + 20 + 65 + 7);
        this.setLocationRelativeTo(null);
    }

    private void initPanelComponents() {
        exchangePanel.setLayout(new GridLayout(0, 4, 7, 7));         //Устанавливаем компоновку (rows, cols, отступы...)

        progressBar = new JProgressBar[departmentsList.size()];
        depNumLabel = new JLabel[departmentsList.size()];
        toExchangeBtn = new JToggleButton[departmentsList.size()];
        openDirBtn = new JButton[departmentsList.size()];

        for (int i = 0; i < departmentsList.size(); i++) {
            progressBar[i] = new JProgressBar();
            progressBar[i].setStringPainted(true);
            exchangePanel.add(progressBar[i]);

            depNumLabel[i] = new JLabel("Отдел №" + departmentsList.get(i));
            depNumLabel[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            exchangePanel.add(depNumLabel[i]);

            toExchangeBtn[i] = new JToggleButton("На обмен");
            toExchangeBtn[i].setActionCommand(String.valueOf(i));
            toExchangeBtn[i].addActionListener(this::toExchangeBtnActionPerformed);
            toExchangeBtn[i].setFocusPainted(false);
            toExchangeBtn[i].setEnabled(false);
            exchangePanel.add(toExchangeBtn[i]);

            openDirBtn[i] = new JButton("...");
            openDirBtn[i].setActionCommand(departmentsList.get(i));
            openDirBtn[i].addActionListener((evt) -> {
                try {
                    this.openDirActionPerformed(evt);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка: " + ex);       //#Для отладки#
                }
            });
            openDirBtn[i].setFocusPainted(false);
            exchangePanel.add(openDirBtn[i]);
        }
    }

    private void toExchangeBtnActionPerformed(ActionEvent evt) {
        int i = Integer.valueOf(evt.getActionCommand());
        File source = new File(options.getDownloadPath() + "\\" + departmentsList.get(i) + "\\" + options.getExchangeFileName());
        File destination = new File(options.getExchangePath() + "\\" + options.getExchangeFileName());
        toExchangeBtn[i].setSelected(!toExchangeBtn[i].isSelected());
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!toExchangeBtn[i].isSelected()) {                               //Если - это первое нажатие на кнопку, то...
                toExchangeBtn[i].setSelected(true);                             //... зажимаем кнопку ...
                printList.add(activeDepartment[i]);                             //... и добавляем заказы в printList
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);                            //#Для отладки#
            toExchangeBtn[i].setSelected(false);
        }
    }

    private void openDirActionPerformed(ActionEvent evt) throws IOException {
        Desktop.getDesktop().open(new File(
                options.getDownloadPath() + "\\" + evt.getActionCommand()));    //Открываем папку соответствующего отдела (кнопка "...")
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exchangePanel = new javax.swing.JPanel();
        mainDownloadBtn = new javax.swing.JButton();
        doPrintBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        optionsCallMenuBtn = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(584, 280));
        setResizable(false);

        exchangePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout exchangePanelLayout = new javax.swing.GroupLayout(exchangePanel);
        exchangePanel.setLayout(exchangePanelLayout);
        exchangePanelLayout.setHorizontalGroup(
            exchangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        exchangePanelLayout.setVerticalGroup(
            exchangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );

        mainDownloadBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mainDownloadBtn.setText("Загрузить данные");
        mainDownloadBtn.setFocusPainted(false);
        mainDownloadBtn.setMaximumSize(new java.awt.Dimension(161, 23));
        mainDownloadBtn.setMinimumSize(new java.awt.Dimension(161, 23));
        mainDownloadBtn.setPreferredSize(new java.awt.Dimension(170, 23));
        mainDownloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainDownloadBtnActionPerformed(evt);
            }
        });

        doPrintBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        doPrintBtn.setText("На печать");
        doPrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doPrintBtnActionPerformed(evt);
            }
        });

        exitBtn.setText("Выход");
        exitBtn.setMaximumSize(new java.awt.Dimension(161, 23));
        exitBtn.setMinimumSize(new java.awt.Dimension(161, 23));
        exitBtn.setPreferredSize(new java.awt.Dimension(170, 23));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        jMenu1.setText("Меню");

        optionsCallMenuBtn.setText("Настройки");
        optionsCallMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsCallMenuBtnActionPerformed(evt);
            }
        });
        jMenu1.add(optionsCallMenuBtn);

        jMenuItem2.setText("Выход");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Справка");

        jMenuItem3.setText("Вызов справки");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainDownloadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(doPrintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(exchangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exitBtn, mainDownloadBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(exchangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(doPrintBtn)
                    .addComponent(mainDownloadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {doPrintBtn, exitBtn, mainDownloadBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDownloadBtnActionPerformed
        activeDepartment = new ActiveDepartment[departmentsList.size()];
        printList.clear();                                                      //Обнуляем заказы на печать

        try {
            for (int i = 0; i < departmentsList.size(); i++) {
                activeDepartment[i] = new ActiveDepartment();
                activeDepartment[i].setDepartmentNumber(departmentsList.get(i));
                new DataImport(options, progressBar[i], 
                        toExchangeBtn[i], activeDepartment[i]).start();         //Запуск потоков зарузки данных с FTP
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);                            //#Для отладки#
        }
    }//GEN-LAST:event_mainDownloadBtnActionPerformed

    private void optionsCallMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallMenuBtnActionPerformed
        OptionsFrame optionsFrame = new OptionsFrame(options);
        optionsFrame.setVisible(true);
    }//GEN-LAST:event_optionsCallMenuBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitBtnActionPerformed

    private void doPrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doPrintBtnActionPerformed
        PrintFrame printFrame = new PrintFrame(options, printList);
        printFrame.setVisible(true);
    }//GEN-LAST:event_doPrintBtnActionPerformed

    private final Options options;
    private final DefaultListModel<String> departmentsList;
    private final LinkedList<ActiveDepartment> printList = new LinkedList();
    private ActiveDepartment activeDepartment[];

    private JProgressBar[] progressBar;
    private JLabel[] depNumLabel;
    private JToggleButton[] toExchangeBtn;
    private JButton[] openDirBtn;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doPrintBtn;
    private javax.swing.JPanel exchangePanel;
    private javax.swing.JButton exitBtn;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JButton mainDownloadBtn;
    private javax.swing.JMenuItem optionsCallMenuBtn;
    // End of variables declaration//GEN-END:variables
}
