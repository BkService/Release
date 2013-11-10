package juniors.server.core.logic.services;

import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.data.DataManager;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class BetsService {
    private static AtomicInteger countBets;
    static {
	countBets = new AtomicInteger(0);
    }

    public boolean makeBet(String login, int eventId, int marketId, int outcomeId,
	    int coefficient) {
	/*
	 * if(DataManager.getInstance().getUser(User)user.getName()) boolean
	 * result = DataManager.getInstance().makeBet(user, event, market, come,
	 * come.getCoefficient());
	 */
	DataManager.getInstance().makeBet(login, eventId, marketId,
		outcomeId, coefficient);
	
	return false;
    }

    public static int getCountBets() {
	return countBets.get();
    }

    public static void resetCountBets() {
	countBets.set(0);
    }
}
