package juniors.server.core.data.users;

import java.util.LinkedList;
import java.util.List;

import juniors.server.core.data.bets.Bet;

/**
 * Хранит данные о пользователе, его ставки, даёт возможность
 * задать и получить эти данные 
 * 
 * @author kovalev
 *
 */
public class User{
	protected String login;
	protected String name;
	protected String surname;
	protected String password; // научиться правильно хранить пароль
	protected String bank_account; // номер банковского счёта
	protected LinkedList<Bet> betList; // контейнер с ссылками на ставки, которые делал пользователь
	protected boolean isAuthorized;	// если авторизован - true
	long lastTimeActive;	// время последней активности пользователя. 
	
	/**
	 * 
	 * @param newLogin
	 * @param newName
	 * @param newSurname
	 * @param newPassword
	 * @param newBankAccount
	 */
	public User(String newLogin, String newName, String newSurname, 
			String newPassword, String newBankAccount){
		login = newLogin;
		name = newName;
		surname = newSurname;
		bank_account = newBankAccount;
		password = newPassword;
		
		betList = new LinkedList<Bet>();
		lastTimeActive = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return String login
	 */
	public String getLogin(){
		return login;
	}
	
	/**
	 * 
	 * @param String new_login
	 */
	public void setLogin(String new_login){
		login = new_login;
	}
	
	/**
	 * 
	 * @return String name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @param String new_name
	 */
	public void setName(String new_name){
		name = new_name;
	}
	
	/**
	 * 
	 * @return String surname
	 */
	public String getSurname(){
		return surname;
	}
	
	/**
	 * 
	 * @param new_surname
	 */
	public void setSurname(String new_surname){
		surname = new_surname;
	}
	
	/**
	 * 
	 * @return String bank_account
	 */
	public String getBankAccount(){
		return bank_account;
	}
	
	/**
	 * 
	 * @param new_bank_account
	 */
	public void setBankAccount(String new_bank_account){
		bank_account = new_bank_account;
	}

	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String new_password) {
		password = new_password;
	}

	
	public void addBet(Bet new_bet) {
		betList.add(new_bet);
	}

	
	public List<Bet> getBets() {
		return betList;
	}
	
	
	
	
	
	
	
}
