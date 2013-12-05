package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.data.users.User;
import juniors.server.core.logic.ServerFacade;
import juniors.server.core.logic.services.AccountsService;

public class RegistrationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public RegistrationHandler() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = (String) request.getParameter("firstName");
		String lname = (String) request.getParameter("lastName");
		String login = (String) request.getParameter("login");
		String passwd  = (String) request.getParameter("passwd");
		String visa = (String) request.getParameter("count");
		
		if(lname == null || lname.isEmpty())
			lname = "____";
		
		
		User user = new User(login, name, lname, passwd, visa);

		AccountsService account = ServerFacade.getInstance().getServices().getAccountsService();
		if(!account.checkUser(user)) {
			account.addUser(user);
			request.getSession().setAttribute("msg", new String("User with login <b>" + login + "</b> created successfully"));
		} else
			request.getSession().setAttribute("msg", new String("User with login <b>" + login + "</b> exists"));
		request.getRequestDispatcher("/").forward(request, response);
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
