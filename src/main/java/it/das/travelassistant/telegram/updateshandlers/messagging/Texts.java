package it.das.travelassistant.telegram.updateshandlers.messagging;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import it.sayservice.platform.smartplanner.data.message.otpbeans.Parking;
import utils.TravelRome2Rio;
import utils.TravelBlaBlaCar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import eu.trentorise.smartcampus.mobilityservice.model.TaxiContact;
import eu.trentorise.smartcampus.mobilityservice.model.TimeTable;

/**
 * Created by antbucc
 */
public class Texts {

    // region utilities

    private static String textTimetable(TimeTable timeTable, int index) {
        String text = "";

        List<String> stops = timeTable.getStops();
        List<String> times = timeTable.getTimes().get(index);

        for (String time : times)
            if (!time.isEmpty())
                text += "`" + time + "` - " + stops.get(times.indexOf(time)) + "\n";

        return text;
    }

    private static String textSlots(Parking parking, Language language) {
    	if (parking.isMonitored() && parking.getSlotsAvailable() >= 0) {
        	return getMessage("slotsmonitored", language.locale(), ""+parking.getSlotsAvailable(), ""+parking.getSlotsTotal());
    	} else {
        	return getMessage("slots", language.locale(), ""+parking.getSlotsTotal());
    	}
    }

    private static String textNear(List<Parking> parkings, String notemplate, Language language) {
    	String text = getMessage("near", language.locale());
        if (parkings.isEmpty())
            text = getMessage(notemplate, language.locale());
        else
            for (Parking park : parkings)
                text += "\n" + park.getName() + " : " + park.getDescription();
        return text;
    }


    public static String textStart(Language language) {
    	return getMessage("welcome", language.locale());
    }

    public static String textError(Language language) {
    	return getMessage("error", language.locale());
    }

    // endregion commands

    // region Menu.START

    public static String textStartHelp(Language language) {
        return textStart(language);
    }

    
    public static String textCalculateTrip(Language language) {
    	return getMessage("calculateTrip", language.locale());
    }

    public static String textStartTaxi(List<TaxiContact> taxi) {
        String text;
        text = "*TAXI*";

        for (TaxiContact el : taxi) {
            text += "\n*" + el.getName() + "*";
            for (String st : el.getPhone())
                text += "\n" + st;
            for (String st : el.getSms())
                text += st.equals("") ? "" : "\n" + st;
        }

        return text;
    }

    public static String textStartAutobus(Language language) {
    	return getMessage("startbus", language.locale());
    }
   
   

    public static String textStartTrains(Language language) {
    	return getMessage("starttrain", language.locale());
    }

    public static String textStartParkings(Language language) {
    	return getMessage("startparking", language.locale());
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
		    			durationString = hour+"."+rest+" h";
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+"*"+durationString+"*"+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        *TIME*"+TIME+"        DISTANCE"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
	    	case CHANGES:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			durationString = hour+"."+rest+" h";
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+durationString+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+"*"+travels.get(i).getNumber_changes()+"*"+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        TIME"+TIME+"        DISTANCE"+DISTANCE+"        *CHANGES*"+CHANGES+"\n\n";
	    		break;
	    	case DISTANCE:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			durationString = hour+"."+rest+" h";
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+travels.get(i).getCost() +
		        					"    "+durationString+
		        					"    "+"*"+travels.get(i).getDistance()+"*"+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     PRICE"+PRICE+"        TIME"+TIME+"        *DISTANCE*"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
	    	default:
		    		for(int i = 0;i<travels.size();i++) {
		    			Float help = Float.parseFloat(travels.get(i).getDuration());
		    			rest = help.intValue() % 60;
		    			hour = help.intValue() / 60;
		    			durationString = hour+"."+rest+" h";
		        		result += travels.get(i).getMean()+"\n"+
		        					"        "+"*"+travels.get(i).getCost() +"*"+
		        					"    "+durationString+
		        					"    "+travels.get(i).getDistance()+
		        					"    "+travels.get(i).getNumber_changes()+"\n\n";
		        	}
		    		result+=getMessage("rome2riosortby", language.locale())+"\n";
		    		result+="     *PRICE*"+PRICE+"        TIME"+TIME+"        DISTANCE"+DISTANCE+"        CHANGES"+CHANGES+"\n\n";
	    		break;
    	}
    	
    		
    		result+=getMessage("rome2rioresult", language.locale());
    	return result;
    }
    
    
    public static String  textBlaBlaCarResult(Language language, ArrayList <TravelBlaBlaCar> travels, String choose) {
    	String result = "";
    	
    	return result;
    }
    
    public static String  textViaggiaTrentoResult(Language language) {
    	return getMessage("viaggiatrentoresult", language.locale());
    }

    
    public static String  textViaggiaTimeDeparture(Language language) {
    	return getMessage("viaggiatimedeparture", language.locale());
    }

    
   

    public static String textStartBikeSharings(Language language) {
    	return getMessage("startbikesharing", language.locale());
    }








    public static String textParkingsNear(List<Parking> parkings, Language language) {
        return textNear(parkings, "nonearparking", language);
    }


	private static String bikeSlots(Parking parking, Language language) {
    	return getMessage("bikeslots", language.locale(), ""+parking.getSlotsAvailable(), ""+parking.getExtra().get("bikes"));
	}

	public static String textBikeSharingsNear(List<Parking> parkings, Language language) {
        return textNear(parkings, "nonearbs", language);

    }

    // endregion Menu.BIKESHARINGS

    private static String getMessage(String msg, Locale locale, String ... params) {
    	ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", locale);
   
    	MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
   
        formatter.applyPattern(bundle.getString(msg));
        return formatter.format(params);
   
    }
    
}
