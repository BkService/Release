package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.Services;

public class TestCommand implements ICommand {

	private static final String manual = "	test - command for monitoring system<br><br>" +
			"	SYNTAX<br>" +
			"		test -type [-time]<br><br>" +
			"	OPTIONS<br>" +
			"		type -time (autorization): <br>" +
			"			-guests -h :count success sign in system on hour<br>" +
			"			-guests -d :count success sign in system on day<br>" +
			"			-guests -m :count success sign in system on month<br>" +
			"		type -time (connections)<br>" +
			"			-connect -s :count connections by second<br>" +
			"			-connect -m :count connections by minute<br>" +
			"			-connect -h :count connections by hour<br>" +
			"			-connect -d :count connections by day<br>" +
			"		type <br>" +
			"			-regusers - get count users, which registered in system<br> " +
			"			-users - get count users, which are in system now<br>" +
			"			-bets - get count bets, which users making by second<br><br>" +
			"	EXAMPLES<br>" +
			"		test -connect -m<br>" +
			"		test -guests -h<br>" +
			"		test -users";
	private static final String INVALID_ARGS = "test: invalid arguments. read manual for help";
	
	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res, String... args) {
		Services servs = ServerFacade.getInstance().getServices();
		if(args.length == 0)
			return INVALID_ARGS;
		if(servs == null){
			return "Server error code 500";
		}
		
		switch(args[0]){
			case "-guests": {
				if(args.length != 2)
					return INVALID_ARGS;
				if(args[1].equals("-h"))
					return "test: logins per hour: " + servs.getStatisticService().getCountLoginsPerHour();
				if(args[1].equals("-d"))
					return "test: logins per day: " + servs.getStatisticService().getCountLoginsPerDay();
				if(args[1].equals("-m"))
					return "test: logins per month: " + servs.getStatisticService().getCountLoginsPerMonth();
				return INVALID_ARGS;
			}
			case "-connect" : {
				if(args.length != 2)
					return INVALID_ARGS;
				if(args[1].equals("-s"))
					return "test: connect per second: " + servs.getStatisticService().getCountRequestsPerSecond();
				if(args[1].equals("-m"))
					return "test: connect per minute: " + servs.getStatisticService().getCountRequestsPerMinute();
				if(args[1].equals("-h"))
					return "test: connect per hour: " + servs.getStatisticService().getCountRequestsPerHour();
				if(args[1].equals("-d"))
					return "test: connect per day: " + servs.getStatisticService().getCountRequestsPerDay();
				return INVALID_ARGS;
			}
			case "-regusers" : {
				return "test: storage users in system: " + servs.getStatisticService().getCountsUsers();
			}
			case "-users" : {
				return "option [users] in development";
			}
			case "-bets" : {
				return "test: bets per second: " + servs.getStatisticService().getCountBetsPerSeconds();
			}
			default : 
				return INVALID_ARGS;
		}
	}

	@Override
	public String getMan() {
		return manual;
	}

	@Override
	public String getShortDescription() {
		return "command for monitoring any parameters";
	}

}
