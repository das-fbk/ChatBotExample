package it.das.travelassistant.telegram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * @author antbucc
 * 
 */

public class testBlaBlaCar {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String stringUrl = "https://public-api.blablacar.com/api/v2/trips?fn=Trento&tn=Rovereto";

		URL myURL = new URL(stringUrl);
		HttpURLConnection myURLConnection = (HttpURLConnection) myURL
				.openConnection();

		myURLConnection.setRequestMethod("GET");
		myURLConnection.setRequestProperty("accept", "application/json");
		myURLConnection.setRequestProperty("key",
				"78d56d3b77604b3daa428ab370cb9b41");

		myURLConnection.setRequestProperty("X-Requested-With", "Curl");

		InputStreamReader inputStreamReader = new InputStreamReader(
				myURLConnection.getInputStream());
		// read this input
		BufferedReader bR = new BufferedReader(inputStreamReader);
		String line = "";

		StringBuilder responseStrBuilder = new StringBuilder();
		while ((line = bR.readLine()) != null) {

			responseStrBuilder.append(line);
		}

		JSONObject result = new JSONObject(responseStrBuilder.toString());
		System.out.println(result);

	}
}
