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
@WebServlet("/CreateReservation")
public class CreateReservation extends HttpServlet {
    private static final long serialVersionUID = 1;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReservation() {
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
        String firstName = request.getParameter("f_name");
        String lastName = request.getParameter("l_name");
        String email = request.getParameter("emailAddr");
        String numGuests = request.getParameter("num_guests"); //Maybe change data type
        String dateSel = request.getParameter("dateSelect");
        String time = request.getParameter("time"); //Maybe change data type
        String notes = request.getParameter("notes");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Thank you for making your reservation!";
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
            connection = DriverManager.getConnection(Credentials.GetDbString(), "root", Credentials.GetDBPassword());
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control of your database now!");
        System.out.println("Creating statement...");

        sql = "insert into Reservations (ReservationId,FName,LName,Notes,EmailAddress,NumClients,StartTime,IsCanceled) values(NULL,?,?,?,?,?,?,?);";

        try {
            statement1 = connection.prepareStatement(sql);
            
            String fNameVal = firstName;
            String lNameVal = lastName;
            String notesVal = notes;
            String emailVal = email;
            int guestVal = Integer.parseInt(numGuests);
            String dateVal = dateSel;
            String timeVal = time;
            String dateTimeVal = dateVal + " " + timeVal;
            
            System.out.println(dateTimeVal);
            int isCanceled = 0;
            
            statement1.setString(1, fNameVal);
            statement1.setString(2, lNameVal);
            statement1.setString(3, notesVal);
            statement1.setString(4, emailVal);
            statement1.setInt(5, guestVal);
            statement1.setString(6, dateTimeVal);
            statement1.setInt(7, isCanceled);
            

        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            statement1.executeUpdate();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("Thank you for making your reservation!");
        out.println("Please look for a confirmation email shortly.");
        out.println("</body></html>");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}