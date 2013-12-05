package juniors.server.core.logic.services;

import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.bets.Bet;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.log.Logs;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
public class BetsService {
	private static AtomicInteger countBets;
	static {
		countBets = new AtomicInteger(0);
	}

	public boolean makeBet(String login, int outcomeId, double sum) {
		countBets.incrementAndGet();
		Bet bet = DataManager.getInstance().makeBet(login, outcomeId, sum);
		if(bet == null) {
			Logs.getInstance().getLogger("Bets Service").warning("Betservice returns false");
			return false;
		}
		Logs.getInstance().getLogger("Bets Service").warning("Betservice works normally");
		return DataManager.getInstance().getBookmaker().addBet(bet);
	}

	public static void evalBets(Market market) {
		for (Outcome outcome : market.getOutcomeCollection()) {
			if (!outcome.getWin()) {
				for (Bet bet : outcome.getBets()) {
					Float sum = (float) 0;
					TransactionHelper.makeTransaction(bet, sum);
				}
			} else {
				for (Bet bet : outcome.getBets()) {
					Float sum = bet.getSum() * outcome.getCoefficient().floatValue();
					TransactionHelper.makeTransaction(bet, sum);
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