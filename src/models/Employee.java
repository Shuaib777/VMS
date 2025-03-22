package models;

import java.util.Map;
import java.util.UUID;

public class Employee extends User {
    private String department;

    public Employee(String id, String name, String department) {
        super(id, name);
        this.department = department;
    }

    public boolean approveVisitor(Visitor visitor) {
        System.out.println("Visitor " + visitor.name + " has been approved by " + name);
        visitor.approve();
        return true;
    }

    public void denyVisitor(Visitor visitor) {
        System.out.println("Visitor " + visitor.name + " has been denied by " + name);
    }

    public void preApproveVisitor(String visitorName, String timeSlot, Map<String, String> preApprovedList) {
        String ePass = UUID.randomUUID().toString();
        preApprovedList.put(visitorName, ePass);
        System.out.println("Pre-approved visitor " + visitorName + " by " + name + " for time slot " + timeSlot);
    }

    @Override
    public void displayInfo() {
        System.out.println("Employee ID: " + id + ", Name: " + name + ", Department: " + department);
    }
}
