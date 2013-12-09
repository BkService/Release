package juniors.server.ext.web.xshell;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.users.User;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;
import juniors.server.core.robots.*;

public class RobotCommand implements ICommand {

	@Override
	public String getName() {
		return "rm";
	}

	static ArrayList<User> users = new ArrayList<User>();
	static ArrayList<RobotsManager.Strategy> strategies = new ArrayList<RobotsManager.Strategy>();

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		if ((args.length == 1) && (args[0].equals("-b"))) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < users.size(); i++) {
				sb.append(strategies.get(i) + " " +users.get(i).getLogin() + " " + users.get(i).getBalance().getBalanceValue() + "<br>");
			}
			return sb.toString();
		}

		if ((args.length > 2) || (args.length <= 0))	
			return "invalid arguments";

		int count = 1;
		RobotsManager.Strategy strat = RobotsManager.Strategy.RANDOM;
		String result = "ok";
		try {
			count = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			result = "not carry number format. using default option: count = 1";
		}
		if(args.length > 1)
			switch(args[1]) {
			case "-g" : strat = RobotsManager.Strategy.GREEDY; break;
			case "-r" : strat = RobotsManager.Strategy.RANDOM; break;
			case "-s" : strat = RobotsManager.Strategy.SAFETY; break;
			}
		Thread stream = new Thread(new RunnerRobots(count, strat));
		stream.setDaemon(true);
		stream.start();
		return result;
	}

	@Override
	public String getMan() {
		return "	rm - command for start robots (Rise of Machines)<br><br>" +
				"		rm [number] [strategy {g, s, r}] OR rm -b (for get balances all robots)<br>" +
				"			-g - greedy<br>" +
				"			-s - safety<br>" +
				"			-r - random<br>" +
				"	'rm number' - is carry query, but 'rm [strategy]' -not carry, needed count robots.";
	}

	private class RunnerRobots implements Runnable {

		int count = 0;
		RobotsManager.Strategy strategy = null;

		public RunnerRobots(int n, RobotsManager.Strategy strat) {
			this.count = n;
			this.strategy = strat;
		}

		@Override
		public void run() {
			User[] users = new User[count];
			for(int i = 0; i < count; ++i) {
				users[i] = genUser();
			}

			(new RobotsManager(count, users, strategy)).runRobots();
		}

		private User genUser() {
			String login = makeWord(10, false);
			String name = "someName";
			String surename = "someSurename";
			String password = makeWord(8, false);
			String bank = makeWord(16, true);
			AccountsService as = ServerFacade.getInstance().getServices().getAccountsService();
			User user = new User(login, name, surename, password, bank);
			if(!as.checkUser(user))
				as.addUser(user);
			users.add(DataManager.getInstance().getUser(login));
			strategies.add(strategy);
			return DataManager.getInstance().getUser(login);
		}

		private String makeWord(int length, boolean onlyDigits) {
			StringBuilder result = new StringBuilder();
			Random rnd = new Random();
			for(int i = 0; i < length; ++i) {
				int num = rnd.nextInt(10);
				if(!onlyDigits) {
					char c = (char)('a' + rnd.nextInt(26));
					if(rnd.nextBoolean())
						result.append(c);
					else 
						result.append(num);
					continue;
				} else {
					result.append(num);
				}
			}
			return result.toString();
		}
	}

	@Override
	public String getShortDescription() {
		return "command for work with robots.";
	}

}