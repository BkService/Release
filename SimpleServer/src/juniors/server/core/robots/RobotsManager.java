package juniors.server.core.robots;

import juniors.server.core.data.users.User;

public class RobotsManager {
	
	static enum Strategy {RANDOM, SAFETY, GREEDY};
	
	int numberRobots;
	User[] users;
	AbstractRobot[] robots;
	Strategy strategy;
	
	
	public RobotsManager(int numberRobots, User[] users, Strategy strategy) {
		assert(numberRobots == users.length);
		this.users = users;
		this.strategy = strategy;
	}
	
	public void runRobots() {
		for (int i = 0; i < numberRobots; i++) {
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
	
	/**
	 * Here can be some statistic for robots.
	 */
	public void getStatistics() {
		
	}
	
}
