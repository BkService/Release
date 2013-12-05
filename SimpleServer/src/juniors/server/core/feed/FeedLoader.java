package juniors.server.core.feed;

import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.DaemonThreadFactory;
import juniors.server.core.logic.RunnableService;

import org.xml.sax.SAXException;

/**
 * Service that supply events, markets, outcomes and their coefficients.
 * 
 * @author watson
 * 
 */

	public class FeedLoader implements RunnableService {
		
		
		
	/**
	 * Helper to establish connection with provider and gets information from it.
	 */
	private FeedWorker feedWorker;
	
	/**
	 * Helper to parse XML which was got from provider.
	 */
	private FeedParser feedParser;
	
	
	/**
	 * Service to refresh information every minute.
	 * 
	 */
	ScheduledExecutorService service;
	
	/**
	 * Delay between activities of feed.
	 */
	private int delaySec = 60;
	
	boolean isStarted = false;

	Logger logger;
	
	
	/**
	 * Constructs feedWorker and feedParser.
	 */
	public FeedLoader() {
		logger = Logs.getInstance().getLogger("Feed");
		feedWorker = new FeedWorker(2 * delaySec / 60);
		try {
			feedParser = new FeedParser();
		} catch (ParserConfigurationException | SAXException e) {
			logger.severe("Cannot create SAX parser, cause" + e.getMessage());
			logger.severe("FeedLoader don't work");
		}
		logger.info("FeedLoader is created");
	}

	/**
	 * Starts feedLoader.
	 */
	@Override
	public void start() {
		if (!isStarted) {
			service = Executors.newScheduledThreadPool(5,
					new DaemonThreadFactory());
			isStarted = true;
			service.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					update();
				}
			}, 0, delaySec, TimeUnit.SECONDS);
			logger.info("FeedLoader is started");
		}
	}

	@Override
	public void stop() {
		isStarted = false;
		service.shutdown();
		logger.info("FeedLoader is stopped");

	}

	@Override
	public boolean isStarted() {
		return isStarted;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TimeUnit getTimeUnitDelay() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Updates data.
	 * Gets InputStream from provider. 
	 * And gives this InputStream to parser. 
	 */
	public void update() {
		logger.info("start updating");
		InputStream is = feedWorker.update();
		if (is != null) {
			feedParser.parse(is);
		}
		logger.info("finish updating");
	}
}
