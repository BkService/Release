package juniors.server.ext.web.xshell;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebShell extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WebShell() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String inputLine = request.getParameter("command");
		String path = "/console.jsp";
		String history = (String)request.getSession().getAttribute("shell");
		if(history != null) 
			history.replaceAll("^\\s+<br>", "");
		Boolean br = true;
		request.getSession().setAttribute("br", br);
		history = (history == null || history.isEmpty()) ? "" : (history + "<br>");
		String resultCommand = history + "admin@simpleserver ~ $ " + inputLine ;
		/* SYSTEMS COMMANDS. Integrated in servlet */
		if(inputLine.equals("exit")) {
			path = "/LogoutHandler";
		} else { 
			if(inputLine.equals("clear")) { 
				resultCommand="";
			} else {
				if(!inputLine.equals("")) {
					/* EXTERNAL COMMANDS. Integrated in system of commands */
					String args[] = inputLine.split("\\s+");
					inputLine = args[0];
					args = Arrays.copyOfRange(args, 1, args.length);
					CommandManager cmdManager = CommandManager.getInstance();
					ICommand command = cmdManager.getCommand(inputLine);
					if(command != null)
						resultCommand += "<br><br><span style=\"white-space: pre;\">" + command.action(request, response, args) + "</span><br>";
					else
						resultCommand += "<br><br>Command '" + inputLine + "' not found<br>";
				}
			}
		}
		request.getSession().setAttribute("shell", resultCommand);
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
