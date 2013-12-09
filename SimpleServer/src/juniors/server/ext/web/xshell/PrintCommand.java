package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.log.LogsRecord;

public class PrintCommand implements ICommand {

	private Logs log = Logs.getInstance();

	@Override
	public String getName() {
		return "print";
	}

	int n = 10;

	private static String INV = "Invalid arguments";

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		if(args.length > 2 || args.length == 1)
			return INV;
		if(args.length > 0)
			if(args[0].equals("-n")) {
				try {
					n = Integer.parseInt(args[1]);
				} catch(NumberFormatException ex) {
					log.getLogger("shell").warning("key -n have not carry value. will be used default value");
				}
			}
		StringBuilder result = new StringBuilder();
		for(LogsRecord x :log.getBuffer().getLastRecords(n)) {
			result.append(x.getDate().toString());
			result.append(" | ");
			result.append(x.getMessage());
			result.append("<br>");
		}
		n = 10;
		return result.toString();
	}

	@Override
	public String getMan() {
		return "	print - command for print items from log<br><br>" +
				"	OPTIONS<br>" +
				"		-n number - set count items, which printed from log<br>";
	}

	@Override
	public String getShortDescription() {
		return "command for show items from logger";
	}

}
