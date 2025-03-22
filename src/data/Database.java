package data;

import models.Employee;
import models.Visitor;

import java.util.*;

public class Database {
    public static final Map<String, Employee> empDB = new HashMap<>();
    public static final Map<String, Visitor> visitorDB = new HashMap<>();
    public static final Map<String, String> preApprovedList = new HashMap<>();
    public static final Map<String, List<Visitor>> visitorLog = new HashMap<>();

    static {
        empDB.put("Alice", new Employee("E101", "Alice", "HR"));
        empDB.put("Bob", new Employee("E102", "Bob", "IT"));
    }
}
