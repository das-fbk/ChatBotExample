package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.StringTokenizer;


public class Rome2RioAPIWrapper {

	public ArrayList<TripAlternativeRome2Rio> getRome2RioAlternatives(String partenza, String destinazione) {
		
		 ArrayList<TripAlternativeRome2Rio> alternatives = new ArrayList<TripAlternativeRome2Rio>();
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
			//System.out.println("routes.length(): "+routes.length());
			for(int i = 0;i < routes.length(); i++){
				
				System.out.println("Soluzione "+i+":");
				JSONObject route = (JSONObject) routes.get(i);
				System.out.println(route);
				
				// transportation mean
				String mean = route.getString("name");
				
				//changes 
				JSONArray changes =  new JSONArray();
				changes = route.getJSONArray("segments");
				int number_changes = -1;
				for(int j = 0;j < changes.length();j++) {
					number_changes++;
				}
				
				//PRICES
				JSONArray prices =  new JSONArray();
				prices = route.getJSONArray("indicativePrices");
				JSONObject price = (JSONObject) prices.get(0);
				
				//price
				Double priceInd = price.getDouble("price");
				
				//distance
				Double distance = route.getDouble("distance");
				
				//duration
				Double duration = route.getDouble("totalDuration");
				
				
				TripAlternativeRome2Rio alternative = new TripAlternativeRome2Rio(mean, priceInd.intValue(), duration, distance.intValue(), number_changes);
				alternatives.add(alternative);
			
			}
		}
		 return alternatives;
	}
	
	
	public ArrayList<TravelsRomeToRioAfterChoose> getRome2RioAfterChoose(String all, String partenza, String destinazione) {
		
		ArrayList<TravelsRomeToRioAfterChoose> alternatives = new ArrayList<TravelsRomeToRioAfterChoose>();
		String result = callURL("http://free.rome2rio.com/api/1.4/json/Search?key=Yt1V3vTI&oName="+partenza+"&dName="+destinazione);
		
		if(result.equalsIgnoreCase("erroreAPI"))
		{
			 return alternatives;
		}
		else{
			JSONObject jsonObj = new JSONObject(result);
			JSONArray routes =  new JSONArray();
			
			routes = jsonObj.getJSONArray("routes");

			for(int i = 0;i < routes.length(); i++){
				
				StringTokenizer stk = new StringTokenizer(all, "*");
				ArrayList <String> help = new ArrayList <String>();
				
		        while (stk.hasMoreTokens()) {
		        	String token = stk.nextToken();
		            StringTokenizer stk1 = new StringTokenizer(token, ";");
		            
		            while (stk1.hasMoreTokens()) {
		                String token1 = stk1.nextToken();
							help.add(token1);
		            }
		            
		        }
					
					JSONArray segments =  new JSONArray();
					segments = ((JSONObject) routes.get(i)).getJSONArray("segments");
					for(int y = 0; y < segments.length(); y++) {
						JSONObject seg = (JSONObject) segments.get(y);
						
						Integer vei = seg.getInt("vehicle");
						Integer dep = seg.getInt("depPlace");
						Integer arr = seg.getInt("arrPlace");
						
						JSONArray vehicles =  new JSONArray();
						vehicles = jsonObj.getJSONArray("vehicles");
						String vehicle1 = ((JSONObject) vehicles.get(vei)).getString("name");
						
						JSONArray places =  new JSONArray();
						places = jsonObj.getJSONArray("places");
						String depPlace1 = ((JSONObject) places.get(dep)).getString("shortName");
						String arrPlace1 = ((JSONObject) places.get(arr)).getString("shortName");
						
						String agency1="";
						JSONArray agencies =  new JSONArray();
						if(seg.has("agencies")) {
							agencies = seg.getJSONArray("agencies");
							Integer agg = ((JSONObject) agencies.get(0)).getInt("agency");
							JSONArray agge =  new JSONArray();
							agge = jsonObj.getJSONArray("agencies");
							agency1 = ((JSONObject) agge.get(agg)).getString("name");
						}else{
							agency1 = "999";
						}
						
						
						for(int t = 0;t<help.size()-3;t++) {
							Integer duration = seg.getInt("transitDuration");
							Double distance = seg.getDouble("distance");
							Integer priceInd = 0;
							if(seg.has("indicativePrices")) {
								JSONArray prices =  seg.getJSONArray("indicativePrices");
								JSONObject price = (JSONObject) prices.get(0);
								priceInd = price.getInt("price");
							}else{
								priceInd = -1;
							}
							
							TravelsRomeToRioAfterChoose alternative = new TravelsRomeToRioAfterChoose(depPlace1, arrPlace1, duration, distance.intValue(), priceInd, vehicle1, agency1, ordinal(y+1));
							if(help.get(t).equals(vehicle1) && help.get(t+1).equals(agency1) && help.get(t+2).equals(depPlace1) && help.get(t+3).equals(arrPlace1)) {
								alternatives.add(alternative);
							}
							
						}
						
					}
					if (alternatives.size() != segments.length()) {
						alternatives.clear();
					}else{
						break;
					}
				}
			
		}
		 return alternatives; 
	}
	
	public ArrayList<TravelsRomeToRioAfterChoose> getRome2Rio(String destinazione, String partenza, String results) {
		
		ArrayList<TravelsRomeToRioAfterChoose> alternatives = new ArrayList<TravelsRomeToRioAfterChoose>();
		String result = callURL("http://free.rome2rio.com/api/1.4/json/Search?key=Yt1V3vTI&oName="+partenza+"&dName="+destinazione);
		if(result.equalsIgnoreCase("erroreAPI"))
		{
			 return alternatives;
		}
		else{
			JSONObject jsonObj = new JSONObject(result);
			JSONArray routes =  new JSONArray();
			routes = jsonObj.getJSONArray("routes");
			for(int i = 0;i < routes.length(); i++){
				
				JSONObject route = (JSONObject) routes.get(i);
				if(route.getString("name").equals(results)) {
					JSONArray segments = route.getJSONArray("segments");
					for(int j = 0; j < segments.length(); j++){
						
						JSONObject segment = (JSONObject) segments.get(j);
						
						Integer duration = segment.getInt("transitDuration");
						Double distance = segment.getDouble("distance");
						
						JSONArray prices =  segment.getJSONArray("indicativePrices");
						JSONObject price = (JSONObject) prices.get(0);
						Integer priceInd = price.getInt("price");
						
						Integer vei = segment.getInt("vehicle");
						Integer dep = segment.getInt("depPlace");
						Integer arr = segment.getInt("arrPlace");
						
						JSONArray vehicles =  new JSONArray();
						vehicles = jsonObj.getJSONArray("vehicles");
						String vehicle = ((JSONObject) vehicles.get(vei)).getString("name");
						
						JSONArray places =  new JSONArray();
						places = jsonObj.getJSONArray("places");
						String depPlace = ((JSONObject) places.get(dep)).getString("shortName");
						String arrPlace = ((JSONObject) places.get(arr)).getString("shortName");
						
						String agency="";
						JSONArray agencies =  new JSONArray();
						if(segment.has("agencies")) {
							agencies = segment.getJSONArray("agencies");
							
							Integer agg = ((JSONObject) agencies.get(0)).getInt("agency");
							JSONArray agge =  new JSONArray();
							agge = jsonObj.getJSONArray("agencies");
							agency = ((JSONObject) agge.get(agg)).getString("name");
						}else{
							agency = "999";
						}
						
						TravelsRomeToRioAfterChoose alternative = new TravelsRomeToRioAfterChoose(depPlace, arrPlace, duration, distance.intValue(), priceInd, vehicle, agency, ordinal(j+1));
						alternatives.add(alternative);
					}
					
				}
				
			}
		}
		 return alternatives;
	}
	
	private static String ordinal (int i) {
	    String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (i % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return i + "th";
	    default:
	        return i + sufixes[i % 10];

	    }
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
