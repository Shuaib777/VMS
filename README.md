# Visitor Management System

## Overview

The **Visitor Management System (VMS)** is a **Java-based application** designed to efficiently manage visitor registrations, approvals, check-ins, and check-outs. It provides a structured and **secure workflow** for handling visitors within an organization, ensuring proper authorization and tracking.

---

## User Types

1. **Security Personnel**

   - Registers new visitors and notifies employees for approval.
   - Manages visitor **check-ins and check-outs**.
   - Allows **pre-approved check-ins** based on set time slots.
   - Views visitor logs for a given date.

2. **Employees**

   - Approves or denies visitor requests.
   - Pre-approves visitors by assigning a **time slot**.
   - Can only pre-approve a **limited number of visitors** (as set by Admin).

3. **Admin**
   - Sets a **pre-approval limit** for employees.
   - Oversees system settings and controls employee permissions.

---

## Features

- **Visitor Registration & Approval Workflow** – Security registers visitors, and employees approve or deny requests.
- **Pre-Approval System** – Employees can pre-approve visitors with specific time slots.
- **Check-in & Check-out System** – Security verifies **ePass IDs** before entry and exit.
- **Visitor Tracking & Logs** – Records visitor details, timestamps, and status.
- **Admin Control Panel** – Allows Admins to set **pre-approval limits** for employees.

---

## System Architecture

### 1. Core Modules & Components

#### A. Models (Data Layer)

- **`User`** – Base class for all users.
- **`Visitor`** – Stores visitor details (ePass, company, contact, photo, timestamps).
- **`Employee`** – Handles visitor approvals and pre-approvals.
- **`Security`** – Manages check-ins, check-outs, and visitor logs.
- **`Admin`** – Sets system-wide configurations, including pre-approval limits.

#### B. Services (Business Logic Layer)

- **`SecurityService`** – Handles visitor registration, check-ins, check-outs, and logging.
- **`EmployeeService`** – Manages visitor approvals and pre-approvals.
- **`AdminService`** – Allows admin to configure system settings (e.g., pre-approval limits).

#### C. UI Layer (Menu & Interaction)

- **`MainMenu`** – User selection (Security, Employee, Admin).
- **`SecurityMenu`** – Security-related operations (register visitor, check-in, check-out).
- **`EmployeeMenu`** – Employee-related tasks (approve/deny visitors, pre-approve).
- **`AdminMenu`** – Admin-related configurations (set pre-approval limits).

#### D. Data Layer (In-Memory Storage)

- `empDB` – Stores employees.
- `visitorDB` – Stores registered visitors.
- `preApprovedList` – Maps **ePass ID** to pre-approved time slots.
- `visitorLog` – Stores visitor logs by date.
- `preApprovalCount` – Tracks how many visitors each employee has pre-approved.

---

## Workflow: Visitor Registration & Check-In Process

### Step 1: Visitor Registration

1. **Security registers a visitor**, entering name, company, purpose, and employee to visit.
2. **Employee receives notification** and decides whether to **approve or deny** the request.
   - **Approved:** System generates an **ePass** and stores it in the database.
   - **Denied:** Visitor is **not allowed entry**.

### Step 2: Pre-Approval System (Optional)

1. **Employee pre-approves a visitor** and assigns a **time slot**.
2. The system stores **ePass ID** mapped to the time slot.

### Step 3: Check-in Process

1. Visitor arrives and presents the **ePass ID** to security.
2. System **verifies the visitor's status**:
   - **Regular Visitor** – Approved visitors are checked in.
   - **Pre-Approved Visitor** – Time slot validation is performed.
3. If verification passes, the visitor is **logged in**.

### Step 4: Check-out Process

1. Visitor presents **ePass ID** at the exit.
2. Security logs the **check-out time**.

### Step 5: Admin Control

- **Admin sets limits** on how many visitors an employee can pre-approve.
- Employees cannot exceed their assigned **pre-approval quota**.

---

## Database Schema (In-Memory Storage Example)

| Table Name           | Fields                                                                   | Description                        |
| -------------------- | ------------------------------------------------------------------------ | ---------------------------------- |
| **Users**            | `id, name, role`                                                         | Stores common user details.        |
| **Visitors**         | `id, name, purpose, company, contact, ePass, photo, check-in, check-out` | Stores visitor details.            |
| **Employees**        | `id, name, department`                                                   | Employees who approve visitors.    |
| **PreApprovedList**  | `ePass, timeSlot`                                                        | Stores ePass mapped to time slots. |
| **VisitorLog**       | `date, list<Visitor>`                                                    | Stores all visitors per date.      |
| **PreApprovalCount** | `employeeId, count`                                                      | Tracks pre-approvals per employee. |

---

## Tech Stack

- **Language:** Java
- **Data Storage:** In-Memory `HashMap` (Can be extended to MySQL/MongoDB)
- **Architecture:** Layered MVC-like (Separation of Concerns: Models, Services, UI)

---

## Example Use Case

### **Scenario: Visitor Appointment & Entry**

#### **Use Case: John (Visitor) visits Alice (Employee) in IT Department**

1. **John arrives at the office** and wants to meet Alice.
2. **Security registers John** in the system.
3. **Alice gets a notification** and **approves the visit**.
4. **System generates an ePass:**

   ```
   ePass ID   : EPASS-5D5D9-J
   Name       : John
   Company    : xyz
   Contact    : 123456789
   Visiting   : Alice
   Purpose    : Business Meeting
   Date       : 2025-03-23
   Check-in   : 07:18:09
   Photo      : https://picsum.photos/seed/picsum/200/300
   ```

5. After the meeting, **John checks out**, and the system **logs his exit time**.

---

## Future Enhancements

- **Convert to a Web App** (Spring Boot + React).
- **Database Integration** (MySQL, PostgreSQL, MongoDB).
- **QR Code for ePass** (Auto-scan for check-in).
- **Mobile App for Security Check-ins**.

---

## Conclusion

The **Visitor Management System (VMS)** provides a **secure, structured, and scalable** way to handle visitor registration, approvals, and tracking. By enforcing **employee approvals, security verifications, and admin controls**, the system enhances **security and efficiency** within organizations.

---

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd visitor-management-system
   ```
3. Compile and Run:
   ```bash
   javac Main.java
   java Main
   ```
4. Follow the **on-screen menu** for operations.

---
