import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
//@WebServlet("/RegisterServletPath")
@WebServlet(name="RegisterServlet", urlPatterns={"/RegisterServletPath"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Inide doGet method");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Inside doPost method");
		PrintWriter out = response.getWriter();
		out.print("<html><h3>It is coming from RegisterServlet.java</h3></html>");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String city = request.getParameter("city");
		String dob = request.getParameter("dob");
		
		System.out.println(name + email + mobile + city + dob);
		
		/*write the logic for input validation*/
		String errorMsg = null;
		if(name == null || name.equals("")) {
			errorMsg = "Name can't be empty";
		}
		if(email == null || email.equals("")) {
			errorMsg = "Email can't be empty";
		}
		if(mobile == null || mobile.equals("")) {
			errorMsg = "Mobile number can't be empty";
		}
		if(city == null || city.equals("")) {
			errorMsg = "City name can't be empty";
		}
		if(dob == null || dob.equals("")) {
			errorMsg = "Date of birth can't be empty";
		}
		
		System.out.println("Line 64");
		
		//when the text field is not null, this code statement won't run
		if(errorMsg != null) {
			System.out.println("Line 67");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
			PrintWriter out1 = response.getWriter();
			out1.println("<font color=red>" +errorMsg+"</font>");
			rd.include(request, response);
		}
		
	//	System.out.println("Line 73");
		else {
			System.out.println("Line 73");
			Connection con = (Connection)getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			System.out.println("Line 79");
			
			
			try{
				System.out.println("Line 81");
				ps = con.prepareStatement("insert into customers(name,email,mobile,city,dob) values (?,?,?,?,?)");
				System.out.println("Line 82");
				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, mobile);
				ps.setString(4, city);
				ps.setString(5, dob);
				System.out.println("Line 87");	
				ps.execute();
				
				//forward to login page to login
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
				PrintWriter out1 = response.getWriter();
				out1.println("<font color=green>Registration Successful, please login.</font>");
				rd.include(request, response);
				
			}
			catch(SQLException e) {
				e.printStackTrace();
				//logger.error("Database connection problem");
				throw new ServletException("DB Connection Problem");
			}
			finally {
				try {
					ps.close();
				}
				catch(SQLException e) {
					System.out.println("Inside second catch block");
					//logger.error("SQLException is closing PreparedStatement");
				}
			}
		}
		
		
		//doGet(request, response);
	}
	

}
