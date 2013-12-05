package juniors.server.core.robots;

import java.util.Random;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;

/**
 * Implementation of robot which bets randomly.
 * @author watson
 *
 */

public class RandomRobot extends AbstractRobot {

	public RandomRobot(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Generates outcome with absolutely randomly algorithm.
	 * @return - Outcome to bet, or null - it indicates what we will not bet now.
	 */
	@Override
	public Outcome generateOutcome() {
		try {
			Random r = new Random();
			Event[] eventsArray = null;
			Market[] marketsArray = null;
			Outcome[] outcomesArray = null;
			events.entrySet().toArray(eventsArray);
			Event event = eventsArray[r.nextInt(eventsArray.length)];

			event.getMarketsCollection().toArray(marketsArray);
			Market market = marketsArray[r.nextInt(marketsArray.length)];
			curMarket = market;
			market.getOutcomeCollection().toArray(outcomesArray);
			return outcomesArray[r.nextInt(outcomesArray.length)];
			
		} catch (IllegalArgumentException | NullPointerException e) {
			return null;
		}
	}
}
