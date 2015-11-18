package impulsexchangeserver;

public class Department {

    public Department(String number, DepartmentStatus status) {
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
    
    private String number;
    private DepartmentStatus status;
}
