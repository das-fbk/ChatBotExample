package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class CityBikeAPIWrapper {
	
	public ArrayList<CityBike> getCityBikeAlternatives(String partenza) {
		
		 ArrayList<CityBike> alternatives = new ArrayList<CityBike>();
		 
		 String result = callURL("http://api.citybik.es/v2/networks");
		 
		if(result.equalsIgnoreCase("erroreAPI")){
			 return alternatives;
		}else{
		 
			JSONObject jsonObj = new JSONObject(result);
			JSONArray networks =  new JSONArray();
			networks = jsonObj.getJSONArray("networks");
			String help = "";
			for(int i = 0;i < networks.length(); i++){
				
				JSONObject leg = (JSONObject) networks.get(i);
				JSONObject location = (JSONObject) leg.get("location");
				String city = (String) location.getString("city");
				
				if(partenza.toLowerCase().equals(city.toLowerCase())){
					help = (String) leg.getString("href");
					break;
				}
				
			}
			
			if(!help.equals("")){
				 String results = callURL("http://api.citybik.es"+help);
				 if(results.equalsIgnoreCase("erroreAPI")){
					 return alternatives;
				}else{
					jsonObj = new JSONObject(results);
					JSONObject network =  (JSONObject) jsonObj.get("network");
					JSONArray stations =  new JSONArray();
					stations = network.getJSONArray("stations");
					
					for(int i = 0;i < stations.length(); i++){
						JSONObject leg = (JSONObject) stations.get(i);
						
						int empty = leg.getInt("empty_slots");
						int free = leg.getInt("free_bikes");
						String name = leg.getString("name");
						
						JSONObject extra = (JSONObject) leg.get("extra");
						
						String description = extra.getString("description");
						if(empty+free != 0){
							alternatives.add(new CityBike(empty,free, description, name));
						}
					}
					
				}
			}
			
		}
		 return alternatives;
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
