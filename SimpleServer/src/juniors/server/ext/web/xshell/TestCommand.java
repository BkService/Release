package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestCommand implements ICommand {

	private static final String manual = "	test - command for monitoring system<br><br>" +
			"	SYNTAX<br>" +
			"		test type -time<br><br>" +
			"	OPTIONS<br>" +
			"		type -time (autorization): <br>" +
			"			guests -h :count success sign in system on hour<br>" +
			"			guests -d :count success sign in system on day<br>" +
			"			guests -m :count success sign in system on month<br>" +
			"		type -time (connections)<br>" +
			"			connect -s :count connections by second<br>" +
			"			connect -m :count connections by minute<br>" +
			"			connect -h :count connections by hour<br>" +
			"			connect -d :count connections by day<br>" +
			"		type <br>" +
			"			regusers - get count users, which registered in system<br> " +
			"			users - get count users, which are in system now<br>" +
			"			bets - get count bets, which users making by second<br><br>" +
			"	EXAMPLES<br>" +
			"		test connect -m<br>" +
			"		test guests -h<br>" +
			"		test users";
	
	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		String result = "command 'test' in development";
		/*
		 * Add code for get statistics
		 * when Dima make StatisticService
		 */
		return result;
	}

	@Override
	public String getMan() {
		return manual;
	}

}
