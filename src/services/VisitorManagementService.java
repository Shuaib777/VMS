package services;

import models.*;

import java.util.*;

public class VisitorManagementService {
    private Map<String, Employee> empDB = new HashMap<>();
    private Map<String, Visitor> visitorDB = new HashMap<>();
    private Map<String, String> preApprovedList = new HashMap<>();
    private Map<String, List<Visitor>> visitorLog = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public VisitorManagementService() {
        empDB.put("Alice", new Employee("E101", "Alice", "HR"));
        empDB.put("Bob", new Employee("E102", "Bob", "IT"));
    }

    public void runSystem() {
        Security sec1 = new Security("S201", "SecurityGuard");
        Admin admin = new Admin("A301", "System Admin");

        while (true) {
            System.out.println("\nSelect User:");
            System.out.println("1. Security");
            System.out.println("2. Employee");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    securityMenu(sec1);
                    break;
                case 2:
                    employeeMenu();
                    break;
                case 3:
                    adminMenu(admin);
                    break;
                case 4:
                    System.out.println("Exiting System...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /** Security Menu **/
    private void securityMenu(Security sec) {
        while (true) {
            System.out.println("\nSecurity Options:");
            System.out.println("1. Register & Notify Employee");
            System.out.println("2. Check-in Visitor");
            System.out.println("3. Pre-Approved Check-in");
            System.out.println("4. Check-out Visitor");
            System.out.println("5. Show Visitors for a Date");
            System.out.println("6. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerVisitor(sec);
                    break;
                case 2:
                    checkInVisitor();
                    break;
                case 3:
                    preApprovedCheckIn(sec);
                    break;
                case 4:
                    checkOutVisitor();
                    break;
                case 5:
                    showVisitorsForDate();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void registerVisitor(Security sec) {
        System.out.print("Enter Visitor Name: ");
        String vName = scanner.nextLine();
        System.out.print("Enter Purpose: ");
        String purpose = scanner.nextLine();
        System.out.print("Enter Employee Name: ");
        String empName = scanner.nextLine();

        String visitDate = java.time.LocalDate.now().toString();
        System.out.println("📅 Visit Date: " + visitDate + " (Automatically Set)");

        Visitor visitor = sec.registerVisitor(vName, purpose, empName, visitDate, empDB);
        if (visitor != null && empDB.containsKey(empName)) {
            visitorLog.computeIfAbsent(visitDate, k -> new ArrayList<>()).add(visitor);
            System.out.println("Notifying Employee: " + empName + "...");
            employeeApprovalMenu(visitor, empDB.get(empName));
        }
    }

    private void checkInVisitor() {
        System.out.print("Enter Visitor Name for Check-in: ");
        String vName = scanner.nextLine();
        if (visitorDB.containsKey(vName)) {
            visitorDB.get(vName).checkIn();
        } else {
            System.out.println("Visitor not found or not approved.");
        }
    }

    private void preApprovedCheckIn(Security sec) {
        System.out.print("Enter Visitor Name for Pre-Approved Check-in: ");
        String vName = scanner.nextLine();
        sec.preApprovedCheckIn(vName, preApprovedList);
    }

    private void checkOutVisitor() {
        System.out.print("Enter Visitor Name for Check-out: ");
        String vName = scanner.nextLine();
        if (visitorDB.containsKey(vName)) {
            visitorDB.get(vName).checkOut();
        } else {
            System.out.println("Visitor not found.");
        }
    }

    private void showVisitorsForDate() {
        System.out.print("Enter Date (YYYY-MM-DD) to View Visitors: ");
        String date = scanner.nextLine();

        if (visitorLog.containsKey(date)) {
            System.out.println("\nVisitors on " + date + ":");
            for (Visitor v : visitorLog.get(date)) {
                v.displayInfo();
            }
        } else {
            System.out.println("No visitors found for this date.");
        }
    }

    /** Employee Menu **/
    private void employeeMenu() {
        System.out.print("Enter Employee Name: ");
        String eName = scanner.nextLine();
        if (!empDB.containsKey(eName)) {
            System.out.println("Employee not found.");
            return;
        }

        Employee emp = empDB.get(eName);
        while (true) {
            System.out.println("\nEmployee Options:");
            System.out.println("1. Approve/Deny Visitor");
            System.out.println("2. Pre-Approve Visitor");
            System.out.println("3. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    processVisitorApproval(emp);
                    break;
                case 2:
                    preApproveVisitor(emp);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void processVisitorApproval(Employee emp) {
        System.out.print("Enter Visitor Name for Approval: ");
        String vName = scanner.nextLine();
        if (!visitorDB.containsKey(vName)) {
            System.out.println("Visitor not found.");
            return;
        }

        Visitor visitor = visitorDB.get(vName);
        System.out.print("Approve visitor? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            emp.approveVisitor(visitor);
            visitorDB.put(visitor.name, visitor);
            System.out.println("✅ Visitor Approved - ePass: " + visitor.getEPass());
        } else {
            emp.denyVisitor(visitor);
        }
    }

    private void employeeApprovalMenu(Visitor visitor, Employee emp) {
        System.out.println("\nEmployee " + emp.name + " - Approve/Deny Visitor: " + visitor.name);
        System.out.print("Approve visitor? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            emp.approveVisitor(visitor);
            visitorDB.put(visitor.name, visitor);
            System.out.println("✅ Visitor Approved - ePass: " + visitor.getEPass());
        } else {
            emp.denyVisitor(visitor);
        }
    }

    /** ✅ Fixed Missing Method: Pre-Approve Visitor **/
    private void preApproveVisitor(Employee emp) {
        System.out.print("Enter Visitor Name for Pre-Approval: ");
        String vName = scanner.nextLine();
        System.out.print("Enter Time Slot: ");
        String timeSlot = scanner.nextLine();

        String ePass = "EPASS-" + UUID.randomUUID().toString().substring(0, 5);
        preApprovedList.put(vName, ePass);
        System.out.println("✅ Pre-Approved Visitor " + vName + " for " + timeSlot + " - ePass: " + ePass);
    }

    /** Admin Menu **/
    private void adminMenu(Admin admin) {
        System.out.print("Enter new Pre-Approval Limit: ");
        int limit = scanner.nextInt();
        scanner.nextLine();
        admin.setPreApproveLimit(limit);
    }
}
