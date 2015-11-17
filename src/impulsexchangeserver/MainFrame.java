package impulsexchangeserver;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        exchangePanel.setLayout(new GridLayout(0, 5, 7, 7));
        setLocationRelativeTo(null);
        initPanelComponents();
        this.setSize(this.getWidth(), (26+7)*elementsCount + 12 + 65);
    }

    private void initPanelComponents() {
        JLabel[] depNumLabel = new JLabel[elementsCount];
        JProgressBar[] progressBar = new JProgressBar[elementsCount];
        JButton[] openDirBtn = new JButton[elementsCount];
        JButton[] infoBtn = new JButton[elementsCount];
        JButton[] toExchangeBtn = new JButton[elementsCount];

        for (int i = 0; i < elementsCount; i++) {
            depNumLabel[i] = new JLabel("№" + i);
            exchangePanel.add(depNumLabel[i]);

            progressBar[i] = new JProgressBar();
            exchangePanel.add(progressBar[i]);

            openDirBtn[i] = new JButton("Открыть");
            openDirBtn[i].setActionCommand("openDirBtn_" + i);
            openDirBtn[i].addActionListener(this::btnsActionPerformed);
            exchangePanel.add(openDirBtn[i]);

            infoBtn[i] = new JButton("Инфо");
            infoBtn[i].setActionCommand("infoBtn_" + i);
            infoBtn[i].addActionListener(this::btnsActionPerformed);
            exchangePanel.add(infoBtn[i]);
            
            toExchangeBtn[i] = new JButton("На обмен");
            toExchangeBtn[i].setActionCommand("toExchangeBtn_" + i);
            toExchangeBtn[i].addActionListener(this::btnsActionPerformed);
            exchangePanel.add(toExchangeBtn[i]);
        }
    }

    private void btnsActionPerformed(ActionEvent evt) {
        System.out.println("evt = " + evt.paramString());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exchangePanel = new javax.swing.JPanel();
        mainDownloadBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(650, 280));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exchangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(mainDownloadBtn)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(mainDownloadBtn)
                .addGap(5, 5, 5)
                .addComponent(exchangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainDownloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDownloadBtnActionPerformed
        System.out.println("size = " + exchangePanel.getSize());
    }//GEN-LAST:event_mainDownloadBtnActionPerformed

    private JScrollPane scrollPane;
    private static final int elementsCount = 5;
    private GridLayout lay = new GridLayout(0, 5, 20, 20);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exchangePanel;
    private javax.swing.JButton mainDownloadBtn;
    // End of variables declaration//GEN-END:variables
}
