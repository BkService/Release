package juniors.server.core.feed;

import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import juniors.server.core.logic.DaemonThreadFactory;
import juniors.server.core.logic.RunnableService;

import org.xml.sax.SAXException;

public class FeedLoader implements RunnableService {

    private FeedWorker feedWorker;
    private FeedParser feedParser;
    private int periodSec = 60;
    ScheduledExecutorService service;
    boolean isStarted = false;

    public FeedLoader() {
	feedWorker = new FeedWorker(2 * periodSec / 60);
	try {
	    feedParser = new FeedParser();
	} catch (ParserConfigurationException | SAXException e) {
	    System.err.println("Cannot create SAX parser, cause");
	    e.printStackTrace();
	}
    }

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
	    }, 0, periodSec, TimeUnit.SECONDS);
	}
    }

    @Override
    public void stop() {
	isStarted = false;
	service.shutdown();

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

    public void update() {
	InputStream is = feedWorker.update();
	if (is != null) {
	    feedParser.parse(is);
	}
    }
}
