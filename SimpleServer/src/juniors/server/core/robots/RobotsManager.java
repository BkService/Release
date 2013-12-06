package juniors.server.core.robots;



import juniors.server.core.data.markets.Outcome;
import juniors.server.core.data.users.User;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;


/**
 * Manager of robots.
 * Provides and implements interface to run testing robots.
 * @author watson
 *
 */

public class RobotsManager {
	/**
	 * We have three strategies to create robots.
	 * Random - robots will choose random event, random market, and random outcome to bet.
	 * Greedy - robots will choose maximal coefficient of all outcomes. These robots will not make bet to one market twice.
	 * Safety - robots will choose minimal coefficient of all outcomes. These robots will not make bet to one market twice. 
	 * @author watson
	 *
	 */
	public static enum Strategy {RANDOM, SAFETY, GREEDY};
	
	/**
	 * Number of robots to run.
	 */
	int numberRobots;
	
	/**
	 * Users, which will be used for robot's bets.
	 */
	User[] users;
	
	/**
	 * Robots, which was run this manager.
	 */
	AbstractRobot[] robots;
	
	/**
	 * Strategy for robots which will be run this manager.
	 */
	Strategy strategy;
	
	Logger logger = Logs.getInstance().getLogger("Robots");
	
	/**
	 * @param numberRobots - number of robots to run. 
	 * @param users - registered in system users for robots.
	 * @param strategy - strategy for robots.
	 */
	public RobotsManager(int numberRobots, User[] users, Strategy strategy) {
		logger.info("Robots manager created with strategy :" + strategy);
		this.numberRobots = numberRobots;
		robots = new AbstractRobot[numberRobots] ;
		this.users = users;
		this.strategy = strategy;
		
	}
	
	/**
	 * Runs robots with defined strategy.
	 * Every robot will work in your own thread 
	 * and will try to make a bet every minute.
	 */
	
	public void runRobots() {
		logger.info("Robots manager runs");
		for (int i = 0; i < numberRobots; i++) {
			System.out.println(i);
			if (strategy == Strategy.SAFETY) {
				robots[i] = new SafetyRobot(users[i]);
			}
			if (strategy == Strategy.RANDOM) {
				robots[i] = new RandomRobot(users[i]);
			}
			if (strategy == Strategy.GREEDY) {
				robots[i] = new GreedyRobot(users[i]);
			}
			robots[i].run();
		}
	}
	
	
	
	
}
