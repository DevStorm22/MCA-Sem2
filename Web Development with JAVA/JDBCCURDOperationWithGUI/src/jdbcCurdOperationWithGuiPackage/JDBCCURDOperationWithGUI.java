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
	
	// GUI frame for entering the data
	static JFrame dataInsertionForm() {
		//Declaring the GUI Component
		JFrame insertionFormFrame = new JFrame("Add employee");
		JLabel titleLabel,idLabel, nameLabel, addressLabel, emailLabel, contactLabel;
		JTextField idInput, nameInput, addressInput, emailInput, contactInput;
		JButton submitButton = new JButton("Submit");
		
		//Initializing the GUI Component
		titleLabel = new JLabel("AdInsert Employee Data");
		idLabel = new JLabel("Enter id:");
		nameLabel = new JLabel("Enter name:");
		addressLabel = new JLabel("Enter address:");
		emailLabel = new JLabel("Enter email:");
		contactLabel = new JLabel("Enter contact:");
		idInput = new JTextField();
		nameInput = new JTextField();
		addressInput = new JTextField();
		emailInput = new JTextField();
		contactInput = new JTextField();
		
		//Action Listener to create the Employee
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				
				String query = "insert into employee value(?, ?, ?, ?, ?)";

				try {
					int Id;
					String Name, Address, Email, Contact;
					
					Id = Integer.parseInt(idInput.getText());
					Name = nameInput.getText();
					Address = addressInput.getText();
					Email = emailInput.getText();
					Contact = contactInput.getText();
					
					Connection con = DBConnection();
					PreparedStatement preparedStatement = con.prepareStatement(query);
					preparedStatement.setInt(1, Id);
					preparedStatement.setString(2, Name);
					preparedStatement.setString(3, Address);
					preparedStatement.setString(4, Email);
					preparedStatement.setString(5, Contact);
					preparedStatement.executeUpdate();
					
					//Alert Message
					JOptionPane.showMessageDialog(insertionFormFrame, "Employee Added");
					
					preparedStatement.close();
					con.close();
				} catch(Exception e) {
					System.err.print(e);
				}
			}
		});
		
		//Adding GUI component to the Frame
		insertionFormFrame.add(titleLabel);
		insertionFormFrame.add(idLabel);
		insertionFormFrame.add(idInput);
		insertionFormFrame.add(nameLabel);
		insertionFormFrame.add(nameInput);
		insertionFormFrame.add(addressLabel);
		insertionFormFrame.add(addressInput);
		insertionFormFrame.add(emailLabel);
		insertionFormFrame.add(emailInput);
		insertionFormFrame.add(contactLabel);
		insertionFormFrame.add(contactInput);
		insertionFormFrame.add(submitButton);
		
		// Configuring the Frame
		insertionFormFrame.setLayout(new GridLayout(7, 2));
		
		return insertionFormFrame;
	}
	
	// Main Entry point
	public static void main(String[] args) {
		ArrayList<Employee> EmployeeArrayList = new ArrayList<>();
		EmployeeArrayList = retrieveDataFromDatabase();
		JFrame mainFrame = new JFrame("JDBC + GUI Demo");
		JFrame tableFrame = displayingDataInTable(EmployeeArrayList);
		JFrame insertDataFrame = dataInsertionForm();
		JButton viewData = new JButton("View Data");
		JButton insertData = new JButton("Insert Data");
		
		// Action listeners
		viewData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tableFrame.setSize(700, 500);
				tableFrame.setVisible(true);
			}
		});
		
		insertData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				insertDataFrame.setSize(500, 700);
				insertDataFrame.setVisible(true);
			}
		});

		// Configuring the main frame
		mainFrame.setLayout(new FlowLayout());
		
		// Adding component to the frame
		mainFrame.add(viewData);
		mainFrame.add(insertData);
		
		//Setting visibility
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
	}
}
