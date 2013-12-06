package juniors.server.core.resultprovider;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.RunnableService;
import juniors.server.core.logic.TimeChecker;
import juniors.server.core.logic.services.BetsService;

/**
 * Service to generate results of markets. 
 * @author watson
 *
 */


public class ResultProvider implements RunnableService {
	/**
	 * Service to refresh results every minute.
	 */
	ScheduledExecutorService service;
	/**
	 * State, which shows launch robot or not.
	 */
	boolean isStarted = false;;
	/**
	 * Delay in seconds between refreshes.
	 */
	long delaySec = 60;
	
	/**
	 * Helper to check time (Event is started or not).
	 */
	TimeChecker timeChecker;

	
	/**
	 * Logs to log, log and log.
	 */
	Logger logger;

	/**
	 * Constructs helper and logs.
	 */
	public ResultProvider() {
		timeChecker = new TimeChecker();
		logger = Logs.getInstance().getLogger("Result Provider");
		logger.info("Result provider created");
	}

	/**
	 * Runs the service.
	 */
	@Override
	public void start() {
		if (!isStarted) {
			service = Executors.newSingleThreadScheduledExecutor();
			isStarted = true;
			service.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					update();
				}
			}, delaySec, delaySec, TimeUnit.SECONDS);
			logger.info("Result provider started");
		}
	}

	@Override
	public void stop() {
		isStarted = false;
		service.shutdown();
		logger.info("Result provider stoped");
	}

	@Override
	public boolean isStarted() {
		return isStarted;
	}
	
	
	@Override
	public long getDelay() {
		return delaySec;
	}

	@Override
	public TimeUnit getTimeUnitDelay() {
		return TimeUnit.SECONDS;
	}

	
	/**
	 * Updates the results.
	 * Checks all events.
	 * If event's Time < current time it generates result to all market in this event.
	 * And sends notice to BetsService that event has a result. 
	 */
	private void update() {
		logger.info("Result provider is updating...");
		Map<Integer, Event> events = DataManager.getInstance().getEventsMap();
		for (Event event : events.values()) {
			if (timeChecker.checkOccurred(event)) {
				logger.info(event.getEventId() + " " + event.getDescription() + "is started. Generating results...");
				for (Market market : event.getMarketsMap().values()) {
					if (!market.isEmpty() && (market.isFinish())) {
						Outcome winOutcome = generateResult(market);
						market.finish(winOutcome);
						BetsService.evalBets(market);
						logger.info(market.getMarketId() + " " + market.getMarketId() + "is cacluclated. " + winOutcome.getOutcomeId() + " " + winOutcome.getDescription() + " is winning outcome.");
					}
				}
			}
		}
		logger.info("Result provider finished updating");
	}
	
	/**
	 * Generates market of bet in accordance with distribution of coefficient.
	 * 
	 * @param market - market to generate result.
	 * @return - outcome that win.
	 */

	private Outcome generateResult(Market market) {
		Random r = new Random();
		Map<Integer, Outcome> outcomes = market.getOutcomeMap();
		double sumCoef = 0;
		for (Outcome outcome : outcomes.values()) {
			sumCoef += ((outcome.getCoefficient() == 0) ? 0 : (1.0 / outcome
					.getCoefficient()));
		}
		double value = r.nextDouble() * sumCoef;
		for (Outcome outcome : outcomes.values()) {
			value -= ((outcome.getCoefficient() == 0) ? 0 : (1.0 / outcome
					.getCoefficient()));
			if (value <= 0) {
				return outcome;
			}
		}
		// It shouldn't be
		logger.warning("Result provider makes something unexpected");
		return outcomes.values().iterator().next();
	}

}
