package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParkingAPIWrapper {
	
		public ArrayList<ParkingTrentoRovereto> getParkingAlternatives(String comune) {
		
		 ArrayList<ParkingTrentoRovereto> alternatives = new ArrayList<ParkingTrentoRovereto>();
		 
		 String result = callURL("https://os.smartcommunitylab.it/core.mobility/getparkingsbyagency/"+comune);
		 
		if(result.equalsIgnoreCase("erroreAPI")){
			 return alternatives;
		}else{
			JSONArray jsonObj =  new JSONArray(result);
			
			for(int i = 0;i < jsonObj.length(); i++){
				
				JSONObject leg = (JSONObject) jsonObj.get(i);
				
				
				String name = (String) leg.getString("name");
				String description = (String) leg.getString("description");
				int total = leg.getInt("slotsTotal");
				int available = leg.getInt("slotsAvailable");
				Boolean monitored = leg.getBoolean("monitored");
				
				if(monitored == false){
					alternatives.add(new ParkingTrentoRovereto(name, description, total, 0, monitored));
				}else{
					alternatives.add(new ParkingTrentoRovereto(name, description, total, available, monitored));
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
