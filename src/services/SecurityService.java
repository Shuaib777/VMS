package services;

import models.Visitor;
import models.Employee;
import data.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class SecurityService {
    private final EmployeeService employeeService = new EmployeeService();

    /** ✅ Registers a visitor (Now Includes Company, Contact & Photo) */
    public Visitor registerVisitor(String vName, String purpose, String empName, String photoPath,
            String companyName, String contactInfo) {
        String visitDate = LocalDate.now().toString();
        String checkInTime = LocalTime.now().toString().substring(0, 8);

        System.out.println("📅 Visit Date: " + visitDate + " (Automatically Set)");

        // Check if employee exists
        if (!Database.empDB.containsKey(empName)) {
            System.out.println("❌ Employee not found: " + empName);
            return null;
        }

        Employee emp = Database.empDB.get(empName);
        String visitorId = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // Shortened ID
        Visitor visitor = new Visitor(visitorId, vName, purpose, empName, visitDate,
                photoPath, companyName, contactInfo);

        // Ask for approval from the employee
        System.out.println("📢 Notifying Employee: " + emp.getName() + " for visitor approval...");
        employeeService.processVisitorApproval(emp, visitor);

        if (visitor.isApproved()) {
            // ✅ Generate ePass and set check-in time
            String ePass = generateEPass(visitor);
            visitor.setEPass(ePass);
            visitor.setCheckInTime(checkInTime);

            // ✅ Store visitor in the database using `ePass ID`
            Database.visitorDB.put(ePass, visitor);
            Database.visitorLog.computeIfAbsent(visitDate, k -> new ArrayList<>()).add(visitor);

            printEPass(visitor);
            return visitor;
        } else {
            System.out.println("❌ Visitor " + vName + " was NOT approved. Registration denied.");
            return null;
        }
    }

    /** ✅ Generates a structured ePass ID */
    private String generateEPass(Visitor visitor) {
        return "EPASS-" + visitor.getId().substring(0, 5) + "-" + visitor.getName().toUpperCase().charAt(0);
    }

    /** ✅ Prints the visitor ePass details (Now Includes Company & Contact Info) */
    private void printEPass(Visitor visitor) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" ✅ VISITOR ePASS GENERATED  ");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" 🎫 ePass ID   : " + visitor.getEPass());
        System.out.println(" 👤 Name       : " + visitor.getName());
        System.out.println(" 🏢 Company    : " + (visitor.getCompanyName() != null ? visitor.getCompanyName() : "N/A"));
        System.out.println(" 📞 Contact    : " + (visitor.getContactInfo() != null ? visitor.getContactInfo() : "N/A"));
        System.out.println(" 🏢 Visiting   : " + visitor.getVisitingEmp());
        System.out.println(" 🎯 Purpose    : " + visitor.getPurpose());
        System.out.println(" 📅 Date       : " + visitor.getDate());
        System.out.println(" ⏰ Check-in   : " + visitor.getCheckInTime());
        System.out.println(
                " 📸 Photo      : " + (visitor.getPhotoPath() != null ? visitor.getPhotoPath() : "No Photo Available"));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public void checkInVisitor(String ePass) {
        if (Database.visitorDB.containsKey(ePass)) {
            Visitor visitor = Database.visitorDB.get(ePass);
            visitor.checkIn();
        } else {
            System.out.println("❌ ePass ID not found or visitor not approved.");
        }
    }

    public void checkOutVisitor(String ePass) {
        if (Database.visitorDB.containsKey(ePass)) {
            Visitor visitor = Database.visitorDB.get(ePass);
            visitor.checkOut();
        } else {
            System.out.println("❌ ePass ID not found.");
        }
    }

    /** ✅ Shows visitors for a given date (Now Includes Company & Contact Info) */
    public void showVisitorsForDate(String date) {
        if (Database.visitorLog.containsKey(date) && !Database.visitorLog.get(date).isEmpty()) {
            System.out.println("\n📅 Visitors on " + date + ":");
            System.out.println(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.printf(" %-10s | %-15s | %-15s | %-15s | %-12s | %-10s | %-8s | %-8s | %-20s%n",
                    "E-Pass ID", "Name", "Company", "Contact Info", "Visiting Emp", "Purpose", "Check-in", "Check-out",
                    "Photo Path");
            System.out.println(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            for (Visitor v : Database.visitorLog.get(date)) {
                System.out.printf(" %-10s | %-15s | %-15s | %-15s | %-12s | %-10s | %-8s | %-8s | %-20s%n",
                        v.getEPass(), v.getName(),
                        (v.getCompanyName() != null ? v.getCompanyName() : "N/A"),
                        (v.getContactInfo() != null ? v.getContactInfo() : "N/A"),
                        v.getVisitingEmp(), v.getPurpose(),
                        (v.getCheckInTime() != null ? v.getCheckInTime() : "N/A"),
                        (v.getCheckOutTime() != null ? v.getCheckOutTime() : "N/A"),
                        (v.getPhotoPath() != null ? v.getPhotoPath() : "No Photo"));
            }
            System.out.println(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        } else {
            System.out.println("\n📅 Visitors on " + date + ": ❌ No visitors found.");
        }
    }

    public void preApprovedCheckIn(String ePass) {
        if (!Database.preApprovedList.containsKey(ePass)) {
            System.out.println("❌ No valid pre-approval found for ePass: " + ePass);
            return;
        }

        // ✅ Get pre-approved time slot
        String approvedTimeSlot = Database.preApprovedList.get(ePass);
        String[] timeRange = approvedTimeSlot.split(" - "); // Example: "10:00 - 12:00"

        // ✅ Get current time
        LocalTime currentTime = LocalTime.now();

        // ✅ Parse time slot to validate
        LocalTime startTime = LocalTime.parse(timeRange[0]);
        LocalTime endTime = LocalTime.parse(timeRange[1]);

        if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
            System.out.println(
                    "❌ Access Denied! Current time is outside the pre-approved time slot: " + approvedTimeSlot);
            return;
        }

        // ✅ Check if visitor exists in the database
        if (!Database.visitorDB.containsKey(ePass)) {
            System.out.println("❌ Visitor record not found for ePass: " + ePass);
            return;
        }

        Visitor visitor = Database.visitorDB.get(ePass);
        visitor.approve();
        visitor.checkIn();
        String visitDate = LocalDate.now().toString();
        Database.visitorLog.computeIfAbsent(visitDate, k -> new ArrayList<>()).add(visitor); // ✅ Mark visitor as
                                                                                             // checked-in

        // ✅ Print visitor's ePass details
        printEPass(visitor);
    }
}
