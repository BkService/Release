package juniors.server.core.logic.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import juniors.server.core.data.DataManager;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.RunnableService;
import juniors.server.core.logic.ServerFacade;
import juniors.server.ext.web.listeners.StatisticInfListener;

public class StatisticService implements RunnableService {

    private static Logger log = Logs.getInstance().getLogger(
	    StatisticService.class.getSimpleName());

    public static final int DELAY = 1;
    public static final TimeUnit TIME_UNIT_DELAY = TimeUnit.SECONDS;

    public static final int DELAY_UPDATE_LOGINS = 1;
    public static final TimeUnit TIME_UNIT_DELAY_UPDATE_LOGINS = TimeUnit.HOURS;

    private ScheduledExecutorService executor;

    private boolean started = false;

    public StatisticService() {
    }

    public long getCountLoginsPerHour() {
	return DataManager.getInstance().getCountLoginPerHour();
    }

    public long getCountLoginsPerDay() {
	return DataManager.getInstance().getCountLoginPerDay();
    }

    public long getCountLoginsPerMonth() {
	return DataManager.getInstance().getCountLoginPerMonth();
    }

    public long getCountRequestsPerSecond() {
	return DataManager.getInstance().getCountRequestPerSecond();
    }

    public long getCountRequestsPerMinute() {
	return DataManager.getInstance().getCountRequestPerMinute();
    }

    public long getCountRequestsPerHour() {
	return DataManager.getInstance().getCountRequestPerHour();
    }

    public long getCountRequestsPerDay() {
	return DataManager.getInstance().getCountRequestPerDay();
    }

    public long getCountBetsPerSeconds() {
	return DataManager.getInstance().getCountBetPerSecond();
    }

    public long getCountBetsPeMinut() {
    	return DataManager.getInstance().getCountBetPerMinute();
    }

    public int getCountsUsers() {
    	return DataManager.getInstance().getCountUsers();
    }
    
    public int getCountOnlineUsers() {
    	//FIXME
    	//return StatisticInfListener.getCountOnlineUsers();
    	return 1;
    }

    private class TaskDelaySecond implements Runnable {
	@Override
	public void run() {
	    DataManager.getInstance().setCountRequestPerSecond(
		    ServerFacade.getCountRequest());
	    ServerFacade.resetCountRequest();

	    DataManager.getInstance().setCountBetPerSecond(
		    BetsService.getCountBets());
	    BetsService.resetCountBets();
	}
    }

    private class TaskDelayHour implements Runnable {
	@Override
	public void run() {
	    DataManager.getInstance().setCountLoginPerHour(
		    StatisticInfListener.getCountAuthUsers());
	    StatisticInfListener.resetStaticInf();
	}
    }

    @Override
    public void start() {
	if (!started) {
	    executor = Executors.newScheduledThreadPool(2);
	    executor.scheduleWithFixedDelay(new TaskDelaySecond(), 0, DELAY,
		    TIME_UNIT_DELAY);
	    executor.scheduleWithFixedDelay(new TaskDelayHour(), 0,
		    DELAY_UPDATE_LOGINS, TIME_UNIT_DELAY_UPDATE_LOGINS);
	    started = true;
	}
    }

    @Override
    public void stop() {
	if (started) {
	    executor.shutdown();
	    started = false;
	}
    }

    @Override
    public boolean isStarted() {
	return started;
    }

    @Override
    public long getDelay() {
	return DELAY;
    }

    @Override
    public TimeUnit getTimeUnitDelay() {
	return TIME_UNIT_DELAY;
    }
}
