package juniors.server.core.robots;


import java.util.ArrayList;
import java.util.List;
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
			List<Event> eventsList ;
			List<Market>  marketsList = null;
			List<Outcome> outcomesList = null;
			eventsList = new ArrayList<Event>(events.values()); 
			Event event = eventsList.get(r.nextInt(eventsList.size()));

			
			
			marketsList = new ArrayList<Market>(event.getMarketsMap().values());
			Market market = marketsList.get(r.nextInt(marketsList.size()));
			curMarket = market;
			
			outcomesList = new ArrayList<Outcome>(market.getOutcomeMap().values());
			return outcomesList.get(r.nextInt(outcomesList.size()));
			
		} catch (IllegalArgumentException | NullPointerException e) {
			return null;
		}
	}


	@Override
	public String nameToLogs() {
		return "--Random Robot : ";
	}
}
