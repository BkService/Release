package juniors.server.ext.emulator;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.log.Logs;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;
import juniors.server.core.logic.services.BetsService;
import juniors.server.core.logic.services.EventService;

public class Connector extends HttpServlet {
	private static final long serialVersionUID = 20L;

	private Logs log = Logs.getInstance();
	
	public Connector() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		log.getLogger("connector").info("connector get request");
		String cop = request.getParameter("cop");
		ServletOutputStream sos = response.getOutputStream();
		ObjectOutputStream ous = new ObjectOutputStream(sos);
		if(cop == null || cop.isEmpty())
			return;
		if(cop.equals("getBets")) {
			EventService sf = null;
			try {
				sf = ServerFacade.getInstance().getServices().getEventService();
			} catch (NullPointerException ex) {
				log.getLogger("connector").warning("fuck... server not started");
				return;
			}
			ous.writeObject(sf.getEventsMap());
			return;
		}
		if(cop.equals("makeBet")) {
			String login = request.getParameter("login");
			String passwd = request.getParameter("password");
			String outcome = request.getParameter("outcomeId");
			String sum = request.getParameter("sum");
			if(login == null || passwd == null || outcome == null || sum == null)
				return;
			double sumValue = 0;
			int outcomeIdValue = 0;
			try {
				sumValue = Double.parseDouble(sum);
				outcomeIdValue = Integer.parseInt(outcome);
			} catch (NumberFormatException ex) {
				log.getLogger("connector").warning("fuck... robot get me not carry format number(s)");
				return;
			}
			AccountsService as = null;
			BetsService bs = null;
			try {
				as = ServerFacade.getInstance().getServices().getAccountsService();
				bs = ServerFacade.getInstance().getServices().BetsService();
			} catch (NullPointerException ex) {
				log.getLogger("connector").warning("fuck... server not started");
				return;
			}
			if(as.getUser(login) == null || !as.getUser(login).getPassword().equals(passwd))
				return;
			ous.writeBoolean(bs.makeBet(login, outcomeIdValue, sumValue));
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
