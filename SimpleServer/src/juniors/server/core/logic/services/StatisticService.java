package juniors.server.core.logic.services;

import java.util.logging.Logger;

import juniors.server.core.data.DataManager;
import juniors.server.ext.web.listeners.SessionsListener;

public class StatisticService implements Runnable {

	private static int countsAuthUsers = 0, countsLogouts = 0, countsUsers = 0,
			countsConnects = 0;

	private Logger log = Logger.getLogger(StatisticService.class.getSimpleName());

	private static final int DELAY = 1000;

	public StatisticService() {

	}

	public int getCountsAuthUsers() {
		return countsAuthUsers;
	}

	public int getCountsUsers() {
		return countsUsers;
	}

	public int getCountsConnects() {
		return countsConnects;
	}
	
	public int getCountsLogouts() {
		return countsLogouts;
	}

	// FIXME Не получаем количества успешных авторизаций. 
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			countsUsers = DataManager.getInstance().getCountUsers();
			countsConnects = SessionsListener.getCountConnects();
			countsLogouts = SessionsListener.getCountDisconnects();			
			SessionsListener.clearStaticInf();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				log.info("Stop static service.");
			}
		}
	}
}
