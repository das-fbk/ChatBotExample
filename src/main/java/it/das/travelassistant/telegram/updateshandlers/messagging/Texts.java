package it.das.travelassistant.telegram.updateshandlers.messagging;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.AUTOBUSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BIKESHARINGSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PARKINGSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TRAINSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import it.sayservice.platform.smartplanner.data.message.otpbeans.Parking;

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

    // endregion utilities

    // region commands

    public static String textLanguage(Language language) {
    	return getMessage("changelang", language.locale());
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

    public static String textStartMain(Language language) {
    	return getMessage("startmain", language.locale());
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
    public static String textStartRome2Rio(Language language) {
    	return getMessage("startrome2rio", language.locale());
    }
    public static String  textStartRome2RioDestination(Language language) {
    	return getMessage("startrome2rioDestination", language.locale());
    }
    public static String  textRome2RioCalcola(Language language) {
    	return getMessage("startrome2rioCacola", language.locale());
    }
    public static String  textRome2RioResult(Language language, ArrayList <Travel> travels, String choose) {
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
    public static String  textViaggiaTrentoResult(Language language) {
    	return getMessage("viaggiatrentoresult", language.locale());
    }
    public static String  textViaggiaTrentoCalcola(Language language) {
    	return getMessage("viaggiaTrentoCalcola", language.locale());
    }
    public static String  textViaggiaTimeDeparture(Language language) {
    	return getMessage("viaggiatimedeparture", language.locale());
    }
    public static String  textTransportType(Language language) {
    	return getMessage("viaggiatransporttype", language.locale());
    }
    
    
    
    
   

    public static String textStartBikeSharings(Language language) {
    	return getMessage("startbikesharing", language.locale());
    }

    // endregion Menu.START

    // region Menu.LANGUAGE

    public static String textLanguageChange(Language language) {
    	return getMessage("langchanged", language.locale());
    }

    // endregion Menu.LANGUAGE

    // region Menu.AUTOBUS

    public static String textAutobusHelp(Language language) {
    	return getMessage("bushelp", language.locale());
    }

    public static String textAutobus(String autobusId, TimeTable timeTable, int index) {
        return "*" + AUTOBUSCOMMAND + " " + autobusId + "*\n" + textTimetable(timeTable, index);
    }

    // endregion Menu.AUTOBUS

    // region Menu.TRAINS

    public static String textTrainHelp(Language language) {
    	return getMessage("trainhelp", language.locale());
    }

    public static String textTrain(String trainId, TimeTable timeTable, int index) {
        return "*" + TRAINSCOMMAND + " " + trainId + "*\n" + textTimetable(timeTable, index);
    }


    // endregion Menu.TRAINS

    // region Menu.PARKINGS

    public static String textParkingsHelp(Language language) {
    	return getMessage("parkinghelp", language.locale());
    }

    public static String textParking(Parking parking, Language language) {
        return "*PARKING " + parking.getName() + "*\n" + parking.getDescription() + "\n" + textSlots(parking, language);
    }

    public static String textParkingsNear(List<Parking> parkings, Language language) {
        return textNear(parkings, "nonearparking", language);
    }

    // endregion Menu.PARKINGS

    // region Menu.BIKESHARINGS

    public static String textBikeSharingsHelp(Language language) {
    	return getMessage("bikesharinghelp", language.locale());
    }

    public static String textBikeSharings(Parking parking, Language language) {
        return "*Bike sharing " + parking.getName() + "*\n" + parking.getDescription() + "\n" + bikeSlots(parking, language);
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
