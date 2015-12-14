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

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    private String departmentNumber;
    private LinkedList<String> detailsList;
}
