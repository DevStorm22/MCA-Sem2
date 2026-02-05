package CURDOperationPackage;

import java.sql.*;

public class CURDOperation {

	public static void main(String[] args) {
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			String query = "Select * from Employee";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaushikdb", "root", "Admin");
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				System.out.println("Empid: " + rs.getInt("eid") + " | EmpName: " + rs.getString("ename") + " | EmpAdderss: " + rs.getString("eaddress"));
			}
			rs.close();
			st.close();
			con.close();
		}
		catch(ClassNotFoundException e) {
			System.err.println(e);
		}
		catch(SQLException e) {
			System.err.println(e);
		}
		finally {
			
		}
	}

}
