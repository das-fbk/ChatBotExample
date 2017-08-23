package it.das.travelassistant.telegram.updateshandlers.messagging;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BIKEVIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CARVIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BUSVIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TRAINVIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.WALKVIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DATEHOUR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEATS;
import utils.TravelRome2Rio;
import utils.TravelBlaBlaCar;
import utils.CityBike;
import utils.TravelViaggiaTrento;
import utils.TravelsRomeToRioAfterChoose;
import utils.TravelsLondonAfterChoose;
import utils.ParkingTrentoRovereto;
import utils.TripAlternativeLondon;
import utils.TravelsViaggiaTrentoAfterChoose;
import utils.TravelsFlixbus_EmtMalaga;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by antbucc
 */
public class Texts {


    public static String textStart(Language language) {
    	return getMessage("welcome", language.locale());
    }

    public static String textError(Language language) {
    	return getMessage("error", language.locale());
    }

    public static String textStartHelp(Language language) {
        return textStart(language);
    }

    
    public static String textCalculateTrip(Language language) {
    	return getMessage("calculateTrip", language.locale());
    }
    
    public static String textChooseViaggiaTrentoStart(Language language) {
    	return getMessage("chooseviaggiatrentostart", language.locale());
    }
   
    public static String textChooseViaggiaTrentoDestination(Language language) {
    	return getMessage("chooseviaggiatrentoarrive", language.locale());
    }
    
    public static String textStartFrom(Language language) {
    	return getMessage("startFrom", language.locale());
    }
    public static String  textStartDestination(Language language) {
    	return getMessage("startDestination", language.locale());
    }
    public static String  textChooseRomeBla(Language language) {
    	return getMessage("chooseBlablacarRometoRio", language.locale());
    }
    
    public static String  textCityBike(Language language, ArrayList<CityBike> citybike, String partenza) {
		String result = "";
		result+="\ud83d\udeb2 Bike sharing in *"+ partenza+"*\n";
		result+="       \ud83d\udd35 = bikes available\n";
		
    	for(int i = 0;i<citybike.size();i++){
    		result+="\n";
    		if(citybike.get(i).getName().equals(citybike.get(i).getStreet())){
    			result+="     *"+(i+1)+".* "+citybike.get(i).getName()+"\n";
    		}else{
    			result+="     *"+(i+1)+".* "+citybike.get(i).getName()+", "+citybike.get(i).getStreet()+"\n";
    		}
    		
    		result+="            *Tot.* "+(citybike.get(i).getEmpty()+citybike.get(i).getFree())+" \ud83d\udeb2\n";
    		result+="            \ud83d\udd35 "+citybike.get(i).getFree()+" \ud83d\udeb2\n";
    		  
    		    
    	}
    	return result;
    }
    
    public static String  textParking(Language language, ArrayList<ParkingTrentoRovereto> park, String partenza) {
		String result = "";
		result+="*P* Parking in *"+ partenza+"*\n";
		result+="       \ud83d\udd35 = parkings available\n";
		
    	for(int i = 0;i<park.size();i++){
    		result+="\n";
    		if(park.get(i).getName().equals(park.get(i).getDescription())){
    			result+="     *"+(i+1)+".* "+park.get(i).getName()+"\n";
    		}else{
    			result+="     *"+(i+1)+".* "+park.get(i).getName()+", "+park.get(i).getDescription()+"\n";
    		}
    		result+="            *Tot.* "+park.get(i).getTotal()+" *P*\n";
    		if(park.get(i).getMonitored() == false){
    			result+="            \ud83d\udeab This information is not available\n";
    		}else{
    			result+="            \ud83d\udd35 "+park.get(i).getAvailable()+" *P*\n";
    		}
    		
    		  
    		    
    	}
    	return result;
    }
    
    public static String  textParkingLeg (Language language, ParkingTrentoRovereto park, int counter, String partenza) {
		String result = "";
		result+="*P* Parking in *"+ partenza+"*\n";
		result+="       \ud83d\udd35 = parkings available\n";
		result+="\n";
    	if(park.getName().equals(park.getDescription())){
    		result+="     *"+(counter+1)+".* "+park.getName()+"\n";
   		}else{
   			result+="     *"+(counter+1)+".* "+park.getName()+", "+park.getDescription()+"\n";
    	}
   		result+="            *Tot.* "+park.getTotal()+" *P*\n";
    	if(park.getMonitored() == false){
   			result+="            \ud83d\udeab This information is not available\n";
    	}else{
    		result+="            \ud83d\udd35 "+park.getAvailable()+" *P*\n";
   		}
    		
    	return result;
    }
    
    public static String  textViaggiTrentoAfterChoose(Language language, ArrayList<TravelsViaggiaTrentoAfterChoose> travels) {
	    	String result = "";
	    	String help = "99";
	    	for(int i = 0; i<travels.size(); i++) {
	    		if(travels.get(i).getBusNumber().equals("999") && travels.get(i).getHours().equals("999")) {
	    			result += "*Walk*\n";
	    			result+="    From: *"+travels.get(i).getFrom()+"*\n";
	    			result+="    To: *"+travels.get(i).getTo()+"*\n\n";
	    		}else{
	    			if(i == 0) {
		    			result += "*"+travels.get(i).getBusNumber()+"* bus route\n";
		    			result+="    From: *"+travels.get(i).getFrom()+"*\n";
		    			result+="    To: *"+travels.get(i).getTo()+"*\n\n";
		    			result+="Timetable";
		    		}
		    		if(!travels.get(i).getHours().substring(0, 2).equals(help)) {
		    			help = travels.get(i).getHours().substring(0, 2);
		    			result+="\n*"+help+"*    "+travels.get(i).getHours().substring(3, 5)+"'";
		    		}else{
		    			result+="  "+travels.get(i).getHours().substring(3,5)+"'";
		    		}
	    		}
	    		
	    	}
	    	return result;
    }
    
    public static String  textRome2RioAfterChoose(Language language, TravelsRomeToRioAfterChoose travels) {
    	String result = 
    	"*"+travels.getPosition()+" LEG*\n"+
    	"*From* "+travels.getStart()+"\n"+
    	"*To* "+travels.getArrive()+"\n";
    	String durationString = "";
    	int rest = travels.getDuration().intValue() % 60;
		int hour = travels.getDuration().intValue() / 60;
		if(rest<10) {
			durationString = hour+".0"+rest+" h";
		}else {
			durationString = hour+"."+rest+" h";
		}
		result+="\u23F3 "+durationString+"\n"+
		"\u21e5 "+travels.getDistance()+" Km"+"\n";
		if(travels.getPrice() != -1) {
			result +="\uD83D\uDCB0 "+travels.getPrice()+" \u20ac"+"\n";
		}
	    result +=travels.getVehicle();
		if(!travels.getAgency().equals("999")) {
			result+=" - "+travels.getAgency();
		}
    	return result;
    }
    
    public static String  textLondonAfterChoose(Language language, TravelsLondonAfterChoose travels) {
    	String result = 
    	"*"+travels.getPosition()+" LEG*\n"+
    	"*From* "+travels.getStart()+"\n"+
    	"*To* "+travels.getArrive()+"\n";
    	String durationString = "";
    	int rest = travels.getDuration().intValue() % 60;
		int hour = travels.getDuration().intValue() / 60;
		if(rest<10) {
			durationString = hour+".0"+rest+" h";
		}else {
			durationString = hour+"."+rest+" h";
		}
		result+="\u23F3 "+durationString+"\n";
		if(travels.getNumberBus() != -1){
			result +=travels.getVehicle() + "  *" + travels.getNumberBus()+"*";
		}else{
			result +=travels.getVehicle();
		}
		if(!travels.getDirection().equals("999")){
			result +="\n*Direction:* " + travels.getDirection();
		}
	    
    	return result;
    }
    
    public static String  textRome2RioArrive(Language language) {
    	return getMessage("rome2rioArrive", language.locale());
    }
    
    
    public static String  textRome2RioResult(Language language, ArrayList <TravelRome2Rio> travels, String choose) {
    	String result = getMessage("rome2riodifferentway", language.locale())+"\n";
    	int rest = 0;
		int hour = 0;
		String durationString = "";
    	switch(choose) {
	    	case TIME:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			if(rest<10) {
		    				durationString = hour+".0"+rest+" h";
		    			}else {
		    				durationString = hour+"."+rest+" h";
		    			}
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+"*"+durationString+"*"+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        *DURATION*"+TIME+"        DISTANCE"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
	    	case CHANGES:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			if(rest<10) {
		    				durationString = hour+".0"+rest+" h";
		    			}else {
		    				durationString = hour+"."+rest+" h";
		    			}
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+durationString+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+"*"+travels.get(i).getNumber_changes()+"*"+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        DURATION"+TIME+"        DISTANCE"+DISTANCE+"        *CHANGES*"+CHANGES+"\n\n";
	    		break;
	    	case DISTANCE:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			if(rest<10) {
		    				durationString = hour+".0"+rest+" h";
		    			}else {
		    				durationString = hour+"."+rest+" h";
		    			}
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+durationString+
		        					"    "+"*"+travels.get(i).getDistance()+"*"+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        DURATION"+TIME+"        *DISTANCE*"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
	    	default:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			if(rest<10) {
		    				durationString = hour+".0"+rest+" h";
		    			}else {
		    				durationString = hour+"."+rest+" h";
		    			}
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+"*"+travels.get(i).getCost() +"*"+
		        					"    "+durationString+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     *PRICE*"+PRICE+"        DURATION"+TIME+"        DISTANCE"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
    	}
    	
    		
    		result+=getMessage("rome2rioresult", language.locale());
    	return result;
    }
  
    public static String  textLondonResult(Language language, ArrayList <TripAlternativeLondon> travels, String choose) {
    	String result = getMessage("rome2riodifferentway", language.locale())+"\n";
    	int rest = 0;
		int hour = 0;
		String durationString = "";
    	switch(choose) {
	    	
	    	case CHANGES:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration().toString());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			if(rest<10) {
		    				durationString = hour+".0"+rest+" h";
		    			}else {
		    				durationString = hour+"."+rest+" h";
		    			}
		    			String shangesString = travels.get(i).getNumber_changes().toString();
		    			if(travels.get(i).getNumber_changes() == 1) {
		    				shangesString+=" change";
		    			}else {
		    				shangesString+=" changes";
		    			}
		        		result += "*"+travels.get(i).getMean().substring(0, (travels.get(i).getMean().indexOf("."))+1)+"*"+
		        					"      "+durationString+
		        					"    "+"*"+shangesString+"*"+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     DURATION"+TIME+"        *CHANGES*"+CHANGES+"\n\n";
	    		break;
	    	default:
	    		for(int i = 0;i<travels.size();i++) {
	    			Float help = Float.parseFloat(travels.get(i).getDuration().toString());
	    			rest = help.intValue() % 60;
	    			hour = help.intValue() / 60;
	    			if(rest<10) {
	    				durationString = hour+".0"+rest+" h";
	    			}else {
	    				durationString = hour+"."+rest+" h";
	    			}
	    			String shangesString = travels.get(i).getNumber_changes().toString();
	    			if(travels.get(i).getNumber_changes() == 1) {
	    				shangesString+=" change";
	    			}else {
	    				shangesString+=" changes";
	    			}
	        		result += "*"+travels.get(i).getMean().substring(0, (travels.get(i).getMean().indexOf("."))+1)+"*"+
	        					"      *"+durationString+"*"+
	        					"    "+shangesString+"\n\n";
	        	}
	    		result+=getMessage("rome2riosortby", language.locale())+"\n";
	    		result+="     *DURATION*"+TIME+"        CHANGES"+CHANGES+"\n\n";
    		break;
    	}
    	
    		
    		result+=getMessage("rome2rioresult", language.locale());
    	return result;
    }
    
    public static String  textFlixbusResult(Language language, ArrayList <TravelsFlixbus_EmtMalaga> travels) {
    	String result = getMessage("rome2riodifferentway", language.locale())+"\n\n";
    	for(int i = 0;i<travels.size();i++){
    		result+="*"+travels.get(i).getPosition()+".*\n";
    		result+="     *From:* "+travels.get(i).getFrom()+"\n";
    		result+="     *To:* "+travels.get(i).getTo()+"\n";
    		result+="     *Bus number:* "+travels.get(i).getRoute()+"\n";
    		result+="     *Bus name displayed:* "+travels.get(i).getHeadsign()+"\n";
    		result+="     *Departure time:* "+travels.get(i).getDeparture_time()+"\n";
    		result+="     *From stop name:* "+travels.get(i).getDeparture_stop_name()+"\n";
    		result+="     *To stop name:* "+travels.get(i).getArrival_stop_name()+"\n";
    		result+="     *Arrival time:* "+travels.get(i).getArrival_time()+"\n\n";
    	}
    	return result;
    }
    
    public static String  textEmtMalagaResult(Language language, ArrayList <TravelsFlixbus_EmtMalaga> travels) {
    	String result = getMessage("rome2riodifferentway", language.locale())+"\n\n";
    	for(int i = 0;i<travels.size();i++){
    		result+="*"+travels.get(i).getPosition()+".*\n";
    		result+="     *From:* "+travels.get(i).getFrom()+"\n";
    		result+="     *To:* "+travels.get(i).getTo()+"\n";
    		result+="     *Bus number:* "+travels.get(i).getRoute()+"\n";
    		result+="     *Bus name displayed:* "+travels.get(i).getHeadsign()+"\n";
    		result+="     *Departure time:* "+travels.get(i).getDeparture_time()+"\n";
    		result+="     *From stop name:* "+travels.get(i).getDeparture_stop_name()+"\n";
    		result+="     *To stop name:* "+travels.get(i).getArrival_stop_name()+"\n";
    		result+="     *Arrival time:* "+travels.get(i).getArrival_time()+"\n\n";
    	}
    	return result;
    }
    
    public static String  textBlaBlaCarResult(Language language, ArrayList <TravelBlaBlaCar> travels, String choose) {
    	String result = getMessage("blablacarbestway", language.locale())+"\n";
    	result += "        "+travels.get(0).getPerfect_price()+"    "+travels.get(0).getPerfect_duration()+"    "+travels.get(0).getDistance()+"\n";
    	result += "        "+ "\ud83d\udd35 "+getMessage("blablacargoodprice", language.locale())+"\n";
    	result += "        "+ "\ud83d\udd36 "+getMessage("blablacarokprice", language.locale())+"\n";
    	result += "        "+ "\ud83d\udd34 "+getMessage("blablacarbadprice", language.locale())+"\n\n";
    	result += getMessage("rome2riodifferentway", language.locale())+"\n";
    	
    	switch(choose) {
	    	case PRICE:
		    		for(int i = 0;i<travels.size();i++) {
		        		result += travels.get(i).getMean().substring(0,4)+travels.get(i).getDateHour()+
		        						"     "+"*"+travels.get(i).getPrice()+"*"+
		        						"     "+travels.get(i).getSeats_left()+"\n"+
		        						"          "+travels.get(i).getCar_model()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     DATE&TIME"+DATEHOUR+"        *PRICE*"+PRICE+"        SEATS"+SEATS+"\n\n";
	    		break;
	    	case SEATS:
		    		for(int i = 0;i<travels.size();i++) {
		        		result += travels.get(i).getMean().substring(0,4)+travels.get(i).getDateHour()+
		        						"     "+travels.get(i).getPrice()+
		        						"     "+"*"+travels.get(i).getSeats_left()+"*"+"\n"+
		        						"          "+travels.get(i).getCar_model()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     DATE&TIME"+DATEHOUR+"        PRICE"+PRICE+"        *SEATS*"+SEATS+"\n\n";
	    		break;
	    	default:
		    		for(int i = 0;i<travels.size();i++) {
		        		result += travels.get(i).getMean().substring(0,4)+"*"+travels.get(i).getDateHour()+"*"+
		        						"     "+travels.get(i).getPrice()+
		        						"     "+travels.get(i).getSeats_left()+"\n"+
		        						"          "+travels.get(i).getCar_model()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     *DATE&TIME*"+DATEHOUR+"        PRICE"+PRICE+"        SEATS"+SEATS+"\n\n";
	    		break;
    	}
    	
    	result+=getMessage("rome2rioresult", language.locale());
    	
    	return result;
    }


    private static String getMessage(String msg, Locale locale, String ... params) {
    	ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", locale);
   
    	MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
   
        formatter.applyPattern(bundle.getString(msg));
        return formatter.format(params);
   
    }
    
    public static String  textViaggiaTrentoTrip(Language language, ArrayList <TravelViaggiaTrento> travels) {
    	String result = getMessage("rome2riodifferentway", language.locale())+"\n";
    	for(int i = 0;i<travels.size();i++) {
    		int rest = Integer.parseInt(travels.get(i).getDuration()) % 60;
    		int hour = Integer.parseInt(travels.get(i).getDuration()) / 60;
    		if(rest<10) {
    			result += "\n*"+(i+1)+". "+"\u23f1"+hour+".0"+rest+"h*\n";
    		}else {
    			result += "\n*"+(i+1)+". "+"\u23f1"+hour+"."+rest+"h*\n";
    		}
    		
    		for(int j =0;j<travels.get(i).getRoutes().size();j++) {
    			System.out.println("============ "+travels.get(i).getSteps().get(j));
    			if(travels.get(i).getSteps().get(j).equals("BUS")) {
    				result += "    "+BUSVIAGGIATRENTO+travels.get(i).getSteps().get(j)+" "+travels.get(i).getRoutes().get(j)+"\n";
				}else if(travels.get(i).getSteps().get(j).equals("TRAIN")) {
					result += "    "+TRAINVIAGGIATRENTO+travels.get(i).getSteps().get(j)+" "+travels.get(i).getRoutes().get(j)+"\n";
				}else if(travels.get(i).getSteps().get(j).equals("BICYCLE")){
					result += "    "+BIKEVIAGGIATRENTO+travels.get(i).getSteps().get(j)+" "+travels.get(i).getRoutes().get(j)+"\n";
				}else if(travels.get(i).getSteps().get(j).equals("WALK")){
					result += "    "+WALKVIAGGIATRENTO+travels.get(i).getSteps().get(j)+" "+travels.get(i).getRoutes().get(j)+"\n";
				}else if(travels.get(i).getSteps().get(j).equals("CAR")){
					result += "    "+CARVIAGGIATRENTO+travels.get(i).getSteps().get(j)+" "+travels.get(i).getRoutes().get(j)+"\n";
				}
    			
    		}
    		
    	}
    	result+="\n";
    	result+=getMessage("rome2rioresult", language.locale());
    	return result;
    }
    
    public static String  textViaggiaTrentoYesNo(Language language) {
    	String result ="*Do you wanna personalize your trip?*\n";
    	
    	return result;
    }
    
    public static String  textViaggiaTrentoRouteType(Language language) {
    	String result ="*Choose one of the following RouteType alternatives*\n";
    	
    	return result;
    }
    public static String  textViaggiaTrentoTransportType(Language language) {
    	String result ="*Legend*\n\n";
    	result+="*TRANSIT* = Public transport\n";
    	result+="*SHAREDBIKE* = Bicycle with a constraint to move between bike sharing points\n";
    	result+="*SHAREDBIKE_WITHOUT_STATION* = Shared bicycle with a constraint to move from a bike sharing point\n";
    	result+="*CARWITHPARKING* = Private car with a constraint to move to a parking close to destination\n";
    	result+="*SHAREDCAR* = Shared car with a constraint to move between car sharing points\n";
    	result+="*SHAREDCAR_WITHOUT_STATION* = Shared car with a constraint to move from a car sharing point\n";
    	result+="*BUS* = Public bus\n";
    	result+="*TRAIN* = Trains\n";
    	result+="*WALK* = Pedestrian walk\n\n";
    	result+="*Choose one of the following TransportType alternatives*\n";
    	
    	return result;
    }

    
}
