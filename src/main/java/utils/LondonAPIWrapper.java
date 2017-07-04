package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

public class LondonAPIWrapper {
	
	public ArrayList<TripAlternativeLondon> getLondonAlternatives(String partenza, String destinazione) {
		
		 ArrayList<TripAlternativeLondon> alternatives = new ArrayList<TripAlternativeLondon>();
		 ArrayList<String> help = new ArrayList<String>();
		 int t = 0;
		 String result = callURL("https://api.tfl.gov.uk/Journey/JourneyResults/"+partenza+"/to/"+destinazione+"?nationalSearch=false");
		if(result.equalsIgnoreCase("erroreAPI"))
		{
			 return alternatives;
		}
		else{
			JSONObject jsonObj = new JSONObject(result);
			JSONArray journeys =  new JSONArray();
			journeys = jsonObj.getJSONArray("journeys");
			
			for(int i = 0;i < journeys.length(); i++){
				
				JSONObject route = (JSONObject) journeys.get(i);
				JSONArray legs =  new JSONArray();
				legs = route.getJSONArray("legs");
				
				int changes = legs.length();
				int duration = route.getInt("duration");
				
				String name = (t+1)+". ";
				
				for(int j = 0;j < legs.length(); j++){
					
					JSONObject leg = (JSONObject) legs.get(j);
					JSONObject mode = (JSONObject) leg.get("mode");
					if(j==0){
						name += mode.getString("name");
					}else{
						name +=","+mode.getString("name");
					}
					
				}
				
				if(i == 0){
					help.add(name);
					t++;
					TripAlternativeLondon alternative = new TripAlternativeLondon(name, duration, changes);
					alternatives.add(alternative);
				}else if(!help.contains(name)){
					help.add(name);
					t++;
					TripAlternativeLondon alternative = new TripAlternativeLondon(name, duration, changes);
					alternatives.add(alternative);
				}
			}
		}
		 return alternatives;
	}

	
public ArrayList<TravelsLondonAfterChoose> getLondonAfterChoose(String all, String partenza, String destinazione) {
		
		ArrayList<TravelsLondonAfterChoose> alternatives = new ArrayList<TravelsLondonAfterChoose>();
		String result = callURL("https://api.tfl.gov.uk/Journey/JourneyResults/"+partenza+"/to/"+destinazione+"?nationalSearch=false");
		
		if(result.equalsIgnoreCase("erroreAPI"))
		{
			 return alternatives;
		}
		else{
			JSONObject jsonObj = new JSONObject(result);
			JSONArray journeys =  new JSONArray();
			journeys = jsonObj.getJSONArray("journeys");
			
			for(int i = 0;i < journeys.length(); i++){
				
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
					segments = ((JSONObject) journeys.get(i)).getJSONArray("legs");
					for(int y = 0; y < segments.length(); y++) {
						JSONObject seg = (JSONObject) segments.get(y);
						
						String depPlace = ((JSONObject) seg.get("departurePoint")).getString("commonName");
						String arrPlace = ((JSONObject) seg.get("arrivalPoint")).getString("commonName");
						Integer duration = seg.getInt("duration");
						String vehicle = ((JSONObject) seg.get("mode")).getString("name");
						
						JSONArray routeOptions =  new JSONArray();
						routeOptions = ((JSONArray) seg.getJSONArray("routeOptions"));
						
						Integer numberBus = 0;
						if(vehicle.equals("bus")){
							numberBus = ((JSONObject) routeOptions.get(0)).getInt("name");
						}else{
							numberBus = -1;
						}
						
						String direction;
						
						if(!vehicle.equals("walking")){
							JSONArray str =  new JSONArray();
							str = (JSONArray)((JSONObject) routeOptions.get(0)).get("directions");;
							direction = (String) str.get(0);
						}else{
							direction = "999";
						}
						
						
						for(int t = 0;t<help.size()-2;t++) {
							TravelsLondonAfterChoose alternative = new TravelsLondonAfterChoose(depPlace, arrPlace, duration, vehicle,direction, numberBus, ordinal(y+1));
							if(help.get(t).equals(vehicle) && help.get(t+1).equals(depPlace) && help.get(t+2).equals(arrPlace)) {
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
