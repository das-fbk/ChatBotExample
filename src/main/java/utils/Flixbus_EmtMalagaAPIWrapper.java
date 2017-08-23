package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Flixbus_EmtMalagaAPIWrapper {
	
	
	public ArrayList <TravelsFlixbus_EmtMalaga> getFlixbusAlternatives(String from, String to) {
		
		 ArrayList <TravelsFlixbus_EmtMalaga> alternatives = new ArrayList <TravelsFlixbus_EmtMalaga>();

			 
			 JSONParser parser = new JSONParser();
			 String realRouteId = null;
			try {
				Object obj = parser.parse(new FileReader("/Users/michaeldolzani/Documents/tirocinio/ChatBotExample/flixbus.json"));
				org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
				
				org.json.simple.JSONArray flixbus = new org.json.simple.JSONArray();
				flixbus = (org.json.simple.JSONArray) jsonObject.get("results");
				
				for(int i = 0; i < flixbus.size(); i++){
					org.json.simple.JSONObject object = (org.json.simple.JSONObject) flixbus.get(i);
					
					alternatives.add(new TravelsFlixbus_EmtMalaga(i+1,
																	from,
																	to,
																	object.get("route").toString().substring(object.get("route").toString().indexOf(":")+1, object.get("route").toString().length()),
																	object.get("headsign").toString(),
																	object.get("departure_time").toString(),
																	object.get("departure_stop_name").toString(),
																	object.get("arrival_time").toString(),
																	object.get("arrival_stop_name").toString()));
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 return alternatives; 
	
	}
	
	public ArrayList <TravelsFlixbus_EmtMalaga> getEmtMalagaAlternatives(String from, String to) {
		
		 ArrayList <TravelsFlixbus_EmtMalaga> alternatives = new ArrayList <TravelsFlixbus_EmtMalaga>();

			 
			 JSONParser parser = new JSONParser();
			 String realRouteId = null;
			try {
				Object obj = parser.parse(new FileReader("/Users/michaeldolzani/Documents/tirocinio/ChatBotExample/emtMalaga.json"));
				org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
				
				org.json.simple.JSONArray flixbus = new org.json.simple.JSONArray();
				flixbus = (org.json.simple.JSONArray) jsonObject.get("results");
				
				for(int i = 0; i < flixbus.size(); i++){
					org.json.simple.JSONObject object = (org.json.simple.JSONObject) flixbus.get(i);
					
					alternatives.add(new TravelsFlixbus_EmtMalaga(i+1,
																	from,
																	to,
																	object.get("route").toString().substring(object.get("route").toString().indexOf(":")+1, object.get("route").toString().length()),
																	object.get("headsign").toString(),
																	object.get("departure_time").toString(),
																	object.get("departure_stop_name").toString(),
																	object.get("arrival_time").toString(),
																	object.get("arrival_stop_name").toString()));
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 return alternatives; 
	
	}
}
