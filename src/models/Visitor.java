package models;

public class Visitor extends User {
    private String purpose;
    private String visitingEmp;
    private String checkInTime;
    private String checkOutTime;
    private boolean approved;
    private String ePass;
    private String date;
    private String photoPath;
    private String companyName; // ✅ Stores visitor's company name
    private String contactInfo; // ✅ Stores visitor's contact information

    public Visitor(String id, String name, String purpose, String visitingEmp, String date,
            String photoPath, String companyName, String contactInfo) {
        super(id, name);
        this.purpose = purpose;
        this.visitingEmp = visitingEmp;
        this.approved = false;
        this.ePass = null;
        this.date = date;
        this.checkInTime = null;
        this.checkOutTime = null;
        this.photoPath = photoPath;
        this.companyName = companyName;
        this.contactInfo = contactInfo;
    }

    /** ✅ Approves visitor and generates an ePass */
    public void approve() {
        this.approved = true;
    }

    /** ✅ Checks if the visitor is approved */
    public boolean isApproved() {
        return approved;
    }

    /** ✅ Generates a custom ePass */
    public void generateEPass(String pass) {
        this.ePass = pass;
    }

    /** ✅ Handles check-in process */
    public void checkIn() {
        if (!approved) {
            System.out.println("❌ Access Denied! Visitor is not approved.");
            return;
        }
        this.checkInTime = java.time.LocalTime.now().toString().substring(0, 8);
        System.out.println("✅ " + name + " checked in at " + checkInTime);
    }

    /** ✅ Handles check-out process */
    public void checkOut() {
        if (checkInTime == null) {
            System.out.println("❌ Visitor has not checked in yet.");
            return;
        }
        this.checkOutTime = java.time.LocalTime.now().toString().substring(0, 8);
        System.out.println("✅ " + name + " checked out at " + checkOutTime);
    }

    /** ✅ Getters */
    public String getVisitingEmp() {
        return visitingEmp;
    }

    public String getEPass() {
        return ePass;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDate() {
        return date;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public String getId() {
        return id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getCompanyName() {
        return companyName;
    } // ✅ Get company name

    public String getContactInfo() {
        return contactInfo;
    } // ✅ Get contact info

    /** ✅ Setters */
    public void setEPass(String ePass) {
        this.ePass = ePass;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    } // ✅ Set company name

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    } // ✅ Set contact info

    /** ✅ Displays visitor info including photo, company, and contact details */
    @Override
    public void displayInfo() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" 🔹 VISITOR DETAILS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" 🔹 Visitor ID : " + id);
        System.out.println(" 👤 Name       : " + name);
        System.out.println(" 🎯 Purpose    : " + purpose);
        System.out.println(" 🏢 Visiting   : " + visitingEmp);
        System.out.println(" 🏢 Company    : " + (companyName != null ? companyName : "N/A"));
        System.out.println(" 📞 Contact    : " + (contactInfo != null ? contactInfo : "N/A"));
        System.out.println(" 📅 Date       : " + date);

        if (approved) {
            System.out.println(" ✅ Approved - ePass: " + ePass);
        } else {
            System.out.println(" ❌ Not Approved");
        }

        if (checkInTime != null) {
            System.out.println(" ⏰ Check-in Time  : " + checkInTime);
        }
        if (checkOutTime != null) {
            System.out.println(" 🕒 Check-out Time : " + checkOutTime);
        }
        if (photoPath != null) {
            System.out.println(" 📸 Photo Stored At: " + photoPath);
        } else {
            System.out.println(" ❌ No Photo Available");
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}
