package juniors.server.core.logic.services;



/**
 * Фабрика сервисов
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class Services {

	private static final Services instance;
	static{
	    instance = new Services();
	}
	
	private StatisticService statisticService;

	public static Services getInstance() {
		return instance;
	}
	
	
	private Services() {
		statisticService = new StatisticService();
	}
	
	public AccountsService getAccountsService() {
		return new AccountsService();
	}
	
	public EventService getEventService() {
		return new EventService();
	}
	
	public StatisticService getStatisticService() {
	    	return statisticService;
	}
	
	public BetsService BetsService() {
	    	return new BetsService();
	}
}
