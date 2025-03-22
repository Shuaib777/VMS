package services;

import models.Visitor;
import models.Employee;
import data.Database;

import java.time.LocalDate;
import java.util.*;

public class SecurityService {
    private final EmployeeService employeeService = new EmployeeService();

    /** ✅ Registers a visitor */
    public Visitor registerVisitor(String vName, String purpose, String empName) {

        String visitDate = LocalDate.now().toString();
        System.out.println("📅 Visit Date: " + visitDate + " (Automatically Set)");

        // Check if employee exists
        if (!Database.empDB.containsKey(empName)) {
            System.out.println("❌ Employee not found: " + empName);
            return null;
        }

        models.Employee emp = Database.empDB.get(empName);
        String visitorId = UUID.randomUUID().toString();
        Visitor visitor = new Visitor(visitorId, vName, purpose, empName, visitDate);

        // Ask for approval from the employee
        System.out.println("📢 Notifying Employee: " + emp.name + " for visitor approval...");
        employeeService.processVisitorApproval(emp, visitor);

        if (visitor.isApproved()) {
            Database.visitorDB.put(vName, visitor);
            Database.visitorLog.computeIfAbsent(visitDate, k -> new ArrayList<>()).add(visitor);
            System.out.println("✅ Visitor Approved & Registered: " + visitor.getName() + " for date: " + visitDate);
            return visitor;
        } else {
            System.out.println("❌ Visitor " + vName + " was NOT approved. Registration denied.");
            return null;
        }
    }

    public void checkInVisitor(String vName) {
        if (Database.visitorDB.containsKey(vName)) {
            Database.visitorDB.get(vName).checkIn();
        } else {
            System.out.println("❌ Visitor not found or not approved.");
        }
    }

    public void checkOutVisitor(String vName) {
        if (Database.visitorDB.containsKey(vName)) {
            Database.visitorDB.get(vName).checkOut();
        } else {
            System.out.println("❌ Visitor not found.");
        }
    }

    public void showVisitorsForDate(String date) {
        if (Database.visitorLog.containsKey(date)) {
            System.out.println("\n📅 Visitors on " + date + ":");
            for (Visitor v : Database.visitorLog.get(date)) {
                v.displayInfo();
            }
        } else {
            System.out.println("❌ No visitors found for this date.");
        }
    }

    public void preApprovedCheckIn(String visitorName) {
        if (Database.preApprovedList.containsKey(visitorName)) {
            System.out.println("✅ Pre-approved visitor " + visitorName + " checked in with ePass: "
                    + Database.preApprovedList.get(visitorName));
        } else {
            System.out.println("❌ No valid pre-approval found for " + visitorName);
        }
    }
}
