package juniors.server.core.logic.services;

import juniors.server.core.logic.ServerFacade;


/**
 * Фабрика сервисов
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class Services {

	private static volatile Services instance;
	
	private StatisticService staticService;

	public static Services getInstance() {
		Services localInstance = instance;
		if (localInstance == null) {
			synchronized (ServerFacade.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new Services();
				}
			}
		}
		return localInstance;
	}
	
	
	private Services() {
		staticService = new StatisticService();
	}
	
	public AccountsService getAccountsService() {
		return new AccountsService();
	}
	
	public EventService getEventService() {
		return new EventService();
	}
	
	public StatisticService getStaticService() {
	    	return staticService;
	}
}
