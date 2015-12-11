package impulsexchangeserver;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame(Options options) {
        this.options = options;
        this.departmentsList = options.getDepartmentsList();

        initComponents();
        exchangePanel.setLayout(new GridLayout(0, 4, 7, 7));
        setLocationRelativeTo(null);
        initPanelComponents();
        this.setSize(this.getWidth(), (26 + 7) * departmentsList.size() + 20 + 65 + 7);

        activeOrders = new ActiveOrders[departmentsList.size()];
        createTimer();
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

//            detailsBtn[i] = new JButton("Детали");
//            detailsBtn[i].setActionCommand("detailsBtn_" + departmentsList.get(i));
//            detailsBtn[i].addActionListener(this::detailsBtnActionPerformed);
//            detailsBtn[i].setFocusPainted(false);
//            exchangePanel.add(detailsBtn[i]);

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
        doPrintBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
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

        doPrintBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        doPrintBtn.setText("Список загруженных заказов");
        doPrintBtn.setEnabled(false);
        doPrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doPrintBtnActionPerformed(evt);
            }
        });

        exitBtn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exitBtn.setText("Выйти без сохранения");
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
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(mainDownloadBtn)
                        .addGap(18, 18, 18)
                        .addComponent(doPrintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addComponent(exchangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {doPrintBtn, exitBtn, mainDownloadBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(exchangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(doPrintBtn)
                    .addComponent(mainDownloadBtn)
                    .addComponent(exitBtn))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {doPrintBtn, exitBtn, mainDownloadBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDownloadBtnActionPerformed
        counter = 0;
        doPrintBtn.setEnabled(false);
        timer.start();
        try {
            for (int i = 0; i < departmentsList.size(); i++) {
                activeOrders[i] = new ActiveOrders();
                activeOrders[i].setDepartmentNumber(departmentsList.get(i));
                new FtpDownload(options, progressBar[i], activeOrders[i]).start(); //Запуск дополнительных потоков для отправки файла на FTP
            }
        } catch (Exception ex) {
            printStackTrace(ex);
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
        LinkedList<ActiveOrders> printOrders = new LinkedList();
        for (ActiveOrders activeOrder : activeOrders) {
            if (activeOrder.isUdpated() == true) {
                printOrders.add(activeOrder);
            }
        }
        PrintFrame pr = new PrintFrame(printOrders);
        pr.setVisible(true);

    }//GEN-LAST:event_doPrintBtnActionPerformed

    private void createTimer() {
        timer = new Timer(100, (ActionEvent e) -> {
            counter = 0;
            for (int i = 0; i < departmentsList.size(); i++) {
                if (progressBar[i].getValue() == 100) {
                    counter++;
                }
            }
            if (counter == departmentsList.size()) {
                counter = 0;
                doPrintBtn.setEnabled(true);
                timer.stop();
            }
        });
    }

    private final Options options;
    private final DefaultListModel<String> departmentsList;
    private Timer timer;
    private int counter;

    private JLabel[] depNumLabel;
    private JProgressBar[] progressBar;
    private JButton[] openDirBtn;
    private JButton[] detailsBtn;
    private JToggleButton[] toExchangeBtn;
    private final ActiveOrders activeOrders[];

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
