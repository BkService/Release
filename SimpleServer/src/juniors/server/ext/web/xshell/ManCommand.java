package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManCommand implements ICommand {

	private static final String DEFAULT_MAN = "		Manual of the wshell interpretator. Commands:<br>" +
			"	clear 		- delete history.<br>" +
			"	feedloader 	- work with the feed loader<br>" +
			"	info		- command for get information about server<br>" +
			"	test		- command for get statistic of connections and users<br>" +
			"	user		- command for create, update and remove users<br>" +
			"	events		- command for show information about events<br>" +
			"	exit 		- exit from webshell<br>" +
			"	man		- show manual about command";
	
	private static final String MANUAL_FOR_MAN = "		man - command for show manual about command<br><br>" +
			"	SYNTAX<br>" +
			"		man [command]<br><br>" +
			"	OPTIONS <br>" +
			"		[command] - name of the command<br>";
	
	private static final String EXIT_MAN = "		exit - command for quit from WebShell (wsh)<br><br>" +
			"		SYNTAX<br>" +
			"			exit";

	private static final String CLEAR_MAN = "		clear - command for delete history of requests in WebShell<br><br>" +
			"		SYNTAX<br>" +
			"			clear";

	@Override
	public String getName() {
		return "man";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res, String... args) {
		if(args.length == 0)
			return DEFAULT_MAN;
		String description = "";
		CommandManager cm = CommandManager.getInstance();
		for(String cmd : args) {
			if(cmd.equals("exit")) {
				return EXIT_MAN;
			}
			if(cmd.equals("clear")) {
				return CLEAR_MAN;
			}
			ICommand command = cm.getCommand(cmd);
			if(command == null) {
				description += "man: " + cmd + " not found";
				continue;
			}
			String man = command.getMan();
			if(man == null) {
				description += "<br>man: man page not found for command " + cmd;
				continue;
			}
			description += man;
		}
		return description;
	}

	@Override
	public String getMan() {
		return MANUAL_FOR_MAN;
	}
}
