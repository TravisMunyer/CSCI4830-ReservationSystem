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
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/ViewReservations")
public class ViewReservations extends HttpServlet {
    private static final long serialVersionUID = 1 ;

    String dns = "ec2-54-82-210-8.compute-1.amazonaws.com";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewReservations() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String searchDate = request.getParameter("searchDate");
        response.setContentType("text/html");
        String dateSel = request.getParameter("dateSelect");
        
        System.out.println(dateSel);
        
        String alteredDate = "'" + dateSel  + "%" + "'";
        
        System.out.println(alteredDate);
 
        PrintWriter out = response.getWriter();
        String title = "Search for a reservation here";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##CCCCFF\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/myDB", "root", "root");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * from Reservations WHERE DATE(startTime)=" + alteredDate + ";";
        try {

            statement1 = connection.prepareStatement(sql);
           // String date = searchDate;
            //statement1.setString(1, date);
     
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
        out.println("<table border=1 width=50% height=30%>");
        out.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Notes</th><th>Email Address</th><th>Number of Clients</th><th>Start Time</th><th>Canceled?</th>");
        try {
            while (rs.next()) {
                //Retrieve by column name
            	String textCanceled = "no";
                int id = rs.getInt("ReservationId");
                String firstName = rs.getString("FName");
                String lastName = rs.getString("LName");
                String notes = rs.getString("Notes");
                String emailAddress = rs.getString("EmailAddress");
                int numClients = rs.getInt("NumClients");
                String startTime = rs.getString("StartTime");
                int isCanceled = rs.getInt("IsCanceled");
                if(isCanceled == 0) 
                {
                	textCanceled = "no";
                }
                else 
                {
                	textCanceled = "yes";
                }
                out.println("<tr><td>" + id + "</td><td>" + firstName + "</td><td>" + lastName + "</td><td>" + notes + "</td><td>" + emailAddress + "</td><td>" + numClients + "</td><td>" + startTime + "</td><td>" + textCanceled + "</td></tr>");
            }
            out.println("</body></html>");
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