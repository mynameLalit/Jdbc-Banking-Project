package com.banking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DBConnectionManager.java is a utility class for Oracle11g database connection and it has a method that
 * returns connection object. We will use this class for database connection and then set the connection
 * to servlet context attribute(web.xml) that other servlets can use.
 * */

public class DBConnectionManager {
	
	private Connection connection;
	
		public DBConnectionManager(String url, String username, String password) throws 
		ClassNotFoundException, SQLException {
			System.out.println("Inside DB Connection Manager");
				Class.forName("oracle.jdbc.OracleDriver");
				this.connection = DriverManager.getConnection(url,username,password);
		}
		public Connection getConnection() {
			return this.connection;
		}
}
