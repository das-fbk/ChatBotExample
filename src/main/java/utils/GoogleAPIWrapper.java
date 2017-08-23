package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleAPIWrapper {
	
	
	public String getGoogleAutocomplete(String city) {
		
		 String alternatives = new String();
		 String result = callURL("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyBnLrMivSthmUmUipPfk5sidv7f0QvvDjg&input="+city+"&components=country:it&language=en");
		 
		if(result.equalsIgnoreCase("erroreAPI")){
			 return alternatives;
		}else{
		 
			JSONObject jsonObj = new JSONObject(result);
			JSONArray predictions =  new JSONArray();
			predictions = jsonObj.getJSONArray("predictions");
			
			for(int i = 0;i < predictions.length(); i++){
				
				JSONObject numbers = (JSONObject) predictions.get(i);
				
				JSONArray terms =  new JSONArray();
				terms = numbers.getJSONArray("terms");
				
				JSONObject value = (JSONObject) terms.get(1);
				
				if(value.getString("value").equals("Province of Trento") && !alternatives.contains(numbers.getString("description"))) {
					alternatives = numbers.getString("description");
				}
			}
		}
		 return alternatives;
	}	
	
	public String getCoordinates(String address){
		
		String completedFrom = address.replace(", ", "+");
		completedFrom = completedFrom.replace(" ", "+");
		
		String URL = "https://maps.googleapis.com/maps/api/geocode/json?address="+completedFrom+"&key=AIzaSyBnLrMivSthmUmUipPfk5sidv7f0QvvDjg";
	    System.out.println(URL);
		String result = callURL(URL);
		String latlong = "";
		
		if(result.equalsIgnoreCase("erroreAPI")){
			 return latlong;
		}else{
			JSONObject jsonObj = new JSONObject(result);
			JSONArray routes =  new JSONArray();
			routes = jsonObj.getJSONArray("results");
			
				
				JSONObject info = (JSONObject) routes.get(0);
				
				JSONObject geometry = info.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				
				Double lat = location.getDouble("lat");
			    Double lng = location.getDouble("lng");
			    
			    String latString = lat.toString();
			    String lngString = lng.toString();
			    
			    latlong = latString+"%2C"+lngString;
			
		
			    return latlong;
		}
	}
	
	public ArrayList <String>getCoordinatesParking(String address){
		
		ArrayList <String>latLong = new ArrayList<String>();
			
			String completedFrom = address.replace(", ", "+");
			completedFrom = completedFrom.replace(" - ", "+");
			completedFrom = completedFrom.replace(" ", "+");
			
			
			String URL = "https://maps.googleapis.com/maps/api/geocode/json?address="+completedFrom+"&key=AIzaSyBnLrMivSthmUmUipPfk5sidv7f0QvvDjg";
		    System.out.println(URL);
			String result = callURL(URL);
			
			if(result.equalsIgnoreCase("erroreAPI")){
				 return latLong;
			}else{
				JSONObject jsonObj = new JSONObject(result);
				JSONArray routes =  new JSONArray();
				routes = jsonObj.getJSONArray("results");
				
					if(routes.length() > 0){
						JSONObject info = (JSONObject) routes.get(0);
						
						JSONObject geometry = info.getJSONObject("geometry");
						JSONObject location = geometry.getJSONObject("location");
						
						Double lat = location.getDouble("lat");
					    Double lng = location.getDouble("lng");
					    
					    String latString = lat.toString();
					    String lngString = lng.toString();
					
					    latLong.add(latString);
					    latLong.add(lngString);
					}
					
				    
				    return latLong;
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
