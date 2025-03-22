package ui;

import models.Admin;
import services.AdminService;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AdminService adminService = new AdminService();

    public void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n⚙️ Admin Options:");
            System.out.println("1. Set Pre-Approval Limit");
            System.out.println("2. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> adminService.setPreApprovalLimit(admin);
                case 2 -> {
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }
}
