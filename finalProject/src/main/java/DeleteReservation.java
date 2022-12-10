import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteReservation")
public class DeleteReservation extends HttpServlet {
    private static final long serialVersionUID = 1;

    public DeleteReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int resId = -1;
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String reservationId = request.getParameter("id");
        
        String title = "Reservation Cancellation";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##CCCCFF\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");
        
        // Validate Reservation Id.
        if (reservationId == null) {
        	out.println("Invalid Link.");
        	return;
        }
        
        try {
        	resId = Integer.parseInt(reservationId);
        	
        	if (resId < 0) {
        		throw new NumberFormatException();
        	}
        }
        catch (NumberFormatException e){
        	out.println("Invalid Link.");
        	return;
        }
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
          System.out.println("Where is your MySQL JDBC Driver?");
          e.printStackTrace();
          return;
        }
        
        try {
        	connection = DriverManager.getConnection(Credentials.GetDbString(), "root", Credentials.GetDBPassword());
        }
        catch (SQLException e2) {
        	System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
      
        System.out.println("SUCCESS!!!! You made it, take control of your database now!");
        System.out.println("Creating statement...");
        
        sql = "UPDATE Reservations SET IsCanceled = 1 WHERE ReservationId=?;";

        try {
        	statement = connection.prepareStatement(sql);
        	statement.setInt(1, resId);
        }
        catch (SQLException e2) {
        	e2.printStackTrace();
        	return;
        }

        try {
        	statement.executeUpdate();
            out.println("Reservation Canceled");
        }
        catch (SQLException e1) {
        	e1.printStackTrace();
        	out.println("Invalid Link.");
        	return;
        }

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
