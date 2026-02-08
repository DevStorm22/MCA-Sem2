package jdbcCurdOperationWithGuiPackage;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Blueprint of the Employee Data
class Employee {
	int Id;
	private String  Name, Address, Email, Contact;
	
	Employee(int Id, String Name, String Address, String Email, String Contact) {
		this.Id = Id;
		this.Name = Name;
		this.Address = Address;
		this.Email = Email;
		this.Contact = Contact;
	}
	
	int getId() {
		return Id;
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


public class JDBCCURDOperationWithGUI {
	
	// Common elements for the functions in this particular class
	static String[] columns = {"Employee ID", "Employee Name", "Employee Address", "Employee Email", "Employee Contact"};
	
	static Connection DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCGUIDB", "root", "kaushik");
				return connection;
			} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return null;
	}
	//Static function to retrieve data from the database
	static ArrayList<Employee> retrieveDataFromDatabase() {
		ArrayList<Employee> EmployeeArrayList = new ArrayList<>();
		
		try {
			Connection con = DBConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee");
			while(rs.next()) {
				Employee employee = new Employee(rs.getInt("eid"), rs.getString("ename"), rs.getString("eaddress"), rs.getString("eemail"), rs.getString("econtact"));
				EmployeeArrayList.add(employee);
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			System.err.print(e);
		}
		
		return EmployeeArrayList;
	}
	
	//Displaying data retrieved from the database to the JTable
	static JFrame displayingDataInTable(ArrayList<Employee> EmployeeArrayList) {
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		JFrame tableFrame = new JFrame();
		JTable dataTable = new JTable();
		JLabel mainTitle = new JLabel("Data table");
		JScrollPane scrollPane = new JScrollPane();
		
		try {
			//Setting data in the model(DefaultTableModel)
			for(Employee employee: EmployeeArrayList) {
				tableModel.addRow(new Object[] {
						String.valueOf(employee.getId()),
						employee.getName(),
						employee.getAddress(),
						employee.getEmail(),
						employee.getContact()
				});
			}
			
			// Defining data model to the table
			dataTable = new JTable(tableModel);
			
			// Adding data table to the scroll pane
			scrollPane = new JScrollPane(dataTable);
			
			// Setting border layout
			tableFrame.setLayout(new BorderLayout());
			
			// Adding GUI components to the frame
			tableFrame.add(mainTitle, BorderLayout.NORTH);
			tableFrame.add(scrollPane, BorderLayout.CENTER);
			
			// Configuring tableFrame
			tableFrame.setSize(700, 500);
			tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Use DISPOSE_ON_CLOSE to close the tableFrame only, not the mainFrame.
			
		} catch(Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		// Returning frame
		return tableFrame;
	}
	
	//Inserting data in the table
	static ArrayList<Employee> InsertionOfEmployee(int Id, String Name, String Address, String Email, String Contact) {
		ArrayList<Employee> EmployeeArrayList = new ArrayList<>();
		String query = "insert into employee value(?, ?, ?, ?, ?)";
		try {
			Connection con = DBConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);
		} catch(Exception e) {
			System.err.print(e);
		}
		
		// Continue from here
		return EmployeeArrayList;
	}
	
	// Main Entry point
	public static void main(String[] args) {
		ArrayList<Employee> EmployeeArrayList = new ArrayList<>();
		EmployeeArrayList = retrieveDataFromDatabase();
		JFrame mainFrame = new JFrame("JDBC + GUI Demo");
		JFrame tableFrame = displayingDataInTable(EmployeeArrayList);
		JButton viewData = new JButton("View Data");
		
		// Action listener
		viewData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tableFrame.setSize(700, 500);
				tableFrame.setVisible(true);
			}
		});

		// Configuring the main frame
		mainFrame.setLayout(new FlowLayout());
		
		// Adding component to the frame
		mainFrame.add(viewData);
		
		//Setting visibility
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
	}
}
