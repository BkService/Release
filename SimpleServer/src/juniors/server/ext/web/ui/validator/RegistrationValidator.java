package juniors.server.ext.web.ui.validator;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import juniors.server.core.log.Logs;

public class RegistrationValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logs log = Logs.getInstance();
    
    public RegistrationValidator() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		log.getLogger("validator").info("Servlet regValidator run");
		
		String login = (String) request.getParameter("login");
		String passwd  = (String) request.getParameter("passwd");
		String rpasswd  = (String) request.getParameter("rpasswd");
		String visa = (String) request.getParameter("count");
		
		if(login.length() < 3 || passwd.length() < 6 || !passwd.equals(rpasswd) ||
												!Pattern.matches("^[0-9]{16}$", visa)) {
			request.getSession().setAttribute("msg", new String("Invalid data in registration form"));
			log.getLogger("validator").warning("servlet validator get not carry data ");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else {
			log.getLogger("validator").info("checked... redirect to create '" + login + "' user");
			request.getSession().setAttribute("msg", new String("Account <b>"+ login +"</b> added. SignIn, please."));
			request.getRequestDispatcher("/RegistrationHandler").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}
}
