package services;

import models.Employee;
import models.Visitor;
import data.Database;

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
            System.out.println("✅ Visitor Approved - ePass: " + visitor.getEPass());
        } else {
            emp.denyVisitor(visitor);
        }
    }

    /** ✅ Pre-approves a visitor */
    public void preApproveVisitor(Employee emp, String visitorName, String timeSlot) {
        if (Database.preApprovedList.containsKey(visitorName)) {
            System.out.println("⚠️ Visitor " + visitorName + " is already pre-approved.");
            return;
        }
        String ePass = "EPASS-" + UUID.randomUUID().toString().substring(0, 5);
        Database.preApprovedList.put(visitorName, ePass);
        System.out.println("✅ Pre-Approved Visitor " + visitorName + " for " + timeSlot + " - ePass: " + ePass);
    }
}
