package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.EventService;

public class BetHandler extends HttpServlet {
	private static final long serialVersionUID = 198L;
	private Logs log = Logs.getInstance();

    public BetHandler() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		log.getLogger("betHandler").info("run servlet BetHandler");
		String sid = request.getParameter("outcome");
		String result = "";
		int iid = 0;
		try{
			iid = Integer.parseInt(sid);
			EventService es = ServerFacade.getInstance().getServices().getEventService();
			exit: for(Event e : es.getEventsMap()) {
				for(Market m : e.getMarketsMap().values()) {
					for(Outcome r : m.getOutcomeMap().values()) {
						if(iid == r.getOutcomeId()){
							log.getLogger("betHandler").info("needed outcome finded");
							result = "Event:	" + e.getDescription() + 
									 "<br>	Market:	 " + m.getDescription() +
									 "<br>	Outcome:  " + r.getDescription() +
									 "<br>	Coef-nt:  <span id=\"COEFFICIENT\">" + r.getCoefficient() + "</span>";
							break exit;
						}
					}
				}
			}
			if(result != "")
				response.getWriter().write(result);
			else response.getWriter().write("error");
		}catch(Exception ex){
			response.getWriter().write("error");
			log.getLogger("betHandler").warning("error search outcome");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		service(request, response);
	}

}
