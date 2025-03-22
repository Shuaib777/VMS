package models;

public class Admin extends User {
    private int preApproveLimit = 5;

    public Admin(String id, String name) {
        super(id, name);
    }

    public void setPreApproveLimit(int limit) {
        this.preApproveLimit = limit;
        System.out.println("Pre-approval limit set to " + limit);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin ID: " + id + ", Name: " + name);
    }
}
