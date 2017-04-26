package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


public class ViaggiaTrentoAPIWrapper {

/*	https://dev.smartcommunitylab.it/
		smart-planner2/
		trentino/
		rest/plan?
		from=46.066591%2C11.15&to=46.164298%2C11.002572
		&date=02%2F11%2F2017&
		departureTime=09%3A44
		&transportType=TRANSIT
		&routeType=fastest&numOfItn=3
*/	

	public ArrayList<TripAlternative> getViaggiaAlternatives(String from, String to, String time) {
		

		
		//call the Viaggia Trento service
		String url = "https://dev.smartcommunitylab.it/smart-planner2/trentino/rest/plan?from="+from+"&to="+to+"&date=02/11/2017&departureTime=09%3A44&transportType=TRANSIT"+"&routeType=fastest&numOfItn=3";
	    System.out.println(url);
		ArrayList<TripAlternative> alternatives = new ArrayList<TripAlternative>();
		/*
       <TripAlternative> alternatives = new ArrayList<TripAlternative>();
		 String result = callURL("http://free.rome2rio.com/api/1.4/json/Search?key=Yt1V3vTI&oName="+partenza+"&dName="+destinazione);
		if(result.equalsIgnoreCase("erroreAPI"))
		{
			 return alternatives;
		}
		else{
		 //System.out.println(result);
			JSONObject jsonObj = new JSONObject(result);
			JSONArray routes =  new JSONArray();
			routes = jsonObj.getJSONArray("routes");
			for(int i=0;i<routes.length();i++)
			{
				
				System.out.println("Soluzione "+i+":");
				JSONObject route = (JSONObject) routes.get(i);
				System.out.println(route);
				// transportation mean
				String mean = route.getString("name");
				
				//PRICES
				JSONArray prices=  new JSONArray();
				prices = route.getJSONArray("indicativePrices");
				JSONObject price = (JSONObject) prices.get(0);
				//System.out.println(price);
				
				//price
				Long priceInd = price.getLong("price");
				
				
				
				//distance
				Long distance = route.getLong("distance");
				
				//duration
				Long duration = route.getLong("totalDuration");
				
				TripAlternative alternative = new TripAlternative(mean,priceInd,distance,duration);
				alternatives.add(alternative);
			
			}
		}
		*/
		 return alternatives;
	}

	// returns the result of the API call as string
	public static String callURL(String myURL) {
		System.out.println(myURL);
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
				//throw {new RuntimeException("Exception while calling URL:"+ myURL, e);
				return "erroreAPI";
				
			} 
	 
			return sb.toString();
		}
	
}
