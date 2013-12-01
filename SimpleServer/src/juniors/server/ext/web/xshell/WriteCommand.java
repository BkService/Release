package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.log.Logs;

public class WriteCommand implements ICommand {

	Logs log = Logs.getInstance();
	
	@Override
	public String getName() {
		return "write";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		String result = "added";
		if(args.length < 1)
			return "Invalid arguments";
		for(String s : args) {
			log.getLogger("shell").info(s);
		}
		return result;
	}

	@Override
	public String getMan() {
		return "	write [args] - command for write messages to log<br><br>" +
				"	OPTIONS <br>" +
				"		args - words, which writes to log<br>";
	}
}
