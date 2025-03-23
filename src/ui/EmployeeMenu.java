package ui;

import models.Employee;
import models.Visitor;
import services.EmployeeService;
import data.Database;
import java.util.Scanner;

public class EmployeeMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final EmployeeService employeeService = new EmployeeService();

    public void showEmployeeMenu() {
        System.out.print("Enter Employee Name: ");
        String eName = scanner.nextLine();

        // Fetch employee from Database
        Employee emp = Database.empDB.get(eName);

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        while (true) {
            System.out.println("\nEmployee Options:");
            System.out.println("1. Approve/Deny Visitor");
            System.out.println("2. Pre-Approve Visitor");
            System.out.println("3. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> processVisitorApproval(emp);
                case 2 -> preApproveVisitor(emp);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void processVisitorApproval(Employee emp) {
        System.out.print("Enter Visitor Name for Approval: ");
        String vName = scanner.nextLine();

        // Fetch visitor from Database
        Visitor visitor = Database.visitorDB.get(vName);

        if (visitor == null) {
            System.out.println("Visitor not found.");
            return;
        }

        employeeService.processVisitorApproval(emp, visitor);
    }

    private void preApproveVisitor(Employee emp) {
        int currentCount = Database.preApprovalCount.getOrDefault(emp.getId(), 0);

        // Check if the limit is reached
        if (currentCount >= Database.preApproveLimit) {
            System.out.println("Pre-approval limit reached for " + emp.getName() + ". Maximum allowed: "
                    + Database.preApproveLimit);
            return;
        }

        System.out.println("\nVisitor Pre-Approval Process");

        // Get Visitor Name
        System.out.print("Enter Visitor Name: ");
        String vName = scanner.nextLine().trim();
        while (vName.isEmpty()) {
            System.out.print("Visitor Name cannot be empty! Enter Visitor Name: ");
            vName = scanner.nextLine().trim();
        }

        // Get Time Slot
        System.out.print("Enter Time Slot (HH:mm - HH:mm): ");
        String timeSlot = scanner.nextLine().trim();
        while (!timeSlot.matches("\\d{2}:\\d{2} - \\d{2}:\\d{2}")) {
            System.out.print("Invalid format! Please enter time slot as HH:mm - HH:mm: ");
            timeSlot = scanner.nextLine().trim();
        }

        // Get Company Name (Optional)
        System.out.print("Enter Visitor's Company Name (Press Enter to skip): ");
        String companyName = scanner.nextLine().trim();
        companyName = companyName.isEmpty() ? "N/A" : companyName;

        // Get Contact Information (Optional)
        System.out.print("Enter Visitor's Contact Info (Press Enter to skip): ");
        String contactInfo = scanner.nextLine().trim();
        contactInfo = contactInfo.isEmpty() ? "N/A" : contactInfo;

        // Process the pre-approval request
        employeeService.preApproveVisitor(emp, vName, timeSlot, companyName, contactInfo);
    }
}
