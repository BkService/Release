package juniors.server.core.robots;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectorHelper {
	private static final String address = "http://localhost:8080/SimpleServer/Connector/";

	public ConnectorHelper() {

	}
	/**
	 * 
	 * @return
	 */
	public String sendGetBetsRequest() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(address + "cop=getBets");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	//Send POST request to get all available bets.
	public String sendMakeBetRequest(String login, String password,
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
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
