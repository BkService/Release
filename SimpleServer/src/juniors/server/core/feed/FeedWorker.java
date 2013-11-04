package juniors.server.core.feed;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class FeedWorker {
	private static final String apiKey = "9dc8e488-D840-E311-bf8a-003048dd52d5";
	private static final String address = "http://xmlfeed.intertops.com/xmloddsfeed/v2/xml/?sportId=26&apikey="+ apiKey;
	private int delta;
	private boolean isFirstRequest;
	
	public FeedWorker(int delta) {
		this.delta = delta;
		isFirstRequest = true;
	}
	
	public InputStream update() {
		try {
			String requestAdress = address + "&delta=" + ((isFirstRequest) ? (delta * 200) : delta);
			isFirstRequest = false;
			URL url = new URL(requestAdress);
			URLConnection cоnn = url.openConnection();
			return cоnn.getInputStream();
		} catch (IOException e) { 
			System.out.println("Cannot get Bets");
		}  
		return null;
	}
}
