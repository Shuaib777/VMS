package ui;

import models.Security;
import services.SecurityService;
import java.util.Scanner;

public class SecurityMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final SecurityService securityService = new SecurityService();

    public void showSecurityMenu(Security sec) {
        while (true) {
            System.out.println("\nSecurity Options:");
            System.out.println("1. Register Visitor");
            System.out.println("2. Pre-Approved Check-in");
            System.out.println("3. Check-out Visitor");
            System.out.println("4. Show Visitors for a Date");
            System.out.println("5. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerVisitor();
                case 2 -> preApprovedCheckIn();
                case 3 -> checkOutVisitor();
                case 4 -> showVisitorsForDate();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void registerVisitor() {
        String vName, purpose, empName, companyName, contactInfo, photoPath;

        while (true) {
            System.out.print("Enter Visitor Name: ");
            vName = scanner.nextLine().trim();
            if (!vName.isEmpty())
                break;
            System.out.println("!!Visitor Name cannot be empty. Please enter a valid name.");
        }

        while (true) {
            System.out.print("Enter Purpose: ");
            purpose = scanner.nextLine().trim();
            if (!purpose.isEmpty())
                break;
            System.out.println("!!Purpose cannot be empty. Please enter a valid purpose.");
        }

        while (true) {
            System.out.print("Enter Employee Name to Visit: ");
            empName = scanner.nextLine().trim();
            if (!empName.isEmpty())
                break;
            System.out.println("!!Employee Name cannot be empty. Please enter a valid name.");
        }

        System.out.print("Enter Visitor's Company Name (or press Enter to skip): ");
        companyName = scanner.nextLine().trim();
        companyName = companyName.isEmpty() ? "N/A" : companyName;

        while (true) {
            System.out.print("Enter Visitor's Contact Info: ");
            contactInfo = scanner.nextLine().trim();
            if (!contactInfo.isEmpty())
                break;
            System.out.println("!!Contact Info cannot be empty. Please enter a valid contact.");
        }

        while (true) {
            System.out.print("Enter Visitor's Photo: ");
            photoPath = scanner.nextLine().trim();
            if (!photoPath.isEmpty())
                break;
            System.out.println("!!Photo Path cannot be empty. Please enter a valid path.");
        }

        securityService.registerVisitor(vName, purpose, empName, photoPath, companyName, contactInfo);
    }

    private void checkOutVisitor() {
        System.out.print("Enter E-pass ID for Check-out: ");
        securityService.checkOutVisitor(scanner.nextLine());
    }

    private void preApprovedCheckIn() {
        System.out.print("Enter E-pass ID for Pre-Approved Check-in: ");
        securityService.preApprovedCheckIn(scanner.nextLine());
    }

    private void showVisitorsForDate() {
        System.out.print("Enter Date (YYYY-MM-DD) to View Visitors: ");
        securityService.showVisitorsForDate(scanner.nextLine());
    }
}
