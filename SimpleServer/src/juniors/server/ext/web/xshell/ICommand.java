package juniors.server.ext.web.xshell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface ICommand {

	String getName();
	String action(HttpServletRequest req, HttpServletResponse res, String ... args);
	String getMan();
	String getShortDescription();
}
