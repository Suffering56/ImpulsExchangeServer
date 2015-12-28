package impulsexchangeserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
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

        residualListInit();
        initPanelComponents();
        setLocationRelativeTo(null);
    }

    private void initPanelComponents() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
            headerBox[i].setSize(85, ELEMENT_HEIGHT);
            headerBox[i].setLocation(5, yLocal);
            headerBox[i].setActionCommand(String.valueOf(i));
            headerBox[i].addActionListener(this::headerBoxActionPerformed);
            yLocal = yLocal + ELEMENT_HEIGHT + ELEMENT_PADDING;
            localPanel.add(headerBox[i]);                                               //Добавление HeaderBox ======= Конец

            singleBox = new JCheckBox[doPrintList.get(i).getOrdersList().size()];        //Добавление SingleBox ======= Начало
            for (int j = 0; j < doPrintList.get(i).getOrdersList().size(); j++) {
                String cleansedOrder = doPrintList.get(i).getOrdersList().get(j);
                singleBox[j] = new JCheckBox(cleansedOrder);
                singleBox[j].setSize(260, ELEMENT_HEIGHT);
                singleBox[j].setLocation(25, yLocal);
                singleBox[j].setActionCommand(String.valueOf(i) + "_" + String.valueOf(j) + "_" + String.valueOf(cleansedOrder));
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

        exitBtn = new JButton("Отмена");
        exitBtn.setFont(new Font("Times New Roman", 0, 14));
        exitBtn.setSize(115, ELEMENT_HEIGHT);
        exitBtn.addActionListener(this::exitBtnActionPerformed);

        completeBtn = new JButton("Завершить обмен");
        completeBtn.setFont(new Font("Times New Roman", 1, 14));
        completeBtn.setSize(155, ELEMENT_HEIGHT);
        completeBtn.addActionListener(this::completeBtnActionPerformed);

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

        this.add(exitBtn);
        this.add(completeBtn);
        this.repaint();
    }

    private void residualListInit() {
        for (int i = 0; i < doPrintList.size(); i++) {
            residualList.add(new ActiveDepartment());
            residualList.getLast().setDepartmentName(doPrintList.get(i).getDepartmentName());
            residualList.getLast().getOrdersList().addAll(doPrintList.get(i).getOrdersList());
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

        if (headerBox[i].isSelected()) {
            residualList.get(i).getOrdersList().clear();                                   //Очищаем residualList если стоит галочка в headerBox
        } else {
            residualList.get(i).getOrdersList().clear();
            residualList.get(i).getOrdersList().addAll(doPrintList.get(i).getOrdersList()); //Добавляем все заказы в residualList галочки НЕТ в headerBox
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
                residualList.get(i).getOrdersList().remove(order);
            } else {
                int index = j + 1 - (singleBoxList.get(i).length
                        - residualList.get(i).getOrdersList().size());          //это, чтобы элемент ...
                if (index < 0) {                                                //... после восстановления вставал на свое место ...
                    index = 0;                                                  //... а это защита от IndexBoundException
                }
                residualList.get(i).getOrdersList().add(index, order);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Неверный номер заказа");
        }
    }

    private void completeBtnActionPerformed(ActionEvent evt) {
        doPrintList.clear();
        doPrintList.addAll(residualList);
        for (int i = 0; i < residualList.size(); i++) {
            new OrdersCleaning(residualList.get(i))
                    .start();
        }
        this.dispose();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
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
    private final LinkedList<ActiveDepartment> doPrintList;                        //Список импортированных заказов (загруженных)
    private final LinkedList<ActiveDepartment> residualList = new LinkedList();  //Список оставшихся заказов (которые нельзя удалять)
    private final LinkedList<JCheckBox[]> singleBoxList = new LinkedList<>();    //Список МАССИВОВ singleBox[] (замена ДВУМЕРНОМУ массиву)  
    private final Pattern p = Pattern.compile("(\\d+/\\d+)\\p{Space}+(\\d+.\\d+.\\d+)\\p{Space}(\\d+:\\d+:\\d+)");
}
