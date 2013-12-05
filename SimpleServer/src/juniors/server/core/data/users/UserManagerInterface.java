package juniors.server.core.data.users;

/**
 * Интерфейс для взаимодействия с UserManager
 */
public interface UserManagerInterface {	
	/**
	 * 
	 * @param login
	 * @return true - пользователь с таким логином существует
	 */
	public boolean containsUser(String login);
	
	/**
	 * Меняет логин, если новый логин ещё не занят и подходит (по длине пока что)
	 * @param login - старый логин
	 * @param new_login - новый логин
	 * @return 
	 */
	public boolean changeLogin(String login, String newLogin);
	
	/**
	 * Создаёт и добавляет пользователя с заданными параметрами. Так же 
	 * 
	 * 
	 * @param newLogin
	 * @param newName
	 * @param newSurname
	 * @param newPassword
	 * @param newBankAccount
	 * @return true - если пользователь создан удачно. �?наче - false
	 */
	public boolean createUser(String newLogin, String newName, String newSurname, 
			String newPassword, String newBankAccount);
	
	/**
	 * 
	 * @param login
	 * @return объект пользователя (не желательно, изменение данных 
	 * через его интерфейсы  
	 */
	public User getUser(String login);
	
	/**
	 * 
	 * @param login
	 * @param newName
	 * @param newSurname
	 * @param newPassword
	 * @param newBankAccount
	 * @return
	 */
	public boolean changeUserData(String login, String newName, 
			String newSurname, String newPassword, String newBankAccount);
	
	/**
	 * 
	 * @param userId
	 * @return true - залогинен. false - уже залогинен, нет такого пользователя и т.д.
	 */
	public boolean authorizeUser(Integer userId);
	
	/**
	 * 
	 * @return количество зарегистрированных пользователей
	 */
	public int getCountUsers();
	
	/**
	 * 
	 * @return количество авторизованных пользователей
	 */
	public int getCountAuthorizedUsers();
        
	public Bookmaker getBookmaker();
}












