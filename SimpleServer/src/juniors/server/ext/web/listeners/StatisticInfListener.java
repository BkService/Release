package juniors.server.ext.web.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Слушатель для подсчёта количества
 * авторизированных пользователей
 * 
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
@WebListener
public class StatisticInfListener implements HttpSessionAttributeListener,
		HttpSessionListener {

	private static AtomicInteger countAuthUsers, countOnlineUsers;
	static {
		countAuthUsers = new AtomicInteger(0);
		countOnlineUsers = new AtomicInteger(0);
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		if (arg0.getName().equals("user") && arg0.getValue() != null) {
			countAuthUsers.incrementAndGet();
			countOnlineUsers.incrementAndGet();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	public static int getCountOnlineUsers() {
		return countOnlineUsers.get();
	}

	public static int getCountAuthUsers() {
		return countAuthUsers.get();
	}

	public static void resetStaticInf() {
		countAuthUsers.set(0);
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		countOnlineUsers.decrementAndGet();
	}

}
