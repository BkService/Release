package juniors.server.core.robots;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.DaemonThreadFactory;

/**
 * Implementation of robot. It has one abstract method generateBet(), which has
 * different implementation depends on strategy of robot.
 * 
 * @author watson
 * 
 */

public abstract class AbstractRobot {
	/**
	 * User to make a bets.
	 */
	User user;

	/**
	 * State, which shows launch robot or not.
	 */
	boolean isStarted;

	/**
	 * Service to make activity every minute.
	 */
	ScheduledExecutorService service;

	/**
	 * Delay in seconds between activities.
	 */
	int delaySec;

	/**
	 * Sum of bet. It is constant to all bets.
	 */
	int sum;

	/**
	 * Events, received from server.
	 */
	Map<Integer, Event> events;

	/**
	 * It is provide interface to interact with server.
	 */
	ConnectorHelper connector;

	/**
	 * Set of markets, which contains all markets on that we have bet. (In
	 * greedy and safety strategy we don't allow robot to bet on one market
	 * twice).
	 */
	Set<Market> hasBets;

	/**
	 * Current market on that we want to bet.
	 */
	Market curMarket;

	Logger logger = Logs.getInstance().getLogger("Robots");
	
	/**
	 * Constructs a robot.
	 * 
	 * @param user
	 *            - user to make a bets.
	 */
	public AbstractRobot(User user) {
		connector = new ConnectorHelper();
		this.user = user;
		delaySec = 60;
		sum = 100;
		isStarted = false;
		hasBets = new HashSet<Market>();
	}
	/**
	 * Stops robot.
	 */
	public void stop() {
		if (isStarted) {
			service.shutdown();
			isStarted = false;
		}
	}

	public boolean isStarted() {
		return isStarted;
	}

	/**
	 * Runs robot.
	 */
	public void run() {
		if (!isStarted) {
			service = Executors
					.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
			isStarted = true;
			service.scheduleAtFixedRate(new Runnable() {
				/**
				 * Robot gets all available bets.
				 * If robots is bankrupt he logs it and die.
				 * Then he get sum of next bet and outcome to bet.
				 * And sends request to make a bet.
				 * If robots get acknowledgment that the bet accepts, he add market to sets which contains markets on that robots has bets.   
				 */
				
				@Override
				public void run() {
					getEvents();
					if (!user.getBalance().isBankrupt()) {
						double sum = getSum();
						Outcome outcome = generateOutcome();
						if (makeBet(outcome, sum)) {
							logger.info(nameToLogs() + " made bet to " + outcome);
							markMarket();
						} else {
							logger.info(nameToLogs() + " had a problem");
						}
					} else {
						logger.warning(nameToLogs() + "Robot is died, he is Bunkrot");
						stop();
					}
				}
			}, 0, delaySec, TimeUnit.SECONDS);
		}
	}

	
	/**
	 * Returns sum of bet. 
	 * @return - sum to bet. If user has balance > sum returns sum. Otherwise returns balance.
	 */
	public double getSum()  {
		return Math.min(sum, user.getBalance().getBalanceValue());
	}
	
	/**
	 * Tries to make a bet. Sends request to server to make a bet with defined
	 * outcome and sum.
	 * 
	 * @return - true - if we make a bet, false - otherwise(we didn't make this
	 *         bet).
	 */
	public boolean makeBet(Outcome outcome, double sum) {
		if (outcome != null) {
			return connector.sendMakeBetRequest(user.getLogin(),
					user.getPassword(), outcome.getOutcomeId(), sum);
		}
		return false;
	}

	/**
	 * Tries to get all events. Gets all events.
	 */
	void getEvents() {
		
		events = DataManager.getInstance().getEventsMap();
	}

	/**
	 * Adds current market to set of markets. This set of markets indicates in
	 * what markets we have bets.
	 */
	void markMarket() {
		hasBets.add(curMarket);
	}

	/**
	 * Generates outcome to bet.
	 * 
	 * @return - outcome to make a bet, or null - it indicates that now we don't
	 *         want/can to bet.
	 */
	public abstract Outcome generateOutcome();
	
	//
	public abstract String nameToLogs();
}
