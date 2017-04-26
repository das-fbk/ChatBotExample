package it.das.travelassistant.telegram;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class GetUrlMain {
 
	public static void main(String[] args) {
	//	System.out.println("\nOutput: \n" + callURL("http://free.rome2rio.com/api/1.4/json/Search?key=Yt1V3vTI&oName=Rome&dName=Tourin"));
		String result = callURL("http://free.rome2rio.com/api/1.4/json/Search?key=Yt1V3vTI&oName=Trento&dName=Rovereto");
		//System.out.println(result);
		JSONObject jsonObj = new JSONObject(result);
		JSONArray routes =  new JSONArray();
		routes = jsonObj.getJSONArray("routes");
		for(int i=0;i<routes.length();i++)
		{
			
			System.out.println("Soluzione "+i+":");
			JSONObject route = (JSONObject) routes.get(i);
			//System.out.println(route);
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
			
			System.out.println(mean+' '+priceInd+' '+distance+' '+duration);
		

		}
    
      //  System.out.println(routes);

     //   String first = jsonObj.getJSONObject("arr").getString("a");
     //   System.out.println(first);

	}
 
	public static String callURL(String myURL) {
	//	System.out.println("Requested URL:" + myURL);
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
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		return sb.toString();
	}
}

