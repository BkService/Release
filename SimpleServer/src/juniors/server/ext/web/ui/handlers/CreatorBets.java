package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.users.User;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;

public class CreatorBets extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreatorBets() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String bet = request.getParameter("bet");
		
		/* parsing bet to int. check bet and user balance. makeBet */
		try {
			int valueBet = Integer.parseInt(bet);
			AccountsService as = ServerFacade.getInstance().getServices().getAccountsService();
			User user = as.getUser(login);
			if(user != null){
				user.changeBalance(-valueBet);
			}
			/* get user. and add bet to bets list */
		} catch(Exception ex) {
			response.getWriter().write("error!");
			return;
		}
		
		response.getWriter().write("Bet '" + bet + "' added successfully.");
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
