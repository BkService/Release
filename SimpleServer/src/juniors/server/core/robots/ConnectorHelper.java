package juniors.server.core.robots;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import juniors.server.core.data.events.Event;

/**
 * Provides interface to interact with server.
 * (Send requests and get responses).
 * @author watson
 *
 */

public class ConnectorHelper {
	/**
	 * Address of server.
	 */
	private static final String address = "http://localhost:8080/SimpleServer/Connector/";

	public ConnectorHelper() {

	}
	/**
	 * Sends GET request to server to get all bets.
	 * @return - events in map, which was received from server or null, if there are occurred problems.  
	 */ 
	public Map<Integer, Event> sendGetBetsRequest() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(address + "cop=getBets");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	
			InputStream is = connection.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			return (Map<Integer, Event>) ois.readObject();
		} catch (IOException e) {
			System.err.println("Problems with connection");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Can't parse recieved data");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * Sends POST request to make a bet.
	 * @param login - user's login.
	 * @param password - users's password.
	 * @param outcomeId - outcome to bet.
	 * @param sum - sum of bet.
	 * @return - Acknowledgment of making bet.
	 */
	public boolean sendMakeBetRequest(String login, String password,
			int outcomeId, double sum) {
		URL url;
		HttpURLConnection connection = null;
		String urlParameters = "cop=makeBet" + "&login=" + login + "&password=" + password
				+ "&outcomeId=" + outcomeId + "&sum=" + sum;
		try {
			// Create connection
			url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			return ois.readBoolean();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return false;
	}
}
