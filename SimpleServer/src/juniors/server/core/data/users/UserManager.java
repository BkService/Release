package juniors.server.core.data.users;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import juniors.server.core.data.bets.*;


public class UserManager implements UserManagerInterface{
	
	private Map<String, User> userMap; // Контейнер с пользователями. Ключь для объекта - логин. 	
	private Map<Integer, User> authorizedUsers; // контейнер с авторизованными пользователями
	
	public UserManager(){
		userMap = new ConcurrentHashMap<String, User>();
		authorizedUsers = new ConcurrentHashMap<Integer, User>();
		
		this.createUser("admin", "Admin", "Admin", "sserver", "XXXXXXXXXXXXXXXX");
		this.createUser("login", "Name", "Surename", "321321", "4963.6548.3252.5791");
	}
	
	/**
	 * 
	 * @param login
	 * @return user с указанным логином 
	 */
	public User getUser(String login){
		return userMap.get(login);
	}
	
	/**
	 * Добавляет нового пользователя с заданными параметрами.
	 * Логин у каждого пользователя уникален.
	 * Так же создаёт для него новый банковский счёт (ещё не реализовано) 
	 * 
	 * @param new_login
	 * @param new_name
	 * @param new_surname
	 * @param new_password
	 * @param new_bank_account
	 * @return true - пользователь создан. False - произошла ошибка при создании.
	 */
	public boolean createUser(String newLogin, String newName, String newSurname, 
			String newPassword, String newBankAccount) 
	{
		// такой логин существует
		if (userMap.containsKey(newLogin)){
			return false;
		}
			
		// имя с фамилией - пустые строки (а эта проверка нужна?)
		if (newName.length() == 0 || newSurname.length() == 0){
			return false;
		}
		
		userMap.put(newLogin, 
				new User(newLogin, newName, newSurname, newPassword, newBankAccount));
		
		return true;
	}
	
	/**
	 * Заменить login на new_login
	 * 
	 * @param login - старый логин
	 * @param new_login - новый логин
	 * @return true - замена выполнена. false - произошла ошибка
	 * @throws LoginIsBusyException
	 * @throws LengthLoginException 
	 */
	public boolean changeLogin(String login, String newLogin)
		{
		// проверка данных
		// новый логин уже занят
		if (userMap.containsKey(newLogin)){ 
			return false;
		}
		
		// не существует пользователя с указанным старым логином
		if (userMap.containsKey(login)){
			return false;
		}
						
		// замена логина. Надо доделать
		User user = userMap.get(login);
		user.setLogin(newLogin);
		userMap.remove(login);
		userMap.put(newLogin, user);
		
		return true;
	}
	
	/**
	 * @param login
	 * @return true - пользователь существует. false - пользователь не существует.
	 */
	public boolean containsUser(String login){
		return userMap.containsKey(login);
	}
	
	
	/*
	public boolean changePassword(String password, String new_password){
		
	}*/
	
	/**
	 * Задать пользователю с указаным логином новые имя, фамилию, номер счёти и пароль
	 */
	@Override
	public boolean changeUserData(String login, String newName,
			String newSurname, String newPassword, String newBankAccount) {
		User user = userMap.get(login);
		
		if (user == null){
			return false;
		}
		
		user.setSurname(newSurname);
		user.setBankAccount(newBankAccount);
		user.setName(newName);
		user.setPassword(newPassword);
		
		return true;
	}
	
	
	/**
	 * Авторизовать пользователя
	 * @param user_id - ключ
	 * @return true - авторизован. false - невозможно авторизовать
	 */
	@Override
	public boolean authorizeUser(Integer userId) {
		User user = userMap.get(userId);
		
		// если пользователя не существует или пользователь уже авторизован
		if (user == null || user.isAuthorized){
			return false;
		}
		
		user.lastTimeActive = System.currentTimeMillis();
		user.isAuthorized = true;
		
		// user_id заменим на какой то ключ
		authorizedUsers.put(userId, user);
		
		return true;
	}
	
	@Override
	public int getCountUsers(){
		return userMap.size();
	}
	
	@Override
	public int getCountAuthorizedUsers(){
		return authorizedUsers.size();
	}
        
        /**
         * 
         * @param login
         * @param newBet
         * @return true - ставка добавлена
         */
        public boolean addBet(String login, Bet newBet){
            return getUser(login).addBet(newBet);
        }
	
	/**
	 *  Только тестирование.
	 */
	public void testUsers(){
		String newLogin = "TestLog";
		String newName = "Name";
		String newSurname = "Surname";
		String newPassword = "pas";
		String newBankAccount = "account";
		
		this.createUser(newLogin, newName, newSurname, newPassword, newBankAccount);
		
		User user = this.getUser(newLogin);
		
		
		
	}
        
        /**
         * Временный способ работы с финансами!
         * Меняет balance на величину sum.
         * Если надо  снять, то sum отрицательна.
         * Balance должен быть >= 0 (надо ли это?)
         * 
         * @param login - логин пользователя
         * @param sum - сумма операции
         * @return - новый balance, или -1 в случае ошибки операции 
         */
      /*  @Override
        public float changeBalance(String login, float sum) {
            return userMap.get(login).changeBalance(sum);
        }*/

	
	/**
	 * для проведения тестов
	 * @param args - не используется
	 */
	public static void main(String[] args) {
		UserManager userManager = new UserManager();
		
		userManager.testUsers();

	}

    
	
}
