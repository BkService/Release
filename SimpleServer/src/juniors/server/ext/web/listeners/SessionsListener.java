/**
 * 
 */
package juniors.server.ext.web.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 * 
 */
@WebListener
public class SessionsListener implements HttpSessionListener {

	private static AtomicInteger countConnects = new AtomicInteger(0),
			countsDisconnects = new AtomicInteger(0);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		countConnects.incrementAndGet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		countConnects.decrementAndGet();
		countsDisconnects.incrementAndGet();
	}

	public static int getCountConnects() {
		return countConnects.get();
	}

	public static int getCountDisconnects() {
		return countsDisconnects.get();
	}
	
	public static void clearStaticInf() {
	    countConnects.set(0);
	    countsDisconnects.set(0);
	}

}
