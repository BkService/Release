package juniors.server.core.logic.services;

import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.bets.Bet;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class BetsService {
    private static AtomicInteger countBets;
    static {
	countBets = new AtomicInteger(0);
    }

    public boolean makeBet(String login, int outcomeId, int sum) {
	/*
	 * // если событие началось Outcome outcome =
	 * DataManager.getInstance().getOutcome(outcomeId); if
	 * (DataManager.getInstance().getOutcome(outcomId)....getStartTime() <
	 * System .currentTimeMillis()) return false;
	 */
	return DataManager.getInstance().makeBet(login, outcomeId, sum);
    }

    public static void evalBets(Market market) {
	for (Outcome outcome : market.getOutcomeCollection()) {
	    if (!outcome.isWin()) {
		for (Bet bet : outcome.getBets()) {
		    float sum = 0;
		    // create transaction
		}
	    } else {
		for (Bet bet : outcome.getBets()) {
		    Float sum = bet.getSum();
		    // create transaction
		}
	    }
	}
    }

    public static int getCountBets() {
	return countBets.get();
    }

    public static void resetCountBets() {
	countBets.set(0);
    }

}
