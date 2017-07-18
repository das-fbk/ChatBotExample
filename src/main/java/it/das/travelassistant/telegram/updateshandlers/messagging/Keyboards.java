package it.das.travelassistant.telegram.updateshandlers.messagging;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CALCOLA;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ROME2RIO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BLABLACAR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.VIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.YES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BIKE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.LONDON;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DATEHOUR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NEXT;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEATS;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PARKING;

import utils.ViaggiaTrentoAPIWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import utils.TravelRome2Rio;
import utils.TravelsRomeToRioAfterChoose;
import utils.TravelViaggiaTrento;
import utils.TripAlternativeLondon;
import utils.TravelBlaBlaCar;
import utils.TripAlternativeRome2Rio;
import utils.TripAlternativeBlaBlaCar;
import java.text.DecimalFormat;

/**
 * Created by bucchiarone@fbk.eu
 */
public class Keyboards {

	// region utilities
	private static ArrayList<TravelRome2Rio> travelsRomeToRio;
	private static ArrayList<TravelBlaBlaCar> travelsBlaBlaCar;
	private static ArrayList<TravelViaggiaTrento> travelsViaggiaTrento;
	private static ArrayList<TravelsRomeToRioAfterChoose> travelsRomeToRioAfterChoose;

	private static ReplyKeyboardMarkup keyboard() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboad(false);

		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}


	private static KeyboardRow keyboardRowButton(String value) {
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(value);

		return keyboardRow;
	}

	
	
	public static ArrayList <TravelRome2Rio> getDifferentWayTravelRomeToRio() {
		return travelsRomeToRio;
	}
	
	public static ArrayList <TravelBlaBlaCar> getDifferentWayTravelBlaBlaCar() {
		return travelsBlaBlaCar;
	}
	
	public static ArrayList <TravelViaggiaTrento> getDifferentWayTravelViaggiaTrento() {
		return travelsViaggiaTrento;
	}
	
	
	private static ReplyKeyboardMarkup keyboardRome2RioResult(long chatId,
			ArrayList<TripAlternativeRome2Rio> alternatives, Menu menu, String filter) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(new KeyboardRow());
        keyboard.get(0).add(PRICE);
        keyboard.get(0).add(TIME);
        keyboard.get(0).add(DISTANCE);
        keyboard.get(0).add(CHANGES);
		
        travelsRomeToRio = new ArrayList<TravelRome2Rio>();
        
		for (int i = 0; i < alternatives.size(); i++) {
			String mean = alternatives.get(i).getMean();
			Double duration = alternatives.get(i).getDuration();
			Integer cost = alternatives.get(i).getPrice();
			Integer distance = alternatives.get(i).getDistance();
			Integer numberChanges = alternatives.get(i).getNumber_changes();
			
			String distanceString = distance.toString() + " Km";
			String costString = cost.toString() + " \u20ac";
			String shangesString = numberChanges.toString();
			if(numberChanges == 1) {
				shangesString+=" change";
			}else {
				shangesString+=" changes";
			}
			
			mean = setKeyboardJourneyOption(mean);
			
			travelsRomeToRio.add(new TravelRome2Rio(mean, duration.toString(), costString, distanceString, shangesString));
			
		}

		switch(filter) {
			case TIME:
					Collections.sort(travelsRomeToRio, TravelRome2Rio.timeComparator);
				break;
			case DISTANCE:
					Collections.sort(travelsRomeToRio, TravelRome2Rio.distanceComparator);
				break;
			case CHANGES:
					Collections.sort(travelsRomeToRio, TravelRome2Rio.changesComparator);
				break;
			default:
					Collections.sort(travelsRomeToRio, TravelRome2Rio.priceComparator);
				break;
		}

		for (int i = 0; i < travelsRomeToRio.size(); i++) {
			keyboard.add(keyboardRowButton(travelsRomeToRio.get(i).getMean()));
		}
		
		
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	private static ReplyKeyboardMarkup keyboardLondonResult(long chatId,
			ArrayList<TripAlternativeLondon> alternatives, Menu menu, String filter) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(new KeyboardRow());
        keyboard.get(0).add(TIME);
        keyboard.get(0).add(CHANGES);
        
		switch(filter) {
			case CHANGES:
					Collections.sort(alternatives, TripAlternativeLondon.changesComparator);
				break;
			default:
					Collections.sort(alternatives, TripAlternativeLondon.timeComparator);
				break;
		}

		for (int i = 0; i < alternatives.size(); i++) {
			String durationString = "";
	    	int rest = alternatives.get(i).getDuration().intValue() % 60;
			int hour = alternatives.get(i).getDuration().intValue() / 60;
			if(rest<10) {
				durationString = hour+".0"+rest+" h";
			}else {
				durationString = hour+"."+rest+" h";
			}
			String shangesString = alternatives.get(i).getNumber_changes().toString();
			if(alternatives.get(i).getNumber_changes() == 1) {
				shangesString+=" change";
			}else {
				shangesString+=" changes";
			}
			keyboard.add(keyboardRowButton(alternatives.get(i).getMean().substring(0, (alternatives.get(i).getMean().indexOf("."))+1)+"     "+durationString+" - "+shangesString));
		}
		
		
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	private static ReplyKeyboardMarkup keyboardViaggiaTrentoAfterChoose(long chatId, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(new KeyboardRow());
        keyboard.get(0).add(NEXT);
        
        replyKeyboardMarkup.setKeyboard(keyboard);
        
        Current.setMenu(chatId, menu);
	
	
	return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	
	private static ReplyKeyboardMarkup keyboardBlaBlaCarResult(long chatId,
			ArrayList<TripAlternativeBlaBlaCar> alternatives, Menu menu, String filter) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		keyboard.add(new KeyboardRow());
		keyboard.get(0).add(DATEHOUR);
        keyboard.get(0).add(PRICE);
        keyboard.get(0).add(SEATS);
        
		
        travelsBlaBlaCar = new ArrayList<TravelBlaBlaCar>();

        for (int i = 0; i < alternatives.size(); i++) {
        	String dateHour = alternatives.get(i).getDate() + " " + alternatives.get(i).getHour().substring(0, 5) + "\ud83d\udcc5";
        	String price = "";
        	if(alternatives.get(i).getPrice() <= alternatives.get(i).getRecommended_price() + 0.5 && alternatives.get(i).getPrice() >= alternatives.get(i).getRecommended_price() - 0.5) {
        		price = "\ud83d\udd36" + alternatives.get(i).getPrice() + " \u20ac";
        	}else if(alternatives.get(i).getPrice() < alternatives.get(i).getRecommended_price() - 0.5) {
        		price = "\ud83d\udd35" + alternatives.get(i).getPrice() + " \u20ac";
        	}else if (alternatives.get(i).getPrice() > alternatives.get(i).getRecommended_price() + 0.5){
        		price = "\ud83d\udd34" + alternatives.get(i).getPrice() + " \u20ac";
        	}
        	
        	String seats_left = alternatives.get(i).getSeats_left() + "\ud83d\udcba";
        	String car_model; 
        	if(alternatives.get(i).getCar_model().equals("null")) {
        		car_model = "\ud83d\ude98"; 
        	}else {
        		car_model = alternatives.get(i).getCar_model() + "\ud83d\ude98"; 
        	}
        	String distance = alternatives.get(i).getDistance() + " Km";
        	
        	double mins = (alternatives.get(i).getPerfect_duration() % 3600) / 3600.0;
        	int hour = alternatives.get(i).getPerfect_duration() / 3600;
        	DecimalFormat df = new DecimalFormat("#.##");
        	String dx=df.format(hour+mins);
        	String perfect_duration = dx+" h";
        	
        	String perfect_price = alternatives.get(i).getRecommended_price() + " \u20ac";
        	
        	String mean = (i+1) +".  "+dateHour+"     "+price+"     "+ seats_left;
        	
			if(alternatives.get(i).getSeats_left() > 0) {
				travelsBlaBlaCar.add(new TravelBlaBlaCar(mean, dateHour, price, seats_left, car_model, distance, perfect_duration, perfect_price));
			}
			
		}
        
        switch(filter) {
			case PRICE:
					Collections.sort(travelsBlaBlaCar, TravelBlaBlaCar.priceComparator);
				break;
			case SEATS:
					Collections.sort(travelsBlaBlaCar, TravelBlaBlaCar.seatsLeftComparator);
				break;
			default:
					Collections.sort(travelsBlaBlaCar, TravelBlaBlaCar.dateHourComparator);
				break;
        }
        
        for (int i = 0; i < travelsBlaBlaCar.size(); i++) {
			keyboard.add(keyboardRowButton(travelsBlaBlaCar.get(i).getMean()));
		}
        
        replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}


	public static ReplyKeyboardMarkup keyboardStart(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(MANUAL));


		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, Menu.START);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	private static ReplyKeyboardMarkup keyboardRome2RioAfterChoose(long chatId, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
			
			List<KeyboardRow> keyboard = new ArrayList<>();

			keyboard.add(new KeyboardRow());
	        keyboard.get(0).add(NEXT);
	        
	        replyKeyboardMarkup.setKeyboard(keyboard);
	        
	        Current.setMenu(chatId, menu);
		
		
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	private static ReplyKeyboardMarkup keyboardLondonAfterChoose(long chatId, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
			
			List<KeyboardRow> keyboard = new ArrayList<>();

			keyboard.add(new KeyboardRow());
	        keyboard.get(0).add(NEXT);
	        
	        replyKeyboardMarkup.setKeyboard(keyboard);
	        
	        Current.setMenu(chatId, menu);
		
		
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}

	
	
	public static ReplyKeyboardMarkup keyboardRome2RioResult(long chatId,
			ArrayList<TripAlternativeRome2Rio> alternatives, String filter) {
		return keyboardRome2RioResult(chatId, alternatives,
				Menu.ROME2RIORESULT, filter);
	}
	
	public static ReplyKeyboardMarkup keyboardLondonResult(long chatId,
			ArrayList<TripAlternativeLondon> alternatives, String filter) {
		return keyboardLondonResult(chatId, alternatives,
				Menu.LONDONRESULT, filter);
	}
	
	public static ReplyKeyboardMarkup keyboardViaggiaTrentoAfterChoose(long chatId) {
		return keyboardViaggiaTrentoAfterChoose(chatId, Menu.VIAGGIATRENTODESTINATION);
	}
	
	public static ReplyKeyboardMarkup keyboardRome2RioAfterChoose(long chatId) {
		return keyboardRome2RioAfterChoose(chatId, Menu.ROME2RIOAFTERCHOOSE);
	}
	
	public static ReplyKeyboardMarkup keyboardLondonAfterChoose(long chatId) {
		return keyboardLondonAfterChoose(chatId, Menu.LONDONAFTERCHOOSE);
	}

	public static ReplyKeyboardMarkup keyboardBlaBlaCarResult(long chatId,
			ArrayList<TripAlternativeBlaBlaCar> alternatives, String filter) {
		return keyboardBlaBlaCarResult(chatId, alternatives,
				Menu.BLABLACARRESULT, filter);
	}

	
	
	public static ReplyKeyboardMarkup keyboardCalcolaRome2Rio(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(CALCOLA));

		replyKeyboardMarkup.setKeyboard(keyboard);

	
		Current.setMenu(chatId, Menu.CALCOLAROME2RIO);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	public static ReplyKeyboardMarkup keyboardCalcolaBlaBlaCar(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(CALCOLA));

		replyKeyboardMarkup.setKeyboard(keyboard);

	
		Current.setMenu(chatId, Menu.CALCOLABLABLACAR);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	
	
	public static ReplyKeyboardMarkup keyboardChooseAlternatives(long chatId, String partenza, String destinazione) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		
		if(partenza.matches("^[a-z]{2}\\d{1}[a-z]{1}\\d{1}[a-z]{2}") ||
			partenza.matches("^[a-z]{1}\\d{1}[a-z]{1}\\d{1}[a-z]{2}") ||
			partenza.matches("^[a-z]{1}\\d{2}[a-z]{2}") ||
			partenza.matches("^[a-z]{1}\\d{3}[a-z]{2}") ||
			partenza.matches("^[a-z]{2}\\d{2}[a-z]{2}") ||
			partenza.matches("^[a-z]{2}\\d{3}[a-z]{2}")){
			
			if(destinazione.matches("^[a-z]{2}\\d{1}[a-z]{1}\\d{1}[a-z]{2}") ||
					destinazione.matches("^[a-z]{1}\\d{1}[a-z]{1}\\d{1}[a-z]{2}") ||
					destinazione.matches("^[a-z]{1}\\d{2}[a-z]{2}") ||
					destinazione.matches("^[a-z]{1}\\d{3}[a-z]{2}") ||
					destinazione.matches("^[a-z]{2}\\d{2}[a-z]{2}") ||
					destinazione.matches("^[a-z]{2}\\d{3}[a-z]{2}")){
				keyboard.add(keyboardRowButton(LONDON));
			}
		}else{
			keyboard.add(keyboardRowButton(ROME2RIO));
			keyboard.add(keyboardRowButton(BLABLACAR));
			keyboard.add(keyboardRowButton(VIAGGIATRENTO));
			keyboard.add(keyboardRowButton(BIKE));
			if(partenza.equals("trento") || partenza.equals("rovereto")){
				keyboard.add(keyboardRowButton(PARKING));
			}
		}

		replyKeyboardMarkup.setKeyboard(keyboard);

	
		Current.setMenu(chatId, Menu.SELEZIONE_SERVIZIO);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}


	
	public static String setKeyboardJourneyOption(String mean) {
		
		mean = Pattern.compile("Night train", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("Night_trai"));
		
		mean = Pattern.compile("night train", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("night_trai"));
		
		mean = Pattern.compile("Night bus", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("Night_bu"));
		
		mean = Pattern.compile("night bus", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("night_bu"));
		
		mean = Pattern.compile("Car ferry", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("Car_ferr"));
		
		mean = Pattern.compile("car ferry", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("car_ferr"));
		
		if(mean.contains("Line")) {
			mean = Pattern.compile(mean.substring(0, mean.indexOf("train")), Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("Train"));
		}
		
		if(mean.contains("line")) {
			mean = Pattern.compile(mean.substring(0, mean.indexOf("train")), Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("train"));
		}
		
		//train
		mean = Pattern.compile(", train to", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement(" to"));
		
		mean = Pattern.compile("Train", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE84" + "Train"));
		
		mean = Pattern.compile("train", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE84" + "train"));
		
		//night train
		mean = Pattern.compile("Night_trai", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE86" + "Night train"));
		
		mean = Pattern.compile("night_trai", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE86" + "night train"));
		
		//bus
		mean = Pattern.compile("Bus", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE8C" + "Bus"));
		
		mean = Pattern.compile("bus", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE8C" + "bus"));
		
		//night bus
		mean = Pattern.compile("Night_bu", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude8d" + "Night bus"));
		
		mean = Pattern.compile("night_bu", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude8d" + "night bus"));
		
		//drive
		mean = Pattern.compile("Drive", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE98" + "Drive"));
		
		mean = Pattern.compile("drive", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE98" + "drive"));
		
		//national-rail
		mean = Pattern.compile("National-rail", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE84" + "National-rail"));
		
		mean = Pattern.compile("national-rail", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE84" + "national-rail"));
		
		//car ferry
		mean = Pattern.compile("Car_ferr", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\u26F4" + "Car ferry"));
		
		mean = Pattern.compile("car_ferr", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\u26F4" + "car ferry"));
		
		//ferry
		mean = Pattern.compile("Ferry", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\u26F4" + "Ferry"));
				
		mean = Pattern.compile("ferry", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\u26F4" + "ferry"));
		
		//fly
		mean = Pattern.compile("Fly", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDEEB" + "Fly"));
		
		mean = Pattern.compile("fly", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDEEB" + "fly"));
		
		//plane
		mean = Pattern.compile("Plane", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDEEB" + "Plane"));
										
		mean = Pattern.compile("plane", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDEEB" + "plane"));
		
		//shuttle
		mean = Pattern.compile("Shuttle", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE90" + "Shuttle"));
		
		mean = Pattern.compile("shuttle", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE90" + "shuttle"));
		
		//rideshare
		mean = Pattern.compile("Rideshare", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83C\uDFCE" + "Rideshare"));
		
		mean = Pattern.compile("rideshare", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83C\uDFCE" + "rideshare"));
		
		//walk
		mean = Pattern.compile("Walk", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83c\udfc3" + "Walk"));
				
		mean = Pattern.compile("walk", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83c\udfc3" + "walk"));
		
		//eurotunnel
		mean = Pattern.compile("Eurotunnel", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDD73" + "Eurotunnel"));
		
		mean = Pattern.compile("eurotunnel", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDD73" + "eurotunnel"));
		
		//taxi
		mean = Pattern.compile("Taxi", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude95" + "Taxi"));
		
		mean = Pattern.compile("taxi", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude95" + "taxi"));
		
		//unknown
		mean = Pattern.compile("Unknown", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\u2047" + "Unknown"));
			
		mean = Pattern.compile("unknown", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\u2047" + "unknown"));
		
		//helicopter
		mean = Pattern.compile("Helicopter", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude81" + "Helicopter"));
				
		mean = Pattern.compile("helicopter", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude81" + "helicopter"));
		
		//tram
		mean = Pattern.compile("Tram", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude8b" + "Tram"));
						
		mean = Pattern.compile("tram", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude8b" + "tram"));
		
		//bicycle
		mean = Pattern.compile("Bicycle", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\udeb2" + "Bicycle"));
								
		mean = Pattern.compile("bicycle", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\udeb2" + "bicycle"));
		
		//animal
		mean = Pattern.compile("Animal", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\udc2b" + "Animal"));
										
		mean = Pattern.compile("animal", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\udc2b" + "animal"));
		
		return mean;
	}
	
	
	
	//viaggiaTrento
	public static ReplyKeyboardMarkup keyboardChooseStartViaggiaTrento(long chatId, String coorstart, String coordestination, String routeType, String transportType) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		ViaggiaTrentoAPIWrapper viaggiaTrento = new ViaggiaTrentoAPIWrapper();
		travelsViaggiaTrento = viaggiaTrento.getViaggiaTrentoRoutes(coorstart, coordestination, routeType, transportType);
		
		Collections.sort(travelsViaggiaTrento, TravelViaggiaTrento.timeComparator);
		
		for(int i = 0;i<travelsViaggiaTrento.size();i++) {
			int rest = Integer.parseInt(travelsViaggiaTrento.get(i).getDuration()) % 60;
    		int hour = Integer.parseInt(travelsViaggiaTrento.get(i).getDuration()) / 60;
    		String result ="";
    		if(rest<10) {
    			result += (i+1)+". "+"\u23f1"+hour+".0"+rest+"h\n";
    		}else {
    			result += (i+1)+". "+"\u23f1"+hour+"."+rest+"h\n";
    		}
    		keyboard.add(keyboardRowButton(result));
			
		}
		
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, Menu.VIAGGIATRENTODESTINATION);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}

	public static ReplyKeyboardMarkup keyboardChooseViaggiaTrentoYesNo(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		keyboard.add(keyboardRowButton(YES));
		keyboard.add(keyboardRowButton(NO));
		
		replyKeyboardMarkup.setKeyboard(keyboard);
	
		Current.setMenu(chatId, Menu.VIAGGIATRENTOSINO);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	public static ReplyKeyboardMarkup keyboardChooseViaggiaTrentoRouteType(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		keyboard.add(keyboardRowButton("fastest".toUpperCase()));
		keyboard.add(keyboardRowButton("healthy".toUpperCase()));
		keyboard.add(keyboardRowButton("leastWalking".toUpperCase()));
		keyboard.add(keyboardRowButton("leastChanges".toUpperCase()));
		keyboard.add(keyboardRowButton("greenest".toUpperCase()));
		
		replyKeyboardMarkup.setKeyboard(keyboard);
	
		Current.setMenu(chatId, Menu.VIAGGIATRENTOROUTETYPE);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}
	
	public static ReplyKeyboardMarkup keyboardChooseViaggiaTrentoTransportType(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		keyboard.add(keyboardRowButton("TRANSIT"));
		keyboard.add(keyboardRowButton("SHAREDBIKE"));
		keyboard.add(keyboardRowButton("SHAREDBIKE_WITHOUT_STATION"));
		keyboard.add(keyboardRowButton("CARWITHPARKING"));
		keyboard.add(keyboardRowButton("SHAREDCAR"));
		keyboard.add(keyboardRowButton("SHAREDCAR_WITHOUT_STATION"));
		keyboard.add(keyboardRowButton("BUS"));
		keyboard.add(keyboardRowButton("TRAIN"));
		keyboard.add(keyboardRowButton("WALK"));
		
		replyKeyboardMarkup.setKeyboard(keyboard);
	
		Current.setMenu(chatId, Menu.VIAGGIATRENTOTRANSPORTTYPE);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}

}
