package CURDOperationMenuDrivenPackage;

import java.util.Scanner;
import java.sql.*;

public class CURDOperationMenuDriven {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaushikdb", "root", "Admin");
			Statement st = con.createStatement();
			ResultSet rs;
			int choice;
			do {
				System.out.print("1. View Table\n2. Add Employee\n3. Delete Employee\n4. Update Employee\n5. Exit\n\nEnter your choice:");
				choice = sc.nextInt();
				switch(choice) {
				case 1:
					rs = st.executeQuery("select * from employee");
					while(rs.next()) {
						System.out.println("Empid: " + rs.getInt("eid") + " | EmpName: " + rs.getString("ename") + " | EmpAdderss: " + rs.getString("eaddress"));
					}
					break;
				case 2:
					System.out.print("Enter Employee Id: ");
					int id = sc.nextInt();
					System.out.print("Enter Employee Name: ");
					String name = sc.nextLine();
					sc.next();
					System.out.print("Enter Employee Salary: ");
					float salary = sc.nextFloat();
					st.executeUpdate("insert into employee values(" + id + ", '" + name + "', " + salary + ")");
					System.out.println("Employee added");
					break;
				case 3:
					//System.out.print("Enter id to delete: ");
					//int id = sc.nextInt();
					break;
				case 4:
					break;
				}
				
			} while (choice != 5);
		}
		catch(SQLException e) {
			System.err.print(e);
		}
		catch(ClassNotFoundException e) {
			System.err.print(e);
		}
	}

}
