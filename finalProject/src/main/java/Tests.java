import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

import junit.framework.Assert;

public class Tests {
	
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
	
	@Test
	public void hashTest() {
		String hash = Cryptography.get_SHA_512_SecurePassword("password", "salt");
		Assert.assertEquals("2908d2c28dfc047741fc590a026ffade237ab2ba7e1266f010fe49bde548b5987a534a86655a0d17f336588e540cd66f67234b152bbb645b4bb85758a1325d64", hash);
	}
	
	@Test
	public void connectionTest() {
		try {
			Connection connection = DriverManager.getConnection(Credentials.GetDbString(), "root", Credentials.GetDBPassword());
			Assert.assertNotNull(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void credentialsTest() {
		Assert.assertEquals(Credentials.GetEmail(), "unocsci4830group@gmail.com");
	}
}
