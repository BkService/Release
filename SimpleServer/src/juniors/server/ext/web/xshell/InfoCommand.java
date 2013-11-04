package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoCommand implements ICommand {

	private static final String manual = "	info - command for get information about server and WebShell<br><br>" +
			"	SYNTAX<br>" +
			"		info [[shell] | [server [port] [host] [protocol]]]<br><br>" +
			"	OPTIONS<br>" +
			"		[shell] - get info about WeShell <br>" +
			"		[server] - get information about server. The option have next keys:<br>" +
			"			[port] - get port, which used as default port for HTTP requests<br>" +
			"			[host] - get host name<br>" +
			"			[protocol] - get using protocol<br>" +
			"	if options not set, then show result of check ping";

	@Override
	public String getName() {
		return "info";
	}

	private static final String INVALID_ARGS = "info: invalid arguments... input 'man info' for get more information";

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res, String... args) {
		if(args.length == 0)
			return "info: check ping... OK";
		if(args.length > 4)
			return INVALID_ARGS;
		if(args[0].equals("shell")) {
			if(args.length != 1) 
				return INVALID_ARGS;
			return "info: wsh: WebShell (C) Juniors Simple Server<br><br>" +
					"	Version	: 2.0.0<br>" +
					"	Author	: Alexey Pismak<br>" +
					"	wsh	: Nov 2013";
		}
		String result = "";
		if(args[0].equals("server")) {
			if(args.length == 1)
				return "info: <br>" +
				"	host: " + req.getServerName() + " <br>" +
				"	port: " + req.getServerPort() + " <br>" +
				"	protocol: " + req.getScheme() + " <br>";
			result += "info: <br>";
			for(int i = 1; i < args.length; ++i) {	
				boolean carryFlag = false;
				if(args[i].equals("host")) {
					result += "	host: " + req.getServerName() + " <br>";
					carryFlag = true;
				}
				if(args[i].equals("port")) {
					result += "	port: " + req.getServerPort() + " <br>";
					carryFlag = true;
				}
				if(args[i].equals("protocol")) {
					result += "	protocol: " + req.getScheme() + " <br>";
					carryFlag = true;
				}
				if(!carryFlag)
					return INVALID_ARGS;
			}
		}
		else
			return INVALID_ARGS;
		return result;
	}

	@Override
	public String getMan() {
		return manual;
	}

}
