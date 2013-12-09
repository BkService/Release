package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManCommand implements ICommand {
	
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
		CommandManager cm = CommandManager.getInstance();
		if(args.length == 0) {
			StringBuilder retstr = new StringBuilder();
			for(String s : cm.getCommandsDescriptions()) {
				retstr.append("	");
				retstr.append(s);
				retstr.append("<br>");
			}
			retstr.append("		about 		- get info about terminal.<br>");
			retstr.append("		clear 		- delete history.<br>");
			retstr.append("		exit 		- exit from webshell");
			return retstr.toString();
		}
		String description = "";
		for(String cmd : args) {
			if(cmd.equals("exit")) {
				description += EXIT_MAN;
				continue;
			}
			if(cmd.equals("clear")) {
				description += CLEAR_MAN;
				continue;
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

	@Override
	public String getShortDescription() {
		return "show manual about command";
	}
}
