package juniors.server.core.robots;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;
import juniors.server.core.logic.TimeChecker;

/**
 * Implementation of greedy robot.
 * 
 * @author watson
 * 
 */

public class GreedyRobot extends AbstractRobot {

	TimeChecker timeChecker = new TimeChecker();

	public GreedyRobot(User user) {
		super(user);
	}

	/**
	 * Generates outcome with greedy algorithm. It finds outcome with maximal
	 * coefficient, such that we haven't bets on market of this outcome.
	 * 
	 * @return - outcome to bet, or null if we haven't outcomes to bet now.
	 */

	@Override
	public Outcome generateOutcome() {
		double maxCoeff = 0;
		Outcome res = null;
		for (Event event : events.values()) {
			if (!timeChecker.checkOccurred(event)) {
				for (Market market : event.getMarketsCollection()) {
					if (!hasBets.contains(market)) {
						for (Outcome outcome : market.getOutcomeCollection()) {
							if (outcome.getCoefficient() > maxCoeff) {
								maxCoeff = outcome.getCoefficient();
								res = outcome;
								curMarket = market;
							}
						}
					}
				}
			}
		}
		return res;
	}

	@Override
	public String nameToLogs() {
		return "--Greedy Robot : ";
	}

}
