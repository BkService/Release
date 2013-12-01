package juniors.server.core.robots;

import java.util.Set;

import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;

public class GreedyRobot extends AbstractRobot {
	
	Set<Market> hasBets;
	
	public GreedyRobot(User user) {
		super(user);
	}

	@Override
	public Outcome generateOutcome() {
		return null;
		
	} 
	
}
