package services;

import models.Employee;
import models.Visitor;
import data.Database;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class EmployeeService {
    private final Scanner scanner = new Scanner(System.in);

    public void processVisitorApproval(Employee emp, Visitor visitor) {
        System.out.print("Approve visitor " + visitor.getName() + "? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            emp.approveVisitor(visitor);
        } else {
            emp.denyVisitor(visitor);
        }
    }

    public void preApproveVisitor(Employee emp, String visitorName, String timeSlot, String companyName,
            String contactInfo) {

        int currentCount = Database.preApprovalCount.getOrDefault(emp.getId(), 0);

        String ePass = "EPASS-" + UUID.randomUUID().toString().substring(0, 5);

        Database.preApprovedList.put(ePass, timeSlot);
        Database.preApprovalCount.put(emp.getId(), currentCount + 1);

        Visitor visitor = new Visitor(
                ePass,
                visitorName,
                "Pre-Approved Visit",
                emp.getName(),
                LocalDate.now().toString(),
                null,
                companyName,
                contactInfo);
        visitor.setEPass(ePass);

        Database.visitorDB.put(ePass, visitor);

        System.out.println("\nPre-Approved Visitor Successfully");
        System.out.println("---------------------------------");
        System.out.println(" ePass: " + ePass);
        System.out.println(" Name: " + visitorName);
        System.out.println(" Time Slot: " + timeSlot);
        System.out.println(" Company: " + (companyName != null && !companyName.isEmpty() ? companyName : "N/A"));
        System.out.println(" Contact: " + (contactInfo != null && !contactInfo.isEmpty() ? contactInfo : "N/A"));
        System.out.println("---------------------------------");
    }
}
