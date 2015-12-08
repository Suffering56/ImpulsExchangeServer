package impulsexchangeserver;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame(Options options) {
        this.options = options;
        this.departmentsList = options.getDepartmentsList();

        initComponents();
        exchangePanel.setLayout(new GridLayout(0, 5, 7, 7));
        setLocationRelativeTo(null);
        initPanelComponents();
        this.setSize(this.getWidth(), (26 + 7) * departmentsList.size() + 20 + 65 + 7);
    }

    private void initPanelComponents() {
        progressBar = new JProgressBar[departmentsList.size()];
        depNumLabel = new JLabel[departmentsList.size()];
        toExchangeBtn = new JToggleButton[departmentsList.size()];
        detailsBtn = new JButton[departmentsList.size()];
        openDirBtn = new JButton[departmentsList.size()];

        for (int i = 0; i < departmentsList.size(); i++) {
            progressBar[i] = new JProgressBar();
            progressBar[i].setStringPainted(true);
            exchangePanel.add(progressBar[i]);

            depNumLabel[i] = new JLabel("Отдел №" + departmentsList.get(i));
            depNumLabel[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            exchangePanel.add(depNumLabel[i]);

            toExchangeBtn[i] = new JToggleButton("На обмен");
            toExchangeBtn[i].setActionCommand("toExchangeBtn_" + departmentsList.get(i));
            toExchangeBtn[i].addActionListener(this::btnsActionPerformed);
            toExchangeBtn[i].setFocusPainted(false);
            exchangePanel.add(toExchangeBtn[i]);

            detailsBtn[i] = new JButton("Детали");
            detailsBtn[i].setActionCommand("detailsBtn_" + departmentsList.get(i));
            detailsBtn[i].addActionListener(this::detailsBtnActionPerformed);
            detailsBtn[i].setFocusPainted(false);
            exchangePanel.add(detailsBtn[i]);

            openDirBtn[i] = new JButton("...");
            openDirBtn[i].setActionCommand("openDirBtn_" + departmentsList.get(i));
            openDirBtn[i].addActionListener((evt) -> {
                try {
                    this.openDirActionPerformed(evt);
                } catch (IOException ex) {
                    System.out.println("ex " + ex);
                }
            });
            openDirBtn[i].setFocusPainted(false);
            exchangePanel.add(openDirBtn[i]);
        }
    }

    private void btnsActionPerformed(ActionEvent evt) {
    }

    private void openDirActionPerformed(ActionEvent evt) throws IOException {
        Desktop.getDesktop().open(new File("C:\\windows"));
    }

    private void detailsBtnActionPerformed(ActionEvent evt) {
        System.out.println("evt = " + evt.paramString());
        DetailsFrame detailsFrame = new DetailsFrame();
        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exchangePanel = new javax.swing.JPanel();
        mainDownloadBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        optionsCallMenuBtn = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 280));
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
        mainDownloadBtn.setText("Загрузить информацию обмена");
        mainDownloadBtn.setFocusPainted(false);
        mainDownloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainDownloadBtnActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Список загруженных заказов");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Завершить обмен");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(mainDownloadBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addComponent(exchangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, mainDownloadBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(exchangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(mainDownloadBtn)
                    .addComponent(jButton2))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, mainDownloadBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDownloadBtnActionPerformed
        try {
            for (int i = 0; i < departmentsList.size(); i++) {
                new FtpDownload(options, progressBar[i], departmentsList.get(i)).start(); //Запуск дополнительных потоков для отправки файла на FTP
            }
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mainDownloadBtnActionPerformed

    private void optionsCallMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallMenuBtnActionPerformed
        OptionsFrame optionsFrame = new OptionsFrame(options);
        optionsFrame.setVisible(true);
    }//GEN-LAST:event_optionsCallMenuBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private final Options options;
    private final DefaultListModel<String> departmentsList;

    private JLabel[] depNumLabel;
    private JProgressBar[] progressBar;
    private JButton[] openDirBtn;
    private JButton[] detailsBtn;
    private JToggleButton[] toExchangeBtn;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exchangePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JButton mainDownloadBtn;
    private javax.swing.JMenuItem optionsCallMenuBtn;
    // End of variables declaration//GEN-END:variables
}
