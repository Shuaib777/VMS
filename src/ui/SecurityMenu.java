package ui;

import models.Security;
import services.SecurityService;
import java.util.Scanner;

public class SecurityMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final SecurityService securityService = new SecurityService();

    public void showSecurityMenu(Security sec) {
        while (true) {
            System.out.println("\n🔒 Security Options:");
            System.out.println("1. Register Visitor");
            System.out.println("2. Check-in Visitor");
            System.out.println("3. Pre-Approved Check-in");
            System.out.println("4. Check-out Visitor");
            System.out.println("5. Show Visitors for a Date");
            System.out.println("6. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerVisitor();
                case 2 -> checkInVisitor();
                case 3 -> preApprovedCheckIn();
                case 4 -> checkOutVisitor();
                case 5 -> showVisitorsForDate();
                case 6 -> {
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    /** ✅ Registers a visitor (Now Includes Company, Contact & Photo) */
    private void registerVisitor() {
        System.out.print("Enter Visitor Name: ");
        String vName = scanner.nextLine();
        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();
        System.out.print("Enter Employee Name to Visit: ");
        String empName = scanner.nextLine();

        // ✅ Prompt for visitor's company name (Optional)
        System.out.print("Enter Visitor's Company Name (or press Enter to skip): ");
        String companyName = scanner.nextLine();
        if (companyName.trim().isEmpty()) {
            companyName = null;
        }

        // ✅ Prompt for visitor's contact information (Optional)
        System.out.print("Enter Visitor's Contact Info (or press Enter to skip): ");
        String contactInfo = scanner.nextLine();
        if (contactInfo.trim().isEmpty()) {
            contactInfo = null;
        }

        // ✅ Prompt for visitor's photo (Optional)
        System.out.print("Enter Visitor Photo Path (or press Enter to skip): ");
        String photoPath = scanner.nextLine();
        if (photoPath.trim().isEmpty()) {
            photoPath = null;
        }

        securityService.registerVisitor(vName, purpose, empName, photoPath, companyName, contactInfo);
    }

    /** ✅ Handles visitor check-in using ePass ID */
    private void checkInVisitor() {
        System.out.print("Enter E-pass ID for Check-in: ");
        securityService.checkInVisitor(scanner.nextLine());
    }

    /** ✅ Handles visitor check-out using ePass ID */
    private void checkOutVisitor() {
        System.out.print("Enter E-pass ID for Check-out: ");
        securityService.checkOutVisitor(scanner.nextLine());
    }

    /** ✅ Handles pre-approved visitor check-in using ePass ID */
    private void preApprovedCheckIn() {
        System.out.print("Enter E-pass ID for Pre-Approved Check-in: ");
        securityService.preApprovedCheckIn(scanner.nextLine());
    }

    /** ✅ Displays visitors for a selected date */
    private void showVisitorsForDate() {
        System.out.print("Enter Date (YYYY-MM-DD) to View Visitors: ");
        securityService.showVisitorsForDate(scanner.nextLine());
    }
}
