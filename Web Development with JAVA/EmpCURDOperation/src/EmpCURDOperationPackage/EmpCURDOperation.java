package EmpCURDOperationPackage;

/*
 * ------------------------------------------------------------
 * Project      : Employee CRUD Operation using JDBC
 * Description  : Menu-driven application demonstrating 
 *                Insert, Update, Delete, Search, Count and 
 *                Display operations using MySQL and JDBC.
 *
 * Author       : DevStorm
 * Generated On : 12th February 2026
 *
 * Technology   : Java, JDBC, MySQL
 * Architecture : Model + DAO + Menu-driven UI
 * ------------------------------------------------------------
 */

import java.sql.*;   // Contains JDBC classes (Connection, PreparedStatement, ResultSet, etc.)
import java.util.*;  // Contains Scanner class

/*
 * Employee Class (Model / Entity Class)
 * --------------------------------------
 * This class represents one row of the "employee" table in the database.
 * Each object of Employee corresponds to one record in the table.
 * Table creation query: - create table employee (empid int primary key, empname varchar(30) not null, empdes varchar(20) not null, empsal float not null);
 */
class Employee {

    // Private data members (Encapsulation)
    private int empid;
    private String empname;
    private String empdes;
    private float empsal;

    // Default Constructor
    public Employee() {}

    // Parameterized Constructor
    public Employee(int empid, String empname, String empdes, float empsal) {
        this.empid = empid;
        this.empname = empname;
        this.empdes = empdes;
        this.empsal = empsal;
    }

    // Getter Methods (Used to retrieve values)
    public int getEmpid() { return empid; }
    public String getEmpname() { return empname; }
    public String getEmpdes() { return empdes; }
    public float getEmpsal() { return empsal; }

    // Setter Methods (Used to modify values)
    public void setEmpid(int empid) { this.empid = empid; }
    public void setEmpname(String empname) { this.empname = empname; }
    public void setEmpdes(String empdes) { this.empdes = empdes; }
    public void setEmpsal(float empsal) { this.empsal = empsal; }
}


/*
 * EmpCURDOperation Class
 * -----------------------
 * This class handles all database operations (DAO Layer).
 * It performs CRUD operations using JDBC.
 */
public class EmpCURDOperation {

    // Database configuration constants
    private static final String URL = "jdbc:mysql://localhost:3306/kaushikdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin";

    /*
     * getConnection()
     * ---------------
     * Establishes a connection with MySQL database.
     * DriverManager automatically loads MySQL driver (JDBC 4.0+).
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // ===================== INSERT OPERATION =====================
    /*
     * insertEmployee()
     * ----------------
     * Inserts a new employee record into the database.
     * Uses PreparedStatement to prevent SQL Injection.
     */
    public void insertEmployee(Employee emp) throws SQLException {

        String sql = "INSERT INTO employee VALUES (?, ?, ?, ?)";

        // try-with-resources automatically closes Connection and PreparedStatement
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Setting values in place of ? placeholders
            ps.setInt(1, emp.getEmpid());
            ps.setString(2, emp.getEmpname());
            ps.setString(3, emp.getEmpdes());
            ps.setFloat(4, emp.getEmpsal());

            ps.executeUpdate();  // Executes INSERT
            System.out.println("Employee inserted successfully.");
        }
    }


    // ===================== UPDATE OPERATION =====================
    /*
     * updateEmployee()
     * ----------------
     * Updates name, designation, and salary based on empid.
     */
    public void updateEmployee(Employee emp) throws SQLException {

        String sql = "UPDATE employee SET empname=?, empdes=?, empsal=? WHERE empid=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getEmpname());
            ps.setString(2, emp.getEmpdes());
            ps.setFloat(3, emp.getEmpsal());
            ps.setInt(4, emp.getEmpid());

            int rows = ps.executeUpdate();  // Returns number of affected rows

            if (rows > 0)
                System.out.println("Employee updated successfully.");
            else
                System.out.println("Employee not found.");
        }
    }


    // ===================== DELETE OPERATION =====================
    /*
     * deleteEmployee()
     * ----------------
     * Deletes employee record based on empid.
     */
    public void deleteEmployee(int empid) throws SQLException {

        String sql = "DELETE FROM employee WHERE empid=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empid);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee deleted successfully.");
            else
                System.out.println("Employee not found.");
        }
    }


    // ===================== SEARCH OPERATION =====================
    /*
     * searchEmployee()
     * ----------------
     * Retrieves and displays employee details using empid.
     */
    public void searchEmployee(int empid) throws SQLException {

        String sql = "SELECT * FROM employee WHERE empid=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empid);

            ResultSet rs = ps.executeQuery();  // Used for SELECT queries

            if (rs.next()) {   // Moves cursor to first row
                System.out.println("\nEmployee Details:");
                System.out.println("ID: " + rs.getInt("empid"));
                System.out.println("Name: " + rs.getString("empname"));
                System.out.println("Designation: " + rs.getString("empdes"));
                System.out.println("Salary: " + rs.getFloat("empsal"));
            } else {
                System.out.println("Employee not found.");
            }
        }
    }


    // ===================== COUNT OPERATION =====================
    /*
     * countEmployees()
     * ----------------
     * Displays total number of employees in the table.
     */
    public void countEmployees() throws SQLException {

        String sql = "SELECT COUNT(*) FROM employee";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("Total Employees: " + rs.getInt(1));
            }
        }
    }


    // ===================== DISPLAY ALL =====================
    /*
     * displayAllEmployees()
     * ---------------------
     * Displays all records from employee table.
     */
    public void displayAllEmployees() throws SQLException {

        String sql = "SELECT * FROM employee";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nAll Employees:");

            while (rs.next()) {  // Loop through all rows
                System.out.println(
                        rs.getInt("empid") + " | " +
                        rs.getString("empname") + " | " +
                        rs.getString("empdes") + " | " +
                        rs.getFloat("empsal")
                );
            }
        }
    }


    // ===================== MAIN METHOD (Menu Driven) =====================
    /*
     * Main method handles user interaction.
     * Calls DAO methods based on user choice.
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EmpCURDOperation dao = new EmpCURDOperation();

        int choice;

        do {
            System.out.println("\n1. Insert");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. Search");
            System.out.println("5. Count");
            System.out.println("6. Display All");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // Clears buffer

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Designation: ");
                        String des = sc.nextLine();

                        System.out.print("Enter Salary: ");
                        float sal = sc.nextFloat();

                        Employee emp = new Employee(id, name, des, sal);
                        dao.insertEmployee(emp);
                        break;

                    case 2:
                        System.out.print("Enter ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        String uname = sc.nextLine();

                        System.out.print("Enter New Designation: ");
                        String udes = sc.nextLine();

                        System.out.print("Enter New Salary: ");
                        float usal = sc.nextFloat();

                        Employee updatedEmp = new Employee(uid, uname, udes, usal);
                        dao.updateEmployee(updatedEmp);
                        break;

                    case 3:
                        System.out.print("Enter ID to delete: ");
                        dao.deleteEmployee(sc.nextInt());
                        break;

                    case 4:
                        System.out.print("Enter ID to search: ");
                        dao.searchEmployee(sc.nextInt());
                        break;

                    case 5:
                        dao.countEmployees();
                        break;

                    case 6:
                        dao.displayAllEmployees();
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }

        } while (choice != 7);

        sc.close(); // Close scanner at end of program
    }
}