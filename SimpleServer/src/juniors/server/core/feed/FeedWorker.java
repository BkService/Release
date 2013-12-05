package juniors.server.core.feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;

/**
 * Works with remote provider.
 * (Also see documentation of feed provider http://xmlfeed.intertops.com/xmloddsfeed/ ).
 * @author watson
 * 
 */

public class FeedWorker {
	/**
	 * Key to access to provider.
	 */
	private static final String apiKey = "9dc8e488-D840-E311-bf8a-003048dd52d5";
	/**
	 * Address of provider.
	 */
	private static final String address = "http://xmlfeed.intertops.com/xmloddsfeed/v2/xml/?apikey="
			+ apiKey;
	/**
	 * Parameter, which transmitted to provider. 
	 * Provider takes updates to us that was between (current time - deltaSec) and (current time).
	 */
	private int deltaSec;
	
	/**
	 * If we make first request we set big deltaSec to get a lot of events.
	 */
	private boolean isFirstRequest;
	
	/**
	 * Constructs FeedWorker with defined deltaSec.
	 * @param delta
	 */
	
	Logger logger;
	
	public FeedWorker(int deltaSec) {
		this.deltaSec = deltaSec;
		isFirstRequest = true;
		logger = Logs.getInstance().getLogger("feed");
	}
	
	/**
	 * Connects to the provider and gets information.
	 * @return - InputStream from provider with data in XML.
	 */
	public InputStream update() {
		InputStream is = null;
		try {
			logger.info("FeedWorker started to get InputStream from provider");
			String requestAdress = address + "&delta="
					+ ((isFirstRequest) ? (deltaSec * 100) : deltaSec);
			isFirstRequest = false;
			URL url = new URL(requestAdress);
			HttpURLConnection cоnn = (HttpURLConnection) url.openConnection();
			while (cоnn.getResponseCode() >= 400) { 
				cоnn = (HttpURLConnection) url.openConnection();
			}
			is = cоnn.getInputStream();
			logger.info("FeedWorker finished to get InputStream from provider. All is ok.");
		} catch (IOException e) {
			logger.warning("Cannot establish connection, cause:" + e.getMessage());
		}
		return is;
	}
}
