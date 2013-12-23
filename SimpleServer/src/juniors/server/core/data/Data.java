package juniors.server.core.data;

import java.util.Collection;
import java.util.Map;
import java.util.Random;


import juniors.server.core.data.events.*;
import juniors.server.core.data.finance.TransactSaver;
import juniors.server.core.data.users.*;
import juniors.server.core.data.markets.*;
import juniors.server.core.data.bets.*;
import juniors.server.core.data.statistics.Note;
import juniors.server.core.data.statistics.StatisticsManager;
import juniors.server.core.data.statistics.StatisticsManagerInterface;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;

/**
 * Класс содержет методы, позволяющие управлять всеми данными, хранящимися на сервере
 * @author kovalev
 *
 */
public class Data implements UserManagerInterface, EventManagerInterface , StatisticsManagerInterface{

	private UserManagerInterface userManager;
	private EventManagerInterface eventManager;
	private StatisticsManagerInterface statistcsManager;
	// при сравнении double что бы убрать погрешность
	private TransactSaver transactSaver;
	public static final float MIN_BET = 10f;

	public Data(){
		userManager = new UserManager();
		eventManager = new EventManager();
		statistcsManager = new StatisticsManager();                
		transactSaver = new TransactSaver();                
	}

	@Override
	public Event addEvent(Event newEvent) {
		return eventManager.addEvent(newEvent);
	}

	/**
	 * Добавляет новый исход в маркет и в общий контейнер.
	 * @param newOutcome
	 * @param eventId
	 * @param marketId
	 * @return - true - если всё корректно добавлено
	 */
	@Override
	public boolean addOutcome(Outcome newOutcome, int eventId, int marketId){
		return eventManager.addOutcome(newOutcome, eventId, marketId);
	}

	@Override
	public boolean containsOutcome(int outcomeId){
		return eventManager.containsOutcome(outcomeId);
	}

	@Override
	public Outcome getOutcome(int outcomeId){
		return eventManager.getOutcome(outcomeId);
	}

	@Override
	public Event getEvent(int eventId) {
		return eventManager.getEvent(eventId);
	}

	@Override
	public Event removeEvent(int eventId) {
		return eventManager.removeEvent(eventId);
	}

	@Override
	public boolean containsUser(String login) {
		return userManager.containsUser(login);
	}

	@Override
	public boolean changeLogin(String login, String newLogin) {
		return userManager.changeLogin(login, newLogin);
	}

	@Override
	public User getUser(String login) {
		return userManager.getUser(login);
	}

	@Override
	public boolean authorizeUser(Integer userId) {
		return userManager.authorizeUser(userId);
	}

	@Override
	public int getCountUsers() {
		return userManager.getCountUsers();
	}

	@Override
	public int getCountAuthorizedUsers() {
		return userManager.getCountAuthorizedUsers();
	}

	@Override
	public boolean createUser(String newLogin, String newName,
			String newSurname, String newPassword, String newBankAccount) {
		return userManager.createUser(newLogin, newName, newSurname, newPassword, newBankAccount);
	}

	@Override
	public boolean changeUserData(String login, String newName,
			String newSurname, String newPassword, String newBankAccount) {
		return userManager.changeUserData(login, newName, newSurname, newPassword, newBankAccount);
	}

	@Override
	public Map<Integer, Event> getEventsMap() {
		return eventManager.getEventsMap();
	}

	@Override
	public Collection<Event> getEventsCollection() {
		return eventManager.getEventsCollection();
	}

	/**
	 * 
	 * @param eventId - id события, маркеты которого нужны.
	 * @return
	 */
	public Map<Integer, Market> getMarketsMap(Integer eventId){
		return eventManager.getEvent(eventId).getMarketsMap();
	}

	/**
	 * 
	 * @param eventId
	 * @param marketId
	 * @return Map со списком исходов для маркета marketId
	 */
	public Map<Integer, Outcome> getOutcomeMap(Integer eventId, Integer marketId){
		return eventManager.getEvent(eventId).getMarket(marketId).getOutcomeMap();
	}

	@Override
	public boolean containsEvent(int eventId){
		return eventManager.containsEvent(eventId);
	}       

	protected Outcome getOutcome(int eventId, int markedId, int outcomeId){
		return eventManager.getEvent(eventId).getMarket(markedId).getOutcome(outcomeId);
	}

	/**
	 * 
	 * @param userId
	 * @param eventId
	 * @param marketId
	 * @param outcomeId
	 * @return 
	 */
	private Logger logger = Logs.getInstance().getLogger("data");
	public Bet makeBet(String userLogin, int outcomeId, double sum, double coefficient) {
		logger.info("All is bad");
		// корректны ли данные запрсая
		if (!containsUser(userLogin) || !containsOutcome(outcomeId)) {
			System.out.println("fail");
			return null;
		}
		logger.info("Im in data makebet");
		logger.info("DATA: check on exists user... ok");
		Outcome currentOutcome = getOutcome(outcomeId);
		if(currentOutcome == null)
			logger.warning("DATA: fail get outcome!");
		
		// соответствует ли величина ставки условиям
		if (currentOutcome.getMaxBet() < sum || MIN_BET > sum){
			return null;
		}
		
		// если коеффициенты не совпадают
		/*
		 * if (Math.abs(currentOutcome.getCoefficient() - coefficient) >
		 * DOUBLE_DELTA){ return false; }
		 */
		Bet newBet = new Bet(getUser(userLogin), currentOutcome,
				currentOutcome.getCoefficient(), (float) sum);
				
		// Записываю везде ставку. Если не удалось - тоже ошибка
		if (!currentOutcome.addBet(newBet)) {
			logger.warning("DATA: fail add bet: Current market had already started");
			return null;
		}
		if (!getUser(userLogin).addBet(newBet)) {
			logger.warning("DATA: fail add bet to user!");
			currentOutcome.removeBet(newBet);
			return null;
		}
		//getBookmaker().addBet(newBet);

		return newBet;
	}

	// / ALEX: I AM USING THIS STUB (DISPATCHER)
	public Bet makeBet(String login, int outcomeId, double sum) {
		return makeBet(login, outcomeId, sum, 0.01);
	}

	@Override
	public long setCountLoginPerHour(long newValue) {
		return statistcsManager.setCountLoginPerHour(newValue);
	}

	@Override
	public long getCountLoginPerHour() {
		return statistcsManager.getCountLoginPerHour();
	}

	@Override
	public Note getLoginPerHour() {
		return statistcsManager.getLoginPerHour();
	}

	@Override
	public long setCountLoginPerDay(long newValue) {
		return statistcsManager.setCountLoginPerDay(newValue);
	}

	@Override
	public long getCountLoginPerDay() {
		return statistcsManager.getCountLoginPerDay();
	}

	@Override
	public Note getLoginPerDay() {
		return statistcsManager.getLoginPerDay();
	}

	@Override
	public long setCountLoginPerMonth(long newValue) {
		return statistcsManager.setCountLoginPerMonth(newValue);
	}

	@Override
	public long getCountLoginPerMonth() {
		return statistcsManager.getCountLoginPerMonth();
	}

	@Override
	public Note getLoginPerMonth() {
		return statistcsManager.getLoginPerMonth();
	}

	@Override
	public long setCountRequestPerSecond(long newValue) {
		return statistcsManager.setCountRequestPerSecond(newValue);
	}

	@Override
	public long getCountRequestPerSecond() {
		return statistcsManager.getCountRequestPerSecond();
	}

	@Override
	public Note getRequestPerSecond() {
		return statistcsManager.getRequestPerSecond();
	}

	@Override
	public long setCountRequestPerMinute(long newValue) {
		return statistcsManager.setCountRequestPerMinute(newValue);
	}

	@Override
	public long getCountRequestPerMinute() {
		return statistcsManager.getCountRequestPerMinute();
	}

	@Override
	public Note getRequestPerMinute() {
		return statistcsManager.getRequestPerMinute();
	}

	@Override
	public long setCountRequestPerHour(long newValue) {
		return statistcsManager.setCountRequestPerHour(newValue);
	}

	@Override
	public long getCountRequestPerHour() {
		return statistcsManager.getCountRequestPerHour();
	}

	@Override
	public Note getRequestPerHour() {
		return statistcsManager.getRequestPerHour();
	}

	@Override
	public long setCountRequestPerDay(long newValue) {
		return statistcsManager.setCountRequestPerDay(newValue);
	}

	@Override
	public long getCountRequestPerDay() {
		return statistcsManager.getCountRequestPerDay();
	}

	@Override
	public Note getRequestPerDay() {
		return statistcsManager.getRequestPerDay();
	}

	@Override
	public long setCountBetPerSecond(long newValue) {
		return statistcsManager.setCountBetPerSecond(newValue);
	}

	@Override
	public long getCountBetPerSecond() {
		return statistcsManager.getCountBetPerSecond();
	}

	@Override
	public Note getBetPerSecond() {
		return statistcsManager.getBetPerSecond();
	}

	@Override
	public long setCountBetPerMinute(long newValue) {
		return statistcsManager.setCountBetPerMinute(newValue);
	}

	@Override
	public long getCountBetPerMinute() {
		return statistcsManager.getCountBetPerMinute();
	}

	@Override
	public Note getBetPerMinute() {
		return statistcsManager.getBetPerMinute();
	}

	/**
	 * Временный способ реализации транзакций
	 * 
	 * @param login
	 * @param betId
	 * @param sum
	 * @return true - транзакция прошла успешно
	 */
	public boolean makeTransact(String login, int betId, float sum){
		// получаю User
		if (!containsUser(login)) {
			return false;
		}
		User user = getUser(login);
		Bet bet = user.getBet(betId);
		Bookmaker bookmaker = getBookmaker();

		// есть ли такая ставка и не рассчитывалась ли она
		if (bet == null || !transactSaver.addTransact(bet)) {
			return false;
		}

		user.getBalance().lockBalance();
		bookmaker.getBalance().lockBalance();

		// если ошибка у игрока, транзакция прерывается и сохраняется ошибка
		if (!user.calculateBet(bet, sum)) {
			transactSaver.addFailTransact(bet);

			user.getBalance().unlockBalance();
			bookmaker.getBalance().unlockBalance();

			return false;
		}

		// если ошибка у букмекера, игроку возвращается ставка
		// операция отменяется и сохраняется ошибка
		if (!bookmaker.calculateBet(bet, sum)) {
			transactSaver.addFailTransact(bet);
			user.addBetAgain(bet, sum);

			user.getBalance().unlockBalance();
			bookmaker.getBalance().unlockBalance();

			return false;
		}

		user.getBalance().unlockBalance();
		bookmaker.getBalance().unlockBalance();

		return true;
	}

	@Override
	public Bookmaker getBookmaker(){
		return userManager.getBookmaker();
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
        return userManager.changeBalance(login, sum);
    }*/

	public static void main(String [] args){
		Data data = new Data();
		int eventId = 10;
		long startTime = 10L;
		int marketId = 11;
		int outcomeId = 12;
		double coefficient = 1.5d;
		double secondCoefficient = 1 / (1 - 1 / coefficient);
		float sum = 700f;
		Bookmaker bookmeker = data.getBookmaker();
		Random rand = new Random();

		Event e = new Event(eventId, startTime);
		Market m = new Market(marketId);
		Outcome o = new Outcome(outcomeId, coefficient);
		Outcome o1 = new Outcome(outcomeId + 1, secondCoefficient);
		String login = "testLogin";
		User user = data.getUser(login);

		data.addEvent(e);
		e.addMarket(m);
		data.addOutcome(o, e.getEventId(), m.getMarketId());
		data.addOutcome(o1, eventId, marketId);
		
		// ставится 700 на второй исход
		data.makeBet(user.getLogin(), o.getOutcomeId() + 1, sum, o.getCoefficient());		
		
		// ставится 7000 на первый исход
		for (int i = 0; i < 10; i++){
			String tempLogin = new String(String.valueOf(rand.nextInt()));
			data.makeBet(tempLogin, outcomeId, 700);
		}
		
		double testC = o.getCoefficient();
		double testSecondC = o1.getCoefficient();
		
	}
	
}


