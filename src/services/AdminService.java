package services;

import models.Admin;
import java.util.Scanner;

public class AdminService {
    private final Scanner scanner = new Scanner(System.in);

    public void setPreApprovalLimit(Admin admin) {
        System.out.print("Enter new Pre-Approval Limit: ");
        int limit = scanner.nextInt();
        scanner.nextLine();
        admin.setPreApproveLimit(limit);
    }
}
