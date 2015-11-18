package impulsexchangeserver;

import java.util.LinkedList;

public class Department {

    public Department(String number, DepartmentStatus status) {
        this.detailsList = new LinkedList();
        this.number = number;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DepartmentStatus getStatus() {
        return status;
    }

    public void setStatus(DepartmentStatus status) {
        this.status = status;
    }

    public LinkedList<String> getDetailsList() {
        return detailsList;
    }
    
    private String number;
    private DepartmentStatus status;
    private final LinkedList <String> detailsList;
}
