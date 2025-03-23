package models;

import data.Database;

public class Admin extends User {

    public Admin(String id, String name) {
        super(id, name);
    }

    public void setPreApproveLimit(int limit) {
        Database.preApproveLimit = limit;
        System.out.println("Pre-approval limit set to " + limit);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin ID: " + id + ", Name: " + name);
    }
}
