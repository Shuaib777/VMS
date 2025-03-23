package models;

public class Security extends User {
    public Security(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayInfo() {
        System.out.println("Security ID: " + id + ", Name: " + name);
    }
}
