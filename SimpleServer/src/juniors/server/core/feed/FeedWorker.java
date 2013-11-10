package juniors.server.core.feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedWorker {
    private static final String apiKey = "9dc8e488-D840-E311-bf8a-003048dd52d5";
    private static final String address = "http://xmlfeed.intertops.com/xmloddsfeed/v2/xml/?apikey="
	    + apiKey;
    private int delta;
    private boolean isFirstRequest;

    public FeedWorker(int delta) {
	this.delta = delta;
	isFirstRequest = true;
    }

    public InputStream update() {
	InputStream is = null;
	try {
	    String requestAdress = address + "&delta="
		    + ((isFirstRequest) ? (delta * 100) : delta);
	    isFirstRequest = false;
	    System.out.println(requestAdress);
	    URL url = new URL(requestAdress);
	    HttpURLConnection cоnn = (HttpURLConnection) url.openConnection();
	    while (cоnn.getResponseCode() >= 400) {
		cоnn = (HttpURLConnection) url.openConnection();
	    }
	    is = cоnn.getInputStream();
	} catch (IOException e) {
	    System.out.println("Cannot establish connection, cause:");
	    e.printStackTrace();
	}
	return is;
    }
}
