
public class Staff extends Person {
    private int staffId;
    private String department;

    public Staff(String name, int age, int staffId, String department) {
        super(name, age);
        this.staffId = staffId;
        this.department = department;
    }


    public String getRole() {
        return "Staff";
    }

    public int getStaffId() {
        return staffId;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
               "Age: " + getAge() + "\n" +
               "Staff ID: " + staffId + "\n" +
               "Department: " + department + "\n" +
               "Role: " + getRole();
    }
}
