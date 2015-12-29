package impulsexchangeserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class PrintFrame extends JFrame {

    public PrintFrame(LinkedList doPrintList) {
        this.doPrintList = doPrintList;
        notRemoveListInit();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Подтверждение обмена");
        this.setResizable(false);
        this.setLayout(null);
        globalPanel = new JPanel();
        globalPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 10));
        globalPanel.setLayout(null);
        this.add(globalPanel);

        headerBox = new JCheckBox[doPrintList.size()];                                  //Задаем МАССИВ HeaderBox[количество отделов]
        int yGlobal = 0;                                                                //Определяяет высоту globalPanel и yLocation globalPanel

        for (int i = 0; i < doPrintList.size(); i++) {
            JPanel localPanel = new JPanel();
            localPanel.setLayout(null);
            localPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

            int yLocal = ELEMENT_PADDING;
            headerBox[i] = new JCheckBox(doPrintList.get(i).getDepartmentName());       //Добавление HeaderBox ======= Начало
            headerBox[i].setFocusPainted(false);
            headerBox[i].setSize(85, ELEMENT_HEIGHT);
            headerBox[i].setLocation(5, yLocal);
            headerBox[i].setActionCommand(String.valueOf(i));
            headerBox[i].addActionListener(this::headerBoxActionPerformed);
            yLocal = yLocal + ELEMENT_HEIGHT + ELEMENT_PADDING;
            localPanel.add(headerBox[i]);                                               //Добавление HeaderBox ======= Конец

            singleBox = new JCheckBox[doPrintList.get(i).getOrdersList().size()];        //Добавление SingleBox ======= Начало
            for (int j = 0; j < doPrintList.get(i).getOrdersList().size(); j++) {
                singleBox[j] = new JCheckBox(extractOrderName(doPrintList.get(i).getOrdersList().get(j)));
                singleBox[j].setFocusPainted(false);
                singleBox[j].setSize(260, ELEMENT_HEIGHT);
                singleBox[j].setLocation(25, yLocal);
                singleBox[j].setActionCommand(String.valueOf(i) + "_" + String.valueOf(j) + "_" + String.valueOf(doPrintList.get(i).getOrdersList().get(j)));
                singleBox[j].addActionListener(this::singleBoxActionPerformed);
                yLocal = yLocal + ELEMENT_HEIGHT + ELEMENT_PADDING;
                localPanel.add(singleBox[j]);                                           //Добавление SingleBox ======= Конец
            }
            singleBoxList.add(singleBox);                                               //Добавляем МАССИВ кнопок SingleBox в List                                           

            localPanel.setSize(WINDOW_WIDTH, yLocal);
            localPanel.setLocation(0, yGlobal);
            yGlobal = yGlobal + yLocal + ELEMENT_PADDING;
            globalPanel.add(localPanel);
        }

        initBaseComponents(yGlobal);
        this.add(exitBtn);
        this.add(completeBtn);
        this.repaint();
    }

    private void notRemoveListInit() {
        for (int i = 0; i < doPrintList.size(); i++) {
            notRemoveList.add(new ActiveDepartment());
            notRemoveList.getLast().setDepartmentName(doPrintList.get(i).getDepartmentName());
            notRemoveList.getLast().getOrdersList().addAll(doPrintList.get(i).getOrdersList());

            removeList.add(new ActiveDepartment());
            removeList.getLast().setDepartmentName(doPrintList.get(i).getDepartmentName());
        }
    }

    private void headerBoxActionPerformed(ActionEvent evt) {
        int i = Integer.valueOf(evt.getActionCommand());
        JCheckBox[] tempSingleBoxList = singleBoxList.get(i);

        for (int j = 0; j < tempSingleBoxList.length; j++) {                                //Выделяем (или наоборот) дочерние singleBox так, чтобы...
            if (tempSingleBoxList[j].isSelected() != headerBox[i].isSelected()) {           //... их значение соответствовало родительскому headerBox
                tempSingleBoxList[j].setSelected(!tempSingleBoxList[j].isSelected());
            }
        }

        if (headerBox[i].isSelected()) {                                                    //если галочку в headerBox ПОСТАВИЛИ
            notRemoveList.get(i).getOrdersList().clear();
            removeList.get(i).getOrdersList().clear();
            removeList.get(i).getOrdersList().addAll(doPrintList.get(i).getOrdersList());
        } else {                                                                            //если галочку с headerBox УБРАЛИ
            notRemoveList.get(i).getOrdersList().clear();
            notRemoveList.get(i).getOrdersList().addAll(doPrintList.get(i).getOrdersList());
            removeList.get(i).getOrdersList().clear();
        }
    }

    private void singleBoxActionPerformed(ActionEvent evt) {
        Pattern pp = Pattern.compile("(\\d+)_(\\d+)_(\\d+/\\d+\\p{Space}+\\d+.\\d+.\\d+\\p{Space}\\d+:\\d+:\\d+)");
        Matcher m = pp.matcher(evt.getActionCommand());
        if (m.matches()) {
            int i = Integer.valueOf(m.group(1));            //индекс отдела
            int j = Integer.valueOf(m.group(2));            //индекс нажатого checkBox-а
            String order = m.group(3);                      //наименование заказа 
            JCheckBox[] tempSingleBox = singleBoxList.get(i);

            if (tempSingleBox[j].isSelected()) {
                notRemoveList.get(i).getOrdersList().remove(order);
                removeList.get(i).getOrdersList().add(order);
            } else {
                notRemoveList.get(i).getOrdersList().add(getIndex(i, j), order);
                removeList.get(i).getOrdersList().remove(order);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Неверный номер заказа");
        }
    }

    private int getIndex(int i, int j) {
        int index = j + 1 - (singleBoxList.get(i).length
                - notRemoveList.get(i).getOrdersList().size());          //это, чтобы элемент ...
        if (index < 0) {                                                //... после восстановления вставал на свое место ...
            index = 0;                                                  //... а это защита от IndexBoundException
        }
        return index;
    }

    private void completeBtnActionPerformed(ActionEvent evt) throws IOException {
        doPrintList.clear();
        doPrintList.addAll(notRemoveList);

        for (int i = 0; i < notRemoveList.size(); i++) {
            new OrdersCleaning(notRemoveList.get(i), removeList.get(i).getOrdersList())
                    .start();
        }
        previousImportSave();
        this.dispose();
    }

    private void previousImportSave() throws IOException {
        File archive = new File(System.getProperty("user.dir") + "\\PreviousImport.bin");
        if (!Files.exists(archive.toPath())) {
            Files.createFile(archive.toPath());
        }

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(archive));
        for (int i = 0; i < removeList.size(); i++) {
            for (int j = 0; j < removeList.get(i).getOrdersList().size(); j++) {
                out.write((removeList.get(i).getOrdersList().get(j) + "\r\n").getBytes());
            }
        }
        out.close();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void initBaseComponents(int yGlobal) {
        exitBtn = new JButton("Отмена");
        exitBtn.setFocusPainted(false);
        exitBtn.setFont(new Font("Times New Roman", 0, 14));
        exitBtn.setSize(115, ELEMENT_HEIGHT);
        exitBtn.addActionListener(this::exitBtnActionPerformed);

        completeBtn = new JButton("Завершить обмен");
        completeBtn.setFocusPainted(false);
        completeBtn.setFont(new Font("Times New Roman", 1, 14));
        completeBtn.setSize(155, ELEMENT_HEIGHT);
        completeBtn.addActionListener((evt) -> {
            try {
                this.completeBtnActionPerformed(evt);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Ошибка записи в архив", JOptionPane.ERROR_MESSAGE);
            }
        });

        if (yGlobal > WINDOW_MAX_HEIGHT) {
            scrollPane = new JScrollPane(globalPanel);
            this.add(scrollPane, null);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            globalPanel.setSize(new Dimension(WINDOW_WIDTH, yGlobal - ELEMENT_PADDING));
            globalPanel.setPreferredSize(new Dimension(globalPanel.getWidth(), globalPanel.getHeight()));
            scrollPane.setSize(globalPanel.getWidth() + 19, WINDOW_MAX_HEIGHT);
            this.setSize(scrollPane.getWidth() + 16, scrollPane.getHeight() + 68);

            exitBtn.setLocation(5, WINDOW_MAX_HEIGHT + ELEMENT_PADDING);
            completeBtn.setLocation(130, WINDOW_MAX_HEIGHT + ELEMENT_PADDING);
        } else {
            if (yGlobal == 0) {
                yGlobal = ELEMENT_PADDING;
            }

            globalPanel.setSize(new Dimension(WINDOW_WIDTH, yGlobal - ELEMENT_PADDING));
            this.setSize(globalPanel.getWidth() + 16, globalPanel.getHeight() + 68);
            exitBtn.setLocation(5, yGlobal);
            completeBtn.setLocation(130, yGlobal);
        }
    }

    private String extractOrderName(String str) {
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return m.group(1);
        } else {
            return "Ошибка чтения строки: <" + str + ">";
        }
    }

    // Объявление swing-компонент
    private JCheckBox[] singleBox, headerBox;
    private JButton completeBtn, exitBtn;
    private JScrollPane scrollPane;
    private JPanel globalPanel;
    // Объявление констант
    private static final int WINDOW_MAX_HEIGHT = 600;                             //Максимальная высота окна PrintFrame (на случай если заказов будет слишком много)
    private static final int WINDOW_WIDTH = 290;                                  //Стандартная ширина окна PrintFrame
    private static final int ELEMENT_HEIGHT = 23;                                 //Стандартная высота элементов (JButton, JCheckBox)
    private static final int ELEMENT_PADDING = 3;                                 //Стандартный отступ между элементами
    // Объявление остальных переменных
    private final LinkedList<ActiveDepartment> doPrintList;                         //Список импортированных заказов (загруженных)
    private final LinkedList<ActiveDepartment> notRemoveList = new LinkedList();    //Список оставшихся заказов (которые нельзя удалять)
    private final LinkedList<ActiveDepartment> removeList = new LinkedList();       //Список заказов на удаление
    private final LinkedList<JCheckBox[]> singleBoxList = new LinkedList<>();       //Список МАССИВОВ singleBox[] (замена ДВУМЕРНОМУ массиву)  
    private final Pattern p = Pattern.compile("(\\d+/\\d+)\\p{Space}+(\\d+.\\d+.\\d+)\\p{Space}(\\d+:\\d+:\\d+)");
}
