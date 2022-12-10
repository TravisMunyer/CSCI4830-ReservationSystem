import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email
{	
    public static void sendEmail(String[] toEmails, int reservationId, LocalDateTime reservationTime) throws AddressException, MessagingException
    {
    	// Gmail credentials.
        String fromUser = Credentials.GetEmail();
        String fromUserEmailPassword = Credentials.GetEmailPassword();
        
        // Connect to SMPT server.
        Session mailSession = getMailSession();
        String emailHost = "smtp.gmail.com";
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);

        // Draft message.
        MimeMessage emailMessage = draftEmailMessage(mailSession, toEmails, reservationId, reservationTime);

        // Send email.
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
	
	private static Session getMailSession()
    {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        return Session.getDefaultInstance(emailProperties, null);
    }
	
	private static String GetLink(int reservationId) {
		// This link method is not secure, as anyone can delete the reservation currently if the reservation ID is known. Suggest using a randomly generated confirmation ID or session ID to cancel.
		return Credentials.GetDnsName() + "/FinalProject/DeleteReservation?id=" + reservationId;
	}

    private static MimeMessage draftEmailMessage(Session mailSession, String[] toEmails, int reservationId, LocalDateTime reservationTime) throws AddressException, MessagingException
    {
        String emailSubject = "Reservation Confirmation";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");        
        String emailBody = "Reservation confirmed for " + dtf.format(reservationTime) + ".\n"
        		+ "Access the following link to cancel: " + GetLink(reservationId);

        MimeMessage emailMessage = new MimeMessage(mailSession);
        
        // Set message recipients.
        for (int i = 0; i < toEmails.length; i++)
        {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }
        
        // Set email text and subject.
        emailMessage.setSubject(emailSubject);
        emailMessage.setText(emailBody);
        return emailMessage;
    }
}
