package juniors.server.ext.web.xshell;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.EventService;
import juniors.server.core.logic.services.Services;

public class EventsCommand implements ICommand {
	
	/*
	 * FIXME
	 * not work events -tc
	 */

	private static final String manual = "		events - command for show events<br><br>" +
			"	SYNTAX<br>" +
			"		events [-flags]<br><br>" +
			"	OPTIONS<br>" +
			"		flags:<br>" +
			"			c - show only count actual events<br>" +
			"			t - show events with markets and outcomes in view tree<br>";

	private boolean COUNT_FLAG = false;
	private boolean TREE_FLAG = false;

	private static final String INVALID_COUNT_ARGS = "events: invalid count arguments";
	private static final String INVALID_KEYS = "events: invalid arguments";

	private static ServerFacade servfacade = ServerFacade.getInstance();

	@Override
	public String getName() {
		return "events";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res, String... args) {
		if(args.length > 1)
			return INVALID_COUNT_ARGS;
		String result = "";
		String[] allEvents = getEvents();
		if(allEvents == null)
			return "Not get events";
		if(allEvents.length == 0)
			return "No events";
		if(args.length == 0) {
			for(int i = 0; i < allEvents.length; ++i) {
				String br = (i == allEvents.length - 1) ? "" : "<br>";
				result += ((i + 1) + ") " + allEvents[i] + br);
			}
			return result;
		}
		if(!parseFlags(args[0]))
			return INVALID_KEYS;
		if(COUNT_FLAG) {
			COUNT_FLAG = false;
			result += "events: count events " + allEvents.length;
			if(TREE_FLAG)
				result += "<br><br>";
		}
		
		if(TREE_FLAG) {
			TREE_FLAG = false;
			result += getTree();
		}
		return result;
	}

	private boolean parseFlags(String arg) {
		if(!Pattern.matches("^-[ct]{0,2}$", arg)) {
			System.out.println("no regexp");
			return false;
		}
		for(char c : arg.toCharArray()) {
			if(c != 'c' && c != 't' && c != '-') {
				System.out.println(c + " - char not valid");
				return false;
			}
			if(c == 'c' && !COUNT_FLAG)
				COUNT_FLAG = true;
			else {
				if(c == 't' && !TREE_FLAG) {
					TREE_FLAG = true;
					continue;
				} else {
						if(c == '-')
							continue;
						else
							return false;
				}
			}
		}	
		return true;
	}

	private String[] getEvents(){
		Services services = servfacade.getServices();
		if(services == null) {
			servfacade.start();
			services = servfacade.getServices();
			if(services == null)
				return null;
		}
		EventService es = services.getEventService();
		if(es == null)
			return null;
		int size = es.getEventsMap().size();
		Event[] event = new Event[size];
		String[] result = new String[size];
		event = es.getEventsMap().toArray(event);
		for(int i = 0; i < size; ++i) {
			result[i] = event[i].getDescription();
		}
		return result;
	}
	
	private String getTree() {
		Services services = servfacade.getServices();
		if(services == null) {
			servfacade.start();
			services = servfacade.getServices();
			if(services == null)
				return null;
		}
		EventService es = services.getEventService();
		if(es == null)
			return null;
		int size = es.getEventsMap().size();
		Event[] event = new Event[size];
		String result = "";
		event = es.getEventsMap().toArray(event);
		for(int i = 0; i < size; ++i) {
			result += event[i].getDescription() + "<br>";
			int sizem = event[i].getMarketsMap().values().size();
			Market[] market = new Market[sizem];
			market = event[i].getMarketsMap().values().toArray(market);
			for(int m = 0; m < sizem; ++m){
				result += "	" + market[m].getDescription() + "<br>";
				int sizer = market[m].getOutcomeMap().values().size();
				Outcome[] outcome = new Outcome[sizer];
				outcome = market[m].getOutcomeMap().values().toArray(outcome);
				for(int r = 0; r < sizer; ++r){
					result += "		" + outcome[r].getDescription() + " | " + outcome[r].getCoefficient() + "<br>";
				}
			}
		}
		
		return result;
	}
	
	

	@Override
	public String getMan() {
		return manual;
	}

}
