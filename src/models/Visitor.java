package models;

public class Visitor extends User {
    private String purpose;
    private String visitingEmp;
    private String checkInTime;
    private String checkOutTime;
    private boolean approved;
    private String ePass;
    private String date; // Stores visit date

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

    /** Approves visitor and generates an ePass */
    public void approve() {
        this.approved = true;
        this.ePass = "EPASS-" + id.substring(0, 5); // Generate ePass
    }

    /** Generates a pre-approved ePass */
    public void generateEPass(String pass) {
        this.ePass = pass;
    }

    /** Checks if the visitor is pre-approved */
    public boolean isPreApproved() {
        return ePass != null;
    }

    /** Visitor check-in */
    public void checkIn() {
        if (!approved) {
            System.out.println("❌ Access Denied! Visitor is not approved.");
            return;
        }
        this.checkInTime = java.time.LocalTime.now().toString();
        System.out.println("✅ " + name + " checked in at " + checkInTime);
    }

    /** Visitor check-out */
    public void checkOut() {
        if (checkInTime == null) {
            System.out.println("❌ Visitor has not checked in yet.");
            return;
        }
        this.checkOutTime = java.time.LocalTime.now().toString();
        System.out.println("✅ " + name + " checked out at " + checkOutTime);
    }

    /** Get visiting employee name */
    public String getVisitingEmp() {
        return visitingEmp;
    }

    /** Get generated ePass */
    public String getEPass() {
        return ePass;
    }

    /** Get visit date */
    public String getDate() {
        return date;
    }

    /** Display visitor details */
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
