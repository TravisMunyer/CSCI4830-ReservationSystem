

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import java.security.SecureRandom;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String username = request.getParameter("txtName");
        String pwd = request.getParameter("password");
        
        //response.setContentType("text/html");            
        //PrintWriter out = response.getWriter();
        
        /**
        String title = "Display Employee Credentials";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##CCCCFF\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");
		 */

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(Credentials.GetDbString(), "root", Credentials.GetDBPassword());
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * FROM Employees WHERE Username=?;";
        
        try {

            statement1 = connection.prepareStatement(sql);
            String theUserName = username;                    
            statement1.setString(1, theUserName);
     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //out.println("<table border=1 width=50% height=30%>");
        //out.println("<tr><th>Id</th><th>Username</th><th>Salt</th><th>PasswordSalted</th>");
        try {
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("id");
                String name = rs.getString("Username");
                String salt = rs.getString("Salt");
                String pwdsalted = rs.getString("PasswordSalted");
                
               // out.println("<tr><td>" + id + "</td><td>" + name + "</td><td>" + Salt + "</td><td>" + PasswordSalted + "</td></tr>");
                
                String securePassword = Cryptography.get_SHA_512_SecurePassword(pwd, salt);
                
                System.out.println(salt);
                System.out.println(securePassword);
                System.out.println(pwdsalted);
                System.out.println(pwd);
                
                if (pwdsalted.equals(securePassword))
                {
                	response.sendRedirect("employeeViewRes.html");
                	System.out.println("login successful");
                }
                else
                {
                	response.sendRedirect("login.jsp");
                	System.out.println("login failed");
                }
            }
            
           // out.println("</body></html>");
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}