package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.users.User;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;
import juniors.server.core.logic.services.BetsService;

public class CreatorBets extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreatorBets() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String bet = request.getParameter("bet");
		String outcome = request.getParameter("outcome");
		System.out.println(outcome);
		/* parsing bet to int. check bet and user balance. makeBet */
		try {
			int valueBet = Integer.parseInt(bet);
			int idOutcome = Integer.parseInt(outcome);
			BetsService bs = ServerFacade.getInstance().getServices().BetsService();
			if(!bs.makeBet(login, idOutcome, valueBet)) {
				response.getWriter().write("Can not create bet");
				return;
			}
		} catch(Exception ex) {
			response.getWriter().write("Can not create bet (EXCEPTION)");
			ex.printStackTrace();
			return;
		}
		
		response.getWriter().write("Bet '" + bet + "' added successfully.  <button onclick=\"hideFormMakeBet();\">OK</button>");
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
