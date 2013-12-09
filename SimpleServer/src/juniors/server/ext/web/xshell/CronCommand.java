package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CronCommand implements ICommand {

	@Override
	public String getName() {
		return "cron";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		
		return "cron in development";
	}

	@Override
	public String getMan() {
		return "	cron - command for write or print log on set times<br><br>" +
				"		SYNTAX <br>" +
				"			cron [-p] [-w [message]] -time [seconds]<br><br>" +
				"		OPTIONS <br>" +
				"			-p OR -w set code of operation: print OR write<br>" +
				"			message - message, which will be write to log. default msg = 'cron action'<br>" +
				"			-time [seconds] set interval time for cron. default time = '10 sec'";
	}

	@Override
	public String getShortDescription() {
		return "command for generate plan of actions";
	}

}
