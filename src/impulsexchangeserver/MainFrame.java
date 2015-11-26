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
        exchangePanel.setLayout(new GridLayout(0, 7, 7, 7));
        setLocationRelativeTo(null);
        initPanelComponents();
        this.setSize(this.getWidth(), (26 + 7) * departmentsList.size() + 12 + 65);

    }

    private void initPanelComponents() {
        depNumLabel = new JLabel[departmentsList.size()];
        spaceLabel = new JLabel[departmentsList.size()];
        progressBar = new JProgressBar[departmentsList.size()];
        openDirBtn = new JButton[departmentsList.size()];
        detailsBtn = new JButton[departmentsList.size()]; 
        toExchangeBtn = new JButton[departmentsList.size()];
        completeBtn = new JToggleButton[departmentsList.size()];

        for (int i = 0; i < departmentsList.size(); i++) {
            progressBar[i] = new JProgressBar();
            progressBar[i].setStringPainted(true);
            exchangePanel.add(progressBar[i]);

            depNumLabel[i] = new JLabel("Отдел №" + departmentsList.get(i));
            exchangePanel.add(depNumLabel[i]);

            toExchangeBtn[i] = new JButton("На обмен");
            toExchangeBtn[i].setActionCommand("toExchangeBtn_" + departmentsList.get(i));
            toExchangeBtn[i].addActionListener(this::btnsActionPerformed);
            toExchangeBtn[i].setFocusPainted(false);
            exchangePanel.add(toExchangeBtn[i]);

            completeBtn[i] = new JToggleButton("Готово");
            completeBtn[i].setActionCommand("completeBtn_" + departmentsList.get(i));
            completeBtn[i].addActionListener(this::btnsActionPerformed);
            completeBtn[i].setFocusPainted(false);
            exchangePanel.add(completeBtn[i]);

            spaceLabel[i] = new JLabel("");
            exchangePanel.add(spaceLabel[i]);

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

            detailsBtn[i] = new JButton("Детали");
            detailsBtn[i].setActionCommand("detailsBtn_" + departmentsList.get(i));
            detailsBtn[i].addActionListener(this::detailsBtnActionPerformed);
            detailsBtn[i].setFocusPainted(false);
            exchangePanel.add(detailsBtn[i]);
        }
    }

    private void btnsActionPerformed(ActionEvent evt) {
        System.out.println("evt = " + evt.paramString());
    }

    private void openDirActionPerformed(ActionEvent evt) throws IOException {
        System.out.println("evt = " + evt.paramString());
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
        optionsCallBtn = new javax.swing.JButton();

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

        mainDownloadBtn.setText("Загрузить информацию обмена");
        mainDownloadBtn.setFocusPainted(false);
        mainDownloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainDownloadBtnActionPerformed(evt);
            }
        });

        optionsCallBtn.setText("Опции");
        optionsCallBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsCallBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exchangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(mainDownloadBtn)
                .addGap(61, 61, 61)
                .addComponent(optionsCallBtn)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainDownloadBtn)
                    .addComponent(optionsCallBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exchangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDownloadBtnActionPerformed
        try {
            for (int i = 0; i < departmentsList.size(); i++) {
                new FtpDownload(progressBar[i], departmentsList.get(i)).start(); //Запуск дополнительных потоков для отправки файла на FTP
            }
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mainDownloadBtnActionPerformed

    private void optionsCallBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallBtnActionPerformed
        OptionsFrame optFrame = new OptionsFrame(options);
        optFrame.setVisible(true);
    }//GEN-LAST:event_optionsCallBtnActionPerformed

    private final Options options;
    private final DefaultListModel <String> departmentsList;
    
    private JLabel[] depNumLabel;
    private JLabel[] spaceLabel;
    private JProgressBar[] progressBar;
    private JButton[] openDirBtn;
    private JButton[] detailsBtn;
    private JButton[] toExchangeBtn;
    private JToggleButton[] completeBtn;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exchangePanel;
    private javax.swing.JButton mainDownloadBtn;
    private javax.swing.JButton optionsCallBtn;
    // End of variables declaration//GEN-END:variables
}
