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
            System.out.println("1. Pre-Approve Visitor");
            System.out.println("2. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> preApproveVisitor(emp);
                case 2 -> {
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

        if (currentCount >= Database.preApproveLimit) {
            System.out.println("Pre-approval limit reached for " + emp.getName() + ". Maximum allowed: "
                    + Database.preApproveLimit);
            return;
        }

        System.out.println("\nVisitor Pre-Approval Process");

        System.out.print("Enter Visitor Name: ");
        String vName = scanner.nextLine().trim();
        while (vName.isEmpty()) {
            System.out.print("Visitor Name cannot be empty! Enter Visitor Name: ");
            vName = scanner.nextLine().trim();
        }

        System.out.print("Enter Time Slot (HH:mm - HH:mm): ");
        String timeSlot = scanner.nextLine().trim();
        while (!timeSlot.matches("\\d{2}:\\d{2} - \\d{2}:\\d{2}")) {
            System.out.print("Invalid format! Please enter time slot as HH:mm - HH:mm: ");
            timeSlot = scanner.nextLine().trim();
        }

        System.out.print("Enter Visitor's Company Name (Press Enter to skip): ");
        String companyName = scanner.nextLine().trim();
        companyName = companyName.isEmpty() ? "N/A" : companyName;

        System.out.print("Enter Visitor's Contact Info: ");
        String contactInfo = scanner.nextLine().trim();

        String photoPath;
        while (true) {
            System.out.print("Enter Visitor's Photo: ");
            photoPath = scanner.nextLine().trim();
            if (!photoPath.isEmpty())
                break;
            System.out.println("!!Photo Path cannot be empty. Please enter a valid path.");
        }

        employeeService.preApproveVisitor(emp, vName, timeSlot, companyName, photoPath, contactInfo);
    }
}
