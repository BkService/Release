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
		String oldLines = (String)request.getSession().getAttribute("shell");
		String br = (oldLines == "") ? oldLines : "<br>";
		String resultCommand = "";
		oldLines = oldLines + br  + "admin@simpleserver ~ $ " + inputLine;
		/* SYSTEMS COMMANDS. Integrated in servlet */
		if(inputLine.equals("exit")) {
			path = "/LogoutHandler";
		} else { 
			if(inputLine.equals("clear")) { 
				oldLines="";
			} else {
				if(inputLine.equals("")) {
					resultCommand = inputLine;
				} else {
					/* EXTERNAL COMMANDS. Integrated in system of commands */
					String args[] = inputLine.split("\\s+");
					inputLine = args[0];
					args = Arrays.copyOfRange(args, 1, args.length);
					CommandManager cmdManager = CommandManager.getInstance();
					ICommand command = cmdManager.getCommand(inputLine);
					if(command != null)
						resultCommand += "<pre>" + command.action(request, response, args) + "</pre>";
					else
						resultCommand = "<br>Command '" + inputLine + "' not found";
					oldLines += resultCommand;
				}
			}
		}
		request.getSession().setAttribute("shell", oldLines);
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

	protected String execute(String cmd, HttpServletRequest request) {
		String result = "<br>command not found. input 'man' for get help";
		if(cmd.equals("info")) {
			result = "<br>Server name: " + request.getServerName() + ". " +
					"Server port: " + request.getServerPort() + ". " +
					" Scheme: " + request.getScheme();
		}
		if(cmd.equals("block")) {
			result = "<br><div id=\"g" +getNextId()+ "\" class=\"grInfo\">" +
					"description graphic. Some graphic will be here." +
					"<div class=\"close\" onclick=\"closeGr('g"+ getBack() +"');\">-</div>" +
					"<br><img class=\"graphic\" href=\"/GrServlet?type=test\" />" +
					"</div><br>";
		}
		return result;
	}

	int nextId = 1;
	private int getNextId() {
		return nextId++;
	}
	private int getBack() {
		return this.nextId - 1;
	}

}
