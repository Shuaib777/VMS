package models;

public class Employee extends User {
    private String department;

    public Employee(String id, String name, String department) {
        super(id, name);
        this.department = department;
    }

    public boolean approveVisitor(Visitor visitor) {
        System.out.println("✅ Visitor " + visitor.getName() + " has been approved by " + name);
        visitor.approve();
        return true;
    }

    public void denyVisitor(Visitor visitor) {
        System.out.println("❌ Visitor " + visitor.getName() + " has been denied by " + name);
    }

    public String getName() {
        return name;
    }

    @Override
    public void displayInfo() {
        System.out.println("Employee ID: " + id + ", Name: " + name + ", Department: " + department);
    }
}
