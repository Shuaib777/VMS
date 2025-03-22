package models;

import java.util.Map;
import java.util.UUID;

public class Security extends User {
    public Security(String id, String name) {
        super(id, name);
    }

    /** ✅ Register a visitor & ensure visit date is included */
    public Visitor registerVisitor(String name, String purpose, String visitingEmp, String date,
            Map<String, Employee> empDB) {
        if (!empDB.containsKey(visitingEmp)) {
            System.out.println("❌ Employee not found: " + visitingEmp);
            return null;
        }
        String visitorId = UUID.randomUUID().toString();
        Visitor visitor = new Visitor(visitorId, name, purpose, visitingEmp, date);
        System.out.println("✅ Visitor Registered: " + visitor.name + " for date: " + date);
        return visitor;
    }

    /** ✅ Check-out visitor */
    public void checkOutVisitor(Visitor visitor) {
        visitor.checkOut();
    }

    /** ✅ Handle pre-approved visitor check-in */
    public void preApprovedCheckIn(String visitorName, Map<String, String> preApprovedList) {
        if (preApprovedList.containsKey(visitorName)) {
            System.out.println("✅ Pre-approved visitor " + visitorName + " checked in with ePass: "
                    + preApprovedList.get(visitorName));
        } else {
            System.out.println("❌ No valid pre-approval found for " + visitorName);
        }
    }

    /** ✅ Display security info */
    @Override
    public void displayInfo() {
        System.out.println("🔒 Security ID: " + id + ", Name: " + name);
    }
}
