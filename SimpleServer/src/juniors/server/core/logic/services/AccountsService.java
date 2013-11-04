package juniors.server.core.logic.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.users.User;

/**
 * Служба аккаунтов.
 * 
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 */
public class AccountsService {

	private static final String TYPE_HASH = "SHA";

	private static final Logger log = Logger.getLogger(AccountsService.class
			.getSimpleName());

	public AccountsService() {

	}
	
	private String getHash(String in) {
		StringBuffer hexString = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(TYPE_HASH);
			byte[] hash = md.digest(in.getBytes());
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.WARNING, e.getMessage());
			hexString.append(in);
		}
		return hexString.toString();
	}

	public boolean addUser(User user) { //alex fixed
		if(user == null)
			return false;		//alex fixed
		String passw = user.getPassword();
		String hashPassw = getHash(passw);
		boolean result = DataManager.getInstance().createUser(user.getLogin(),
				user.getName(), user.getSurname(), hashPassw,
				user.getBankAccount());
		return result;
	}

	public boolean checkUser(User user) { //alex fixed
		User saveUser = DataManager.getInstance().getUser(user.getLogin());
		if(saveUser == null) 	// alex fixed
			return false;
		String hashPassw = getHash(user.getPassword());
		return hashPassw.equals(saveUser.getPassword());
	}
	
	public User getUser(String login) {
		return DataManager.getInstance().getUser(login);
	}

	boolean deleteUser(User user) {
		return false;
	}

}
