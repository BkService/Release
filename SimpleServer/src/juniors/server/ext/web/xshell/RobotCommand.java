package juniors.server.ext.web.xshell;

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

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		if(args.length > 0)	//FIXME
			return "invalid arguments";
		Thread stream = new Thread(new RunnerRobots());
		stream.setDaemon(true);
		stream.start();
		return "ok";
	}

	@Override
	public String getMan() {
		return "	rm - command for start robots (Rise of Machines)<br>";
	}
	
	private class RunnerRobots implements Runnable {
		
		
		
		@Override
		public void run() {
			/* быдло код. по хорошему надо 
			 * принимать в качестве параметров
			 * количество юзеров и их стратегию */
			User[] users1 = new User[1];
			User[] users2 = new User[1];
			User[] users3 = new User[1];
			for(int i = 0; i < 1; ++i) {
				users1[i] = genUser();
				users2[i] = genUser();
				users3[i] = genUser();
			}
			(new RobotsManager(1, users1, RobotsManager.Strategy.GREEDY)).runRobots();
			(new RobotsManager(1, users2, RobotsManager.Strategy.RANDOM)).runRobots();
			(new RobotsManager(1, users3, RobotsManager.Strategy.SAFETY)).runRobots();
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

}
