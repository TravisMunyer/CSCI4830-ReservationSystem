import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

public class EmailTests {
	
	@Test
	public void sendEmailTest() {
		try {
			Email.sendEmail(new String[] { Credentials.GetEmail() }, 1, LocalDateTime.now());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
