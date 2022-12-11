
// This stuff should be stored in a DB or secrets manager, but too expensive for this project.
public class Credentials {
	public static String GetEmail() {
		return "unocsci4830group@gmail.com";
	}
	
	public static String GetEmailPassword() {
		return "vjtiitaklweskwlb";
	}
	
	public static String GetDBPassword() {
		return "root";
	}
	
	public static String GetDnsName() {
		return "ec2-54-82-210-8.compute-1.amazonaws.com";
	}
	
	public static String GetDbString() {
		return "jdbc:mysql://" + GetDnsName() + ":3306/myDB";
	}
}
