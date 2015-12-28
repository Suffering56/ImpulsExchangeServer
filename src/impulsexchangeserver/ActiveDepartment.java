package impulsexchangeserver;

import java.util.LinkedList;

public class ActiveDepartment {

    public ActiveDepartment() {
        detailsList = new LinkedList<>();
    }

    public LinkedList<String> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(LinkedList<String> detailsList) {
        this.detailsList = detailsList;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private String departmentName;            //Номер отдела
    private LinkedList <String> detailsList;    //Список заказов
}
