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
	
		public DBConnectionManager(String dbURL, String username, String password) throws 
		ClassNotFoundException, SQLException {
			
				Class.forName("oracle.jdbc.driver.OracleDriver");
				this.connection = DriverManager.getConnection(dbURL,username,password);
		}
		public Connection getConnection() {
			return this.connection;
		}
}
