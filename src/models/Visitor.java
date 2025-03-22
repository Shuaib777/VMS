package models;

public class Visitor extends User {
    private String purpose;
    private String visitingEmp;
    private String checkInTime;
    private String checkOutTime;
    private boolean approved;
    private String ePass;
    private String date;

    public Visitor(String id, String name, String purpose, String visitingEmp, String date) {
        super(id, name);
        this.purpose = purpose;
        this.visitingEmp = visitingEmp;
        this.approved = false;
        this.ePass = null;
        this.date = date;
        this.checkInTime = null;
        this.checkOutTime = null;
    }

    public void approve() {
        this.approved = true;
        this.ePass = "EPASS-" + id.substring(0, 5);
    }

    public boolean isApproved() {
        return approved;
    }

    // see if it is required
    public void generateEPass(String pass) {
        this.ePass = pass;
    }

    public boolean isPreApproved() {
        return ePass != null;
    }

    public void checkIn() {
        if (!approved) {
            System.out.println("❌ Access Denied! Visitor is not approved.");
            return;
        }
        this.checkInTime = java.time.LocalTime.now().toString();
        System.out.println("✅ " + name + " checked in at " + checkInTime);
    }

    public void checkOut() {
        if (checkInTime == null) {
            System.out.println("❌ Visitor has not checked in yet.");
            return;
        }
        this.checkOutTime = java.time.LocalTime.now().toString();
        System.out.println("✅ " + name + " checked out at " + checkOutTime);
    }

    public String getVisitingEmp() {
        return visitingEmp;
    }

    public String getEPass() {
        return ePass;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    @Override
    public void displayInfo() {
        System.out.println("Visitor ID: " + id + ", Name: " + name + ", Purpose: " + purpose + ", Visiting: "
                + visitingEmp + ", Date: " + date);
        if (approved) {
            System.out.println("✅ Approved - ePass: " + ePass);
        } else {
            System.out.println("❌ Not Approved");
        }
        if (checkInTime != null) {
            System.out.println("🔵 Check-in Time: " + checkInTime);
        }
        if (checkOutTime != null) {
            System.out.println("🟢 Check-out Time: " + checkOutTime);
        }
    }
}
