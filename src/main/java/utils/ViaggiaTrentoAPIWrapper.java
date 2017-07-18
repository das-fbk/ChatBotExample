package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ViaggiaTrentoAPIWrapper {

	
	public ArrayList <TravelViaggiaTrento> getViaggiaTrentoRoutes(String coordinatesFrom, String coordinatesTo, String routeType, String transportType) {
	
		 String dateHour = new SimpleDateFormat("MM/dd/yyyy HH:mm:mm").format(Calendar.getInstance().getTime());  
		 String date = dateHour.substring(0,2)+"%2F"+dateHour.substring(3,5)+"%2F"+ dateHour.substring(6,10);
		 String hour = dateHour.substring(11,13)+"%3A"+dateHour.substring(14,16);
		 ArrayList <TravelViaggiaTrento> alternatives = new ArrayList <TravelViaggiaTrento>();
		 
		 String result = callURL("https://dev.smartcommunitylab.it/smart-planner/trentino/rest/plan?from="+coordinatesFrom+"&to="+coordinatesTo+"&date="+date+"&departureTime="+hour+"&transportType="+transportType+"&routeType="+routeType+"&numOfItn=10");
		 
		if(result.equalsIgnoreCase("erroreAPI")){
			 return alternatives;
		}else{
		 
			JSONArray jsonArr = new JSONArray(result);
			ArrayList <String> help = new ArrayList <String>();
			
			for(int i = 0;i < jsonArr.length(); i++){
				JSONObject numbers = (JSONObject) jsonArr.get(i);
				JSONArray leg =  new JSONArray();
				leg = numbers.getJSONArray("leg");
				String hel = "";
				
				for(int j = 0;j < leg.length(); j++) {
					JSONObject legs = (JSONObject) leg.get(j);
					
					JSONObject transport = (JSONObject) legs.getJSONObject("transport");
					String type = transport.getString("type").toUpperCase();
					
					if(type.equals("BUS")) {
						String busNumber = transport.getString("routeShortName");
						hel+=busNumber;
					}else {
						hel+="999";
					}
				}
				
				if(!help.contains(hel)) {
					help.add(hel);
				}else {
					help.add("nothing");
				}
				
			}
			
			for(int i = 0;i < jsonArr.length(); i++){
				
				JSONObject numbers = (JSONObject) jsonArr.get(i);
				ArrayList <String> routes = new ArrayList <String>();
				ArrayList <String> steps = new ArrayList <String>();
				ArrayList <String> routeId =  new ArrayList <String>();
				ArrayList <String> agencyId =  new ArrayList <String>();
				
				JSONArray leg =  new JSONArray();
				leg = numbers.getJSONArray("leg");
				String complete = "";
				
				System.out.println("\n"+leg.length()+"\n");
				String hel = "";
				for(int j = 0;j < leg.length(); j++) {
					
					JSONObject legs = (JSONObject) leg.get(j);
					
					JSONObject transport = (JSONObject) legs.getJSONObject("transport");
					String type = transport.getString("type").toUpperCase();
					
					JSONObject to = (JSONObject) legs.getJSONObject("to");
					String nameTO = to.getString("name");
					
					JSONObject from = (JSONObject) legs.getJSONObject("from");
					String nameFrom = from.getString("name");
					
					if(type.equals("BUS")) {
						
						routeId.add(transport.getString("routeId"));
						agencyId.add(transport.getString("agencyId"));
						String busNumber = transport.getString("routeShortName");
						hel += busNumber;
						complete = "*"+busNumber+"*\n"+"          *FROM* "+nameFrom+"\n"+"          *TO* "+nameTO;
					}else{
						hel += "999";
						routeId.add("999");
						agencyId.add("999");
						complete = "\n"+"          *FROM* "+nameFrom+"\n"+"          *TO* "+nameTO;
					}
					
					routes.add(complete);
					steps.add(type);
				}
				
				if(help.contains(hel)) {
					if(i == help.indexOf(hel) && alternatives.size() < 4) {
						int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(numbers.getInt("duration"));
						alternatives.add(new TravelViaggiaTrento(""+minutes, steps, routes, routeId, agencyId));
					}
				}
				
			}
		}
		 return alternatives;
	}
	
	public ArrayList <TravelsViaggiaTrentoAfterChoose> getViaggiaTrentoAfterChoose(String agencyId, String routeId, String from, String to) {
		
		 ArrayList <TravelsViaggiaTrentoAfterChoose> alternatives = new ArrayList <TravelsViaggiaTrentoAfterChoose>();
		 if(!agencyId.equals("999") && !routeId.equals("999")) {
			 
			 JSONParser parser = new JSONParser();
			 String realRouteId = null;
			try {
				Object obj = parser.parse(new FileReader("/Users/michaeldolzani/Documents/tirocinio/das.travelassisstant/viaggiaTrento.json"));
				org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;

				org.json.simple.JSONObject prova = (org.json.simple.JSONObject) jsonObject.get(agencyId);
				realRouteId = (String) prova.get(routeId);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			 
			 String result = callURL("https://os.smartcommunitylab.it/core.mobility/timetable/"+agencyId+"/"+realRouteId);
			 
				if(result.equalsIgnoreCase("erroreAPI")){
					 return alternatives;
				}else{
					JSONObject jsonArr = new JSONObject(result);
					int helpFrom = 0;
					int helpTo = 0;
					
						JSONArray number = (JSONArray) jsonArr.getJSONArray("stopNames");
						for(int j = 0; j < number.length(); j++) {
							String numbers = (String) number.get(j);
							if(numbers.equals(from)) {
								helpFrom = j;
							}
							if(numbers.equals(to)) {
								helpTo = j;
							}
						}
						
						number = (JSONArray) jsonArr.getJSONArray("trips");
						for(int j = 0; j < number.length(); j++) {
							JSONObject trip = (JSONObject) number.get(j);
							JSONArray stopTimes = (JSONArray) trip.getJSONArray("stopTimes");
							if(!stopTimes.getString(helpFrom).equals("") && !stopTimes.getString(helpTo).equals("")) {
								String help;
								if(routeId.substring(0,1).equals("0")) {
									help = routeId.substring(1,2);
								}else {
									help = routeId.substring(0,2);
								}
								alternatives.add(new TravelsViaggiaTrentoAfterChoose(from, to, help,stopTimes.getString(helpFrom)));
							}
						}
				}
			 
		 }else {
			 alternatives.add(new TravelsViaggiaTrentoAfterChoose(from, to, "999","999"));
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
