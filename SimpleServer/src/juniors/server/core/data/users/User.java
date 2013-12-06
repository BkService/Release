package juniors.server.core.data.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import juniors.server.core.data.bets.Bet;

/**
 * Хранит данные о пользователе, его ставки, даёт возможность
 * задать и получить эти данные 
 * 
 * @author kovalev
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String login;
	protected String name;
	protected String surname;
	protected String password; // научиться правильно хранить пароль
	protected String bankAccount; // номер банковского счёта
    protected Balance balance;    // баланс (деньги), balance >= 0
	protected Map<Integer, Bet> bets; // контейнер с ссылками на ставки, которые делал пользователь
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
			String newPassword, String newBankAccount)
	{
		login = newLogin;
		name = newName;
		surname = newSurname;
		bankAccount = newBankAccount;
		password = newPassword;
                balance = new Balance();
                balance.changeBalance(1000f);
		
		bets = new ConcurrentHashMap<Integer, Bet>();
		lastTimeActive = System.currentTimeMillis();
	}
	
	public String getLogin(){
		return login;
	}
	
	public void setLogin(String newLogin){
		login = newLogin;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setSurname(String newSurname){
		surname = newSurname;
	}
	
	public String getBankAccount(){
		return bankAccount;
	}
	
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
	 * @return true - всё добавлено без ошибок. false - ставка уже зарезервирована
	 */
	public boolean addBet(Bet newBet) {
	    if (!balance.addToReserve(newBet.getBetId(), newBet.getSum())){
		return false;
	    }
	    
	    this.bets.put(newBet.getBetId(), newBet);
	    return true;
	}
	
	/**
	 * 
	 * @param betId
	 * @return - объект ставки с таким id
	 */
	public Bet getBet(int betId){
	    return bets.get(betId);
	}
	
	public Map<Integer, Bet> getBets() {
		return bets;
	}
	
        /**
         * Если sum = 0, то ставка списывается, иначе ставка удаляется, а available 
	 * пополняется на sum.
         * 
         * @param bet - ставка, с которой производится работа
         * @param sum - сумма операции (>= 0)
         * @return - true - операция прошла успешно. False - произошла ошибка 
         */
	public boolean calculateBet(Bet bet, float sum){
	    // на случай отрицательной суммы нужна ли здесь проверка?
	    if (sum < 0){
		return false;
	    }
	    
	    // проверка существования такого резерва и ставки 
	    if (!balance.containsReserve(bet.getBetId()) || !bets.containsKey(bet.getBetId())){
		return false;
	    }
	    
            // если ставка проиграна, она просто удаляется
	    if (sum == 0){
	    	balance.removeFromReserve(bet.getBetId());
	    	bets.remove(bet.getBetId());
	    	
	    	return true;
            }
	    else { // ставка выиграна
		balance.changeBalance(sum);
		balance.removeFromReserve(bet.getBetId());
		bets.remove(bet.getBetId());	    
		
		return true;
	    }
     }
	
	public Balance getBalance() {
		return this.balance;
	}
	
	/**
	 * Отменяет рассчёт ставки. Меняет баланс и возвращает ставку в резерв
	 *  
	 * @param bet 
	 * @param sum
	 * @return true - ставка добавлена успешно
	 */
	public boolean addBetAgain(Bet bet, float sum){
	    if (sum > 0){
		balance.addToReserve(bet.getBetId(), sum);		    
		this.bets.put(bet.getBetId(), bet);
		
		return true;
	    }
	    
	    return false;
	}
	
	/**
     * Проверяет, банкрот ли пользователь. Если доступные средства меньше 
     * величины минимальной ставки и ресервы пусты, то пользователь банкрот
     * 
     * @return - true - да, банкрот
     */
    public boolean isBankrupt(){
    	return balance.isBankrupt();
    }
}
