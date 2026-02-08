package jdbcGuiPackage;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

class Employee {
	private int Id;
	private String Name, Address, Email, Contact;
	
	Employee(int Id, String Name, String Address, String Email, String Contact) {
		this.Id = Id;
		this.Name = Name;
		this.Address = Address;
		this.Email = Email;
		this.Contact = Contact;
	}
	
	String getId() {
		return String.valueOf(Id); //Converting Integer value to String
	}
	
	String getName() {
		return Name;
	}
	
	String getAddress() {
		return Address;
	}
	
	String getEmail() {
		return Email;
	}
	
	String getContact() {
		return Contact;
	}
}

public class JDBCGUI {
	public static void main(String[] args) {
		
		//Array of the heading of the table
		String[] columns = {"Id", "Name", "Address", "Email", "Contact"};
		
		//Swing Component initialization
		JFrame mainFrame = new JFrame("Main Frame");
		JLabel titleLabel = new JLabel("Database Table");
		JLabel errorLabel = new JLabel("");
		
		//Store Data in the ArrayList
		ArrayList<Employee> EmpAL = new ArrayList<>();
		
		try {
			String query = "select * from employee";
			Class.forName("com.mysql.cj.jdbc.Driver"); // Load and Register JDBC Driver
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCGUIDB", "root" ,"kaushik");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			int i = 0;
			
			while(rs.next()) { //While data is there in the database table
				int Id;
				String Name, Address, Email, Contact;
				
				//Storing data in the individual variable
				Id = rs.getInt("eid");
				Name = rs.getString("ename");
				Address = rs.getString("eaddress");
				Email = rs.getString("eemail");
				Contact = rs.getString("econtact");
				
				//Print data on the console
				System.out.println("Employee " + (++i) + " Details:\nEmployee Id: " + Id + "\nEmployee Name: " + Name + "Employee Address:" + Address + "\nEmployee E-mail: " + Email + "\nEmployee Contact no.: " + Contact + "\n");
				
				//Store data in the instance
				Employee e = new Employee(Id, Name, Address, Email, Contact);
				EmpAL.add(e); //Adding data into the Employee ArrayList
			}
			
			
			
			//Closing the resources
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			errorLabel = new JLabel(e.getMessage());
		}
		
		//Default Table Model
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		//Adding the data into table model
		for(Employee emp : EmpAL) {
			tableModel.addRow(new Object[] {
					emp.getId(),
					emp.getName(),
					emp.getAddress(),
					emp.getEmail(),
					emp.getContact()
			});
		}
		
		//Initializing the table with table model
		JTable dataTable = new JTable(tableModel);
		
		//Setting data table in the scroll pane
		JScrollPane scrollPane = new JScrollPane(dataTable);
		
		//Adding elements to the frame
		mainFrame.add(titleLabel);
		mainFrame.add(errorLabel);
		mainFrame.add(scrollPane);
		
		//Setting close operation
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When you click on the red cross button on the top right corner, Frame gets close
		
		//Setting size of the frame
		mainFrame.setSize(500, 500);
		
		//Setting the visibility mode of the frame
		mainFrame.setVisible(true); //Frame visible on if this value is true.
	}
}
