package services;

import models.Employee;
import models.Visitor;
import data.Database;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class EmployeeService {
    private final Scanner scanner = new Scanner(System.in);

    /** ✅ Approves or denies a visitor */
    public void processVisitorApproval(Employee emp, Visitor visitor) {
        System.out.print("Approve visitor " + visitor.getName() + "? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            emp.approveVisitor(visitor);
        } else {
            emp.denyVisitor(visitor);
        }
    }

    /** ✅ Pre-approves a visitor */
    public void preApproveVisitor(Employee emp, String visitorName, String timeSlot, String companyName,
            String contactInfo) {
        // ✅ Generate a unique ePass
        String ePass = "EPASS-" + UUID.randomUUID().toString().substring(0, 5);

        // ✅ Store pre-approval details
        Database.preApprovedList.put(ePass, timeSlot);

        // ✅ Create a visitor object with all necessary details
        Visitor visitor = new Visitor(
                ePass, // ePass ID (Used as Visitor ID)
                visitorName, // Visitor Name
                "Pre-Approved Visit", // Purpose
                emp.getName(), // Visiting Employee Name
                LocalDate.now().toString(), // Date (Auto-generated)
                null, // Photo Path (Optional, set later)
                companyName, // Visitor’s Company
                contactInfo // Visitor’s Contact Info
        );
        visitor.setEPass(ePass);

        // ✅ Store the visitor in the database
        Database.visitorDB.put(ePass, visitor);

        // ✅ Log pre-approval details
        System.out.println("\n✅ Pre-Approved Visitor Successfully");
        System.out.println(" 🎫 ePass: " + ePass);
        System.out.println(" 👤 Name: " + visitorName);
        System.out.println(" 🕒 Time Slot: " + timeSlot);
        System.out.println(" 🏢 Company: " + (companyName != null && !companyName.isEmpty() ? companyName : "N/A"));
        System.out.println(" 📞 Contact: " + (contactInfo != null && !contactInfo.isEmpty() ? contactInfo : "N/A"));
    }

}
