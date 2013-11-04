package juniors.server.ext.web.ui.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SwitchHandler() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pgname = request.getParameter("target");
		if(pgname == null || pgname.isEmpty())
			pgname = "main";
		request.getSession().setAttribute("pagetype", pgname);
		request.getRequestDispatcher("/cabinet.jsp").forward(request, response);
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
