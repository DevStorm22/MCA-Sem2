package com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnCheck {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaushikdb", "root", "Admin");
		String result;
		if(con.isValid(100)) {
			result = "Connection Establised";
			System.out.println(result);
		}
		result = "Connection Closed after 1000 ms";
		System.out.println(result);
	}
}