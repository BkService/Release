package juniors.server.core.resultprovider;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.logic.RunnableService;
import juniors.server.core.logic.TimeChecker;
import juniors.server.core.logic.services.BetsService;

public class ResultProvider implements RunnableService {

    ScheduledExecutorService service;
    boolean isStarted = false;;
    long periodSec = 60;
    TimeChecker timeChecker;

    public ResultProvider() {
	timeChecker = new TimeChecker();
    }

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
	    }, 60, 60, TimeUnit.SECONDS);
	}
    }

    @Override
    public void stop() {
	isStarted = false;
	service.shutdown();
    }

    @Override
    public boolean isStarted() {
	return isStarted;
    }

    @Override
    public long getDelay() {
	return periodSec;
    }

    @Override
    public TimeUnit getTimeUnitDelay() {
	return TimeUnit.SECONDS;
    }

    private void update() {
	Random r = new Random();
	Map<Integer, Event> events = DataManager.getInstance().getEventsMap();
	for (Event event : events.values()) {
	    if (timeChecker.checkOccured(event)) {
		for (Market market : event.getMarketsMap().values()) {
		    if (!market.isEmpty()) {
			market.finish(generateResult(market));
			BetsService.evalBets(market);
		    }
		}
	    }
	}
    }
    private Outcome generateResult(Market market) {
	Random r = new Random();
	Map<Integer, Outcome> outcomes = market.getOutcomeMap();
	double sumCoef = 0;
	for (Outcome outcome : outcomes.values()) {
	    sumCoef += outcome.getCoefficient();
	}
	double value = r.nextDouble() * sumCoef;
	for (Outcome outcome : outcomes.values()) {
	    value -= outcome.getCoefficient();
	    if (value <= 0) {
		return outcome;
	    }
	}
	//It shouldn't be
	return outcomes.values().iterator().next();
    }

}
