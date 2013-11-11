package juniors.server.ext.web.xshell;

public class AdminParameters {

	private static String password = "sserver";
	
	public static boolean changePassword(String oldpasswd, String newpasswd) {
		boolean result = false;
		if(oldpasswd.equals(password) && newpasswd.length() >= 5) {
			result = true;
			password = newpasswd;
		}
		return result;
	}
	
	public static boolean checkPassword(String passwd) {
		return passwd.equals(password);
	}
	
}
