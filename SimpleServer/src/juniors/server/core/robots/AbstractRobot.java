package juniors.server.core.robots;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;
import juniors.server.core.logic.DaemonThreadFactory;

public abstract class AbstractRobot {
	User user;
	
	boolean isStarted;
	ScheduledExecutorService service;
	int delaySec;
	Map<Integer, Event> events; 
	ConnectorHelper connector;
	
	
	public AbstractRobot(User user) {
		connector = new ConnectorHelper();
		this.user = user;
		service = Executors.newSingleThreadScheduledExecutor();
		delaySec = 60;
	}
	
	
	/**
	 * This method runs robots.
	 */
	public void run() {
		if (!isStarted) {
			service = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
			isStarted = true;
			service.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					//getBets
					getBets();
					makeBet(generateOutcome());
				}
			}, 0, delaySec, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Here must be method, which allows to make bet.
	 * 
	 */
	
	public void makeBet(Outcome outcome) { 
		connector.sendMakeBetRequest(user.getLogin(), user.getName(), outcome.getOutcomeId(), 123);
	}
	
	
	/**
	 * Here must be method, which allows to get bets from server.  
	 * 
	 **/
	Map<Integer, Event> getBets() {	
		connector.sendGetBetsRequest();
		return null;
	}
	
	public abstract Outcome generateOutcome();	
}
