package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.users.User;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;
import juniors.server.core.logic.services.Services;

public class BalanceProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BalanceProvider() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String login = request.getParameter("login");
		ServerFacade sf = ServerFacade.getInstance();
		Services services = sf.getServices();
		if(services == null){
			response.getWriter().write("error");
			return;
		}
		AccountsService as = services.getAccountsService();
		if(as == null) {
			response.getWriter().write("error");
			return;
		}
		User user = as.getUser(login);
		if(user == null) {
			response.getWriter().write("login error");
			return;
		}
		response.getWriter().write(String.valueOf(user.getBalance().getBalanceValue()));
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
