package juniors.server.ext.web.xshell;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.logic.ServerFacade;

public class FeedLoaderCommand implements ICommand {

	private static final String manual = "<br>	feedloader - the command for work with feed loader.<br><br>" +
			"	SYNTAX<br>" +
			"		feedloader [options]<br><br>" +
			"	OPTIONS <br>" +
			"		[start] - for start feed loader<br>" +
			"		[stop] - for stop feed loader<br>" +
			"		[time] - time when the feed loader was started<br>" +
			"		if options not set - then show state feed loader";

	private Date timeStart = null;
	
	@Override
	public String getName() {
		return "feedloader";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res, String... args) {
		ServerFacade sf = ServerFacade.getInstance();
		if(args.length == 0)
			return sf.getStatusFL() ? "feedloader: work state" : "feedloader: stop state";
		if(args.length > 1)
			return "feedloader: invalid arguments... enter 'man feedloader' for get help";
		String result = "";
		String cmd = args[0];
		if(cmd.equals("start")) {
			if(sf.getStatusFL())
				return "  feed loader already run";
			sf.start();
			this.timeStart = new Date();
			result = "  starting feedloader... successfull";
		}
		if(cmd.equals("time")) {
			if(this.timeStart == null)
				return "feed loader not started";
			result = "  " + this.timeStart.toString();
		}
		if(cmd.equals("stop")) {
			if(!sf.getStatusFL())
				return "  feed loader already stoped";
			sf.stop();
			result = "  stop feed loader... succesfull";
		}
		return result;
	}

	@Override
	public String getMan() {
		return manual;
	}

}
