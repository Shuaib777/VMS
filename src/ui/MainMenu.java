package ui;

import models.Admin;
import models.Security;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final Security security = new Security("S201", "SecurityGuard");
    private final Admin admin = new Admin("A301", "System Admin");

    public void showMainMenu() {
        while (true) {
            System.out.println("\nSelect User:");
            System.out.println("1. Security");
            System.out.println("2. Employee");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> new SecurityMenu().showSecurityMenu(security);
                case 2 -> new EmployeeMenu().showEmployeeMenu();
                case 3 -> new AdminMenu().showAdminMenu(admin);
                case 4 -> {
                    System.out.println("Exiting System...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
