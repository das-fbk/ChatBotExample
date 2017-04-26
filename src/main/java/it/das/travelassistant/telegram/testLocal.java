package it.das.travelassistant.telegram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author antbucc
 * 
 */

/*
 * https://maps.googleapis.com/maps/api/directions/outputFormat?parameters
 * transit_mode = transit_mode=train|tram|subway|bus
 * 
 * alternatives — If set to true, specifies that the Directions service may
 * provide more than one route alternative in the response. Note that providing
 * route alternatives may increase the response time from the server.
 * 
 * Mode: driving (default) indicates standard driving directions using the road
 * network. - walking requests walking directions via pedestrian paths &
 * sidewalks (where available). - bicycling requests bicycling directions via
 * bicycle paths & preferred streets (where available). - transit requests
 * directions via public transit routes (where available). If you set the mode
 * to transit, you can optionally specify either a departure_time or an
 * arrival_time. If neither time is specified, the departure_time defaults to
 * now (that is, the departure time defaults to the current time). You can also
 * optionally include a transit_mode and/or a transit_routing_preference.
 */

public class testLocal {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		String from = "Povo";
		String to = "Piazza Dante";

		String latlngFrom = "46.075201,11.121040";
		// UNDERSTAND THE PROVINCE
		String result = callURL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ latlngFrom + "&sensor=false");
		System.out.println(result);

		// retrieve the city province
		JSONObject jsonObj = new JSONObject(result);

		JSONArray results = new JSONArray();
		results = jsonObj.getJSONArray("results");
		// results.get(0).address_components[i].short_name;

	}

	/*
	 * // AUTOCOMPLETE ADDRESS GooglePlaces client = new GooglePlaces(
	 * "AIzaSyBnLrMivSthmUmUipPfk5sidv7f0QvvDjg"); List<Place> placesFrom =
	 * client.getPlacesByQuery(from, GooglePlaces.MAXIMUM_RESULTS); List<Place>
	 * placesTo = client.getPlacesByQuery(to, GooglePlaces.MAXIMUM_RESULTS);
	 * 
	 * String completedFrom = placesFrom.get(0).getAddress() .replace(", ",
	 * "+"); completedFrom = completedFrom.replace(" ", "+"); String completedTo
	 * = placesTo.get(0).getAddress().replace(", ", "+"); completedTo =
	 * completedTo.replace(" ", "+");
	 * 
	 * String mode = "transit"; String GoogleAPIKey =
	 * "AIzaSyBnLrMivSthmUmUipPfk5sidv7f0QvvDjg"; String result =
	 * callURL("https://maps.googleapis.com/maps/api/directions/json?origin=" +
	 * completedFrom + "&destination=" + completedTo + "&key=" + GoogleAPIKey +
	 * "&mode=" + mode + "&alternatives=true"); System.out.println(result);
	 * JSONObject jsonObj = new JSONObject(result); JSONArray routes = new
	 * JSONArray(); routes = jsonObj.getJSONArray("routes"); for (int i = 0; i <
	 * routes.length(); i++) {
	 * 
	 * System.out.println("Soluzione " + i + ":"); JSONObject route =
	 * (JSONObject) routes.get(i);
	 * 
	 * JSONArray legs = route.getJSONArray("legs");
	 * System.out.println("NUMERO DI LEGS: " + legs.length());
	 * 
	 * }
	 */

	public static String callURL(String myURL) {
		// System.out.println("Requested URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL,
					e);
		}

		return sb.toString();
	}
}
