import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banking.util.Customer;


@WebServlet("/LoginServletPath")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String errorMsg = null;
		
		if(email == null || email.equals("")) {
			errorMsg = "Email can't be empty";
		}
		if(password == null || password.equals("")) {
			errorMsg = "Password can't me empty";
		}
		
		if(errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" +errorMsg+ "</font>");
			rd.include(request, response);	
		}
		
		else {
			Connection con = (Connection)getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = con.prepareStatement("select name, email, mobile, city, dob from customers where email=? and password=? limit 1");
				ps.setString(0, email);
				ps.setString(1, password);
				rs = ps.executeQuery();
				
				if(rs != null && rs.next()) {
					Customer customer = new Customer(rs.getString("name"), 
							rs.getString("email"), rs.getString("mobile"), 
							rs.getString("city"), rs.getString("dob"));
					
					HttpSession session = request.getSession();
					session.setAttribute("Customer", customer);
					response.sendRedirect("home.jsp");
				}
				else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login/html");
					PrintWriter out = response.getWriter();
					out.println("<font color=red>User not found </font>");
					rd.include(request, response);
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
				throw new ServletException("DB Connection Problem");
			}
			finally {
				try {
					rs.close(); //closing result set
					ps.close(); //closing prepared statement
				}
				catch(SQLException e) {
					System.out.println("SQLException is closing ResultSet and PreparedStatement");
				}
			}
		}
	}

}
