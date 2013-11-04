package juniors.server.ext.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import juniors.server.core.logic.ServerFacade;

/**
 * Для запуска тасков после деплоя и остановки соответствующих.  
 * 
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
@WebListener
public class RunnerContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ServerFacade.getInstance().stop();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServerFacade.getInstance().start();
	}

}
