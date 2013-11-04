package juniors.server.core.feed;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class FeedLoader implements Runnable {
	private int periodSec = 60;
	private FeedWorker feedWorker;
	private FeedParser feedParser;
	public FeedLoader() {
		feedWorker = new FeedWorker(2 * periodSec / 60);
		try {
			feedParser = new FeedParser();
		} catch (ParserConfigurationException | SAXException e) {
			System.err.println("Cannot create SAX parser");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, periodSec * 1000);
		
	}
	public void update() {
		InputStream is = feedWorker.update();
		feedParser.parse(is);
	}
}
