package models;

public abstract class User {
    protected String id;
    public String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void displayInfo();
}
