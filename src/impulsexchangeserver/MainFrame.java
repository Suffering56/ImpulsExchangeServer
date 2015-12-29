package impulsexchangeserver;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        departmentNameList = Options.departmentsList;
        departmentsCount = departmentNameList.size();
        initComponents();
        initPanelComponents();

        this.setSize(this.getWidth(), (26 + 7) * departmentsCount + 20 + 65 + 7);
        this.setLocationRelativeTo(null);
    }

    private void initPanelComponents() {
        exchangePanel.setLayout(new GridLayout(0, 4, 7, 7));         //Устанавливаем компоновку (rows, cols, отступы...)

        progressBar = new JProgressBar[departmentsCount];
        departmentNameLabel = new JLabel[departmentsCount];
        toExchangeBtn = new JToggleButton[departmentsCount];
        openDirBtn = new JButton[departmentsCount];

        for (int i = 0; i < departmentsCount; i++) {
            progressBar[i] = new JProgressBar();
            progressBar[i].setStringPainted(true);
            exchangePanel.add(progressBar[i]);

            departmentNameLabel[i] = new JLabel("Отдел №" + departmentNameList.get(i));
            departmentNameLabel[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            exchangePanel.add(departmentNameLabel[i]);

            toExchangeBtn[i] = new JToggleButton("На обмен");
            toExchangeBtn[i].setActionCommand(String.valueOf(i));               //передаем в качестве параметра индекс отдела
            toExchangeBtn[i].addActionListener(this::toExchangeBtnActionPerformed);
            toExchangeBtn[i].setFocusPainted(false);
            toExchangeBtn[i].setEnabled(false);
            exchangePanel.add(toExchangeBtn[i]);

            openDirBtn[i] = new JButton("...");
            openDirBtn[i].setActionCommand(departmentNameList.get(i));             //передаем в качестве параметра номер отдела
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
        File source = new File(System.getProperty("user.dir") + "\\" + departmentNameList.get(i) + "\\" + Options.exchangeFileName);
        File destination = new File(Options.exchangePath + "\\" + Options.exchangeFileName);
        toExchangeBtn[i].setSelected(!toExchangeBtn[i].isSelected());
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!toExchangeBtn[i].isSelected()) {                               //Если - это первое нажатие на кнопку, то...
                toExchangeBtn[i].setSelected(true);                             //... зажимаем кнопку ...
                doPrintList.add(activeDepartment[i]);                           //... и добавляем заказы в printList
            }
        } catch (IOException ex) {
            toExchangeBtn[i].setSelected(false);
            JOptionPane.showMessageDialog(null, ex);                            //#Для отладки#
        }
    }

    private void openDirActionPerformed(ActionEvent evt) throws IOException {
        DirectoryHandler.checkLocalDirectory(evt.getActionCommand());
        Desktop.getDesktop().open(new File(
                System.getProperty("user.dir") + "\\" + evt.getActionCommand()));    //Открываем папку соответствующего отдела (кнопка "...")
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
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Impuls Exchange Server");
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
        doPrintBtn.setFocusPainted(false);
        doPrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doPrintBtnActionPerformed(evt);
            }
        });

        exitBtn.setText("Выход");
        exitBtn.setFocusPainted(false);
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

        jMenu3.setText("Архив");

        jMenuItem1.setText("Предыдущий обмен");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem4.setText("Поиск по всем заказам");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem6.setText("Последние 25 заказов отдела");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Справка");

        jMenuItem3.setText("Вызов справки");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
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
        activeDepartment = new ActiveDepartment[departmentsCount];
        doPrintList.clear();                                                      //Обнуляем заказы на печать

        try {
            for (int i = 0; i < departmentsCount; i++) {
                activeDepartment[i] = new ActiveDepartment();
                activeDepartment[i].setDepartmentName(departmentNameList.get(i));
                new DataImport(progressBar[i],
                        toExchangeBtn[i], activeDepartment[i]).start();         //Запуск потоков зарузки данных с FTP
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);                            //#Для отладки#
        }
    }//GEN-LAST:event_mainDownloadBtnActionPerformed

    private void optionsCallMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallMenuBtnActionPerformed
        OptionsFrame optionsFrame = new OptionsFrame();
        optionsFrame.setVisible(true);
    }//GEN-LAST:event_optionsCallMenuBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitBtnActionPerformed

    private void doPrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doPrintBtnActionPerformed
        PrintFrame printFrame = new PrintFrame(doPrintList);
        printFrame.setVisible(true);
    }//GEN-LAST:event_doPrintBtnActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        HelpFrame helpFrame = new HelpFrame();
        helpFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            ArchiveLastExchangeFrame previousImportFrame = new ArchiveLastExchangeFrame();
            previousImportFrame.setVisible(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);                            //для отладки
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ArchiveSearchFrame archiveSearchFrame = new ArchiveSearchFrame();
        archiveSearchFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        String param = JOptionPane.showInputDialog(null, "Введите номер отдела:", "Выбор отдела", JOptionPane.PLAIN_MESSAGE);
        if (param != null) {
            if (!departmentNameList.contains(param)) {
                JOptionPane.showMessageDialog(null, "Программа не знает такого отдела!", "Отдел №" + param, JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    ArchiveLast_25_Frame archiveLast_25_Frame = new ArchiveLast_25_Frame(param);
                    archiveLast_25_Frame.setVisible(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "У данного отдела пока еще нет архива!", "Отдел №" + param, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private final DefaultListModel<String> departmentNameList;
    private final int departmentsCount;
    private ActiveDepartment activeDepartment[];
    private final LinkedList<ActiveDepartment> doPrintList = new LinkedList();
    private JProgressBar[] progressBar;
    private JLabel[] departmentNameLabel;
    private JToggleButton[] toExchangeBtn;
    private JButton[] openDirBtn;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doPrintBtn;
    private javax.swing.JPanel exchangePanel;
    private javax.swing.JButton exitBtn;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JButton mainDownloadBtn;
    private javax.swing.JMenuItem optionsCallMenuBtn;
    // End of variables declaration//GEN-END:variables
}
