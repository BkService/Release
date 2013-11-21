package juniors.server.core.logic.services;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.data.DataManager;
import juniors.server.core.logic.RunnableService;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class BetsService implements RunnableService {
    private static AtomicInteger countBets;
    static {
	countBets = new AtomicInteger(0);
    }
        

    public boolean makeBet(String login, int outcomeId, int sum) {
	// если событие началось
	//if (DataManager.getInstance().getEvent(eventId).getStartTime() < System
	//	.currentTimeMillis())
	//    return false;
	return DataManager.getInstance().makeBet(login, outcomeId, sum);
    }

    public static int getCountBets() {
	return countBets.get();
    }

    public static void resetCountBets() {
	countBets.set(0);
    }
    
    private class Task implements Runnable {

	@Override
	public void run() {
	    //for(DataManager.getInstance().)
	    
	}
	
    }

    @Override
    public void start() {
	// TODO Auto-generated method stub

    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isStarted() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public long getDelay() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public TimeUnit getTimeUnitDelay() {
	// TODO Auto-generated method stub
	return null;
    }
}
