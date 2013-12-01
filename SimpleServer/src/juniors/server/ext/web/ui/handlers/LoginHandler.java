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

public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginHandler() {
    	super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String username = request.getParameter("uname");
		String passwd = request.getParameter("passwd");
		String path = "/SwitchHandler";
		Services s = ServerFacade.getInstance().getServices();
		User user = null;
		if(username.equals("admin") && passwd.equals("sserver")) {
			path = "/console.jsp";
			request.getSession().setAttribute("adm", new Boolean(true));
		}
		else {
			if(s != null) {
			AccountsService accounts = s.getAccountsService();
			user = accounts.getUser(username); // username - is login
				if(user == null) {
					path = "index.jsp";
					request.getSession().setAttribute("msg", "User with the login and password doesn't exists");
				}
			} else {
				path = "index.jsp";
				request.getSession().setAttribute("msg", "Server not started. Please, contact with admin.");
			}
		}
		request.getSession().setAttribute("user", user);
		request.getRequestDispatcher(path).forward(request, response);
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
