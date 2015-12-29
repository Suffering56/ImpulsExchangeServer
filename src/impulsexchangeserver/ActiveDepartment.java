package impulsexchangeserver;

import java.util.LinkedList;

public class ActiveDepartment {

    public LinkedList<String> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(LinkedList<String> ordersList) {
        this.ordersList = ordersList;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private String departmentName;                                //Номер отдела
    private LinkedList<String> ordersList = new LinkedList<>();   //Список заказов
}
