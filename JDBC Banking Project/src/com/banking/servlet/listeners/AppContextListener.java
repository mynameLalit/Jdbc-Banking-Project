package com.banking.servlet.listeners;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.banking.util.DBConnectionManager;

public class AppContextListener implements ServletContextListener {
	
		public void contextInitialized(ServletContextEvent servletContextEvent) {
			ServletContext ctx = servletContextEvent.getServletContext();
			System.out.println("Inside AppContextListener");
			//Initialize DB connection
			String url = ctx.getInitParameter("url");
			String username = ctx.getInitParameter("username");
			String password = ctx.getInitParameter("password");
			
			try {
				DBConnectionManager connectionManager = new DBConnectionManager(url, username, password);
				ctx.setAttribute("DBConnection", connectionManager.getConnection());
				System.out.println("DB Connection Intialized Successfully");
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public void contextDestroyed(ServletContextEvent servletContextEvent) {
			Connection con = (Connection)servletContextEvent.getServletContext().getAttribute("DBConnection");
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
}
