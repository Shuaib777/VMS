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
            System.out.println("❌ Employee not found.");
            return;
        }

        while (true) {
            System.out.println("\n👨‍💼 Employee Options:");
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
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void processVisitorApproval(Employee emp) {
        System.out.print("Enter Visitor Name for Approval: ");
        String vName = scanner.nextLine();

        // Fetch visitor from Database
        Visitor visitor = Database.visitorDB.get(vName);

        if (visitor == null) {
            System.out.println("❌ Visitor not found.");
            return;
        }

        employeeService.processVisitorApproval(emp, visitor);
    }

    private void preApproveVisitor(Employee emp) {
        System.out.print("Enter Visitor Name for Pre-Approval: ");
        String vName = scanner.nextLine();
        System.out.print("Enter Time Slot: ");
        String timeSlot = scanner.nextLine();

        employeeService.preApproveVisitor(emp, vName, timeSlot);
    }
}
