package juniors.server.core.data.users;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

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
	protected String bankAccount; // номер банковского счёта
    protected Balance balance;    // баланс (деньги), balance >= 0
	protected Set<Bet> bets; // контейнер с ссылками на ставки, которые делал пользователь
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
		bankAccount = newBankAccount;
		password = newPassword;
                balance = new Balance();
                balance.available = 1000f;
		
		bets = new ConcurrentSkipListSet<Bet>();
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
	public void setLogin(String newLogin){
		login = newLogin;
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
	public void setName(String newName){
		name = newName;
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
	 * @param newSurname
	 */
	public void setSurname(String newSurname){
		surname = newSurname;
	}
	
	/**
	 * 
	 * @return String bank_account
	 */
	public String getBankAccount(){
		return bankAccount;
	}
	
	/**
	 * 
	 * @param newBankAccount
	 */
	public void setBankAccount(String newBankAccount){
		bankAccount = newBankAccount;
	}

	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String newPassword) {
		password = newPassword;
	}

	/**
	 * Добавляет новую ставку и резервирует необходимую сумму в балансе
	 * @param newBet
	 * @return true - всё добавлено без ошибок.
	 */
	public boolean addBet(Bet newBet) {
	    if (balance.reserve.containsKey(newBet) || bets.contains(newBet)){
	    	return false;
	    }
	    
	    balance.available -= newBet.getSum();
	    balance.reserve.put(newBet, newBet.getSum());
		this.bets.add(newBet);
		return true;
	}

	
	public Set<Bet> getBets() {
		return bets;
	}
	
        /**
         * Если sum = 0, то ставка списывается, иначе ставка удаляется, а available 
	 * пополняется на sum.
         * 
         * @param bet - ставка, с которой производится работа
         * @param sum - сумма операции (строго > 0).
         * @return - true - операция прошла успешно. False - произошла ошибка 
         */
	public boolean calculateBet(Bet bet, float sum){
	    // на случай отрицательной суммы нужна ли здесь проверка?
	    if (sum < 0){
		return false;
	    }
	    // проверка существования такого резерва и ставки 
	    if (!balance.reserve.containsKey(bet) || bets.contains(bet)){
		return false;
	    }
	    
            // если ставка проиграна, она просто удаляется
	    if (sum == 0){
	    	balance.reserve.remove(bet);
	    	bets.remove(bet);
	    	return true;
            }
	    else { // ставка выиграна
		balance.available += sum;
		balance.reserve.remove(bet);
		bets.remove(bet);
		return true;
	    }
     }
	
	public Balance getBalance() {
		return this.balance;
	}
}
