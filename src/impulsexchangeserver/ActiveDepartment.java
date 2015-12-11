package impulsexchangeserver;

import java.util.LinkedList;

public class ActiveDepartment {

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

    public boolean isUdpated() {
        return udpated;
    }

    public void setUdpated(boolean udpated) {
        this.udpated = udpated;
    }

    private boolean udpated = false;
    private String departmentNumber;
    private LinkedList<String> detailsList;
}
