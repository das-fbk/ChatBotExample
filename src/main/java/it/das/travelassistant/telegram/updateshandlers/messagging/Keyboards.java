package it.das.travelassistant.telegram.updateshandlers.messagging;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ANDATA;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CALCOLA;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CURRENT;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ENGLISH;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ESPANOL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.INDEX;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ITALIANO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NOW;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.RETURN;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;

import it.sayservice.platform.smartplanner.data.message.otpbeans.Parking;
import it.sayservice.platform.smartplanner.data.message.otpbeans.Route;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import utils.TripAlternative;

/**
 * Created by bucchiarone@fbk.eu
 */
public class Keyboards {

	// region utilities
	private static ArrayList<Travel> travels;

	private static ReplyKeyboardMarkup keyboard() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboad(false);

		return replyKeyboardMarkup;
	}

	private static KeyboardRow keyboardLocationButton(String value,
			Boolean user, Boolean location) {

		KeyboardButton keyboardLocationButton = new KeyboardButton();
		keyboardLocationButton.setText(value);
		// keyboardLocationButton.setRequestContact(true);
		keyboardLocationButton.setRequestLocation(true);
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(keyboardLocationButton);

		return keyboardRow;

	}

	private static KeyboardRow keyboardRowButton(String value) {
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(value);

		return keyboardRow;
	}

	private static KeyboardRow keyboardRowLocation() {
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow
				.add(new KeyboardButton("LOCATION").setRequestLocation(true));

		return keyboardRow;
	}

	private static ReplyKeyboardMarkup keyboardFrom(long chatId,
			List<String> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboardMarkup keyboardTo(long chatId,
			List<String> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboardMarkup keyboardTransportType(long chatId,
			List<String> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboardMarkup keyboardTimeDeparture(long chatId,
			List<String> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboardMarkup keyboardZoneRome2Rio(long chatId,
			List<String> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		// for (String p : zone)
		// keyboard.add(keyboardRowButton(p));

		// keyboard.add(keyboardRowLocation());
		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	public static ArrayList <Travel> getDifferentWayTravel() {
		return travels;
	}
	
	private static ReplyKeyboardMarkup keyboardZoneRome2RioResult(long chatId,
			ArrayList<TripAlternative> alternatives, Menu menu, String filter) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(new KeyboardRow());
        keyboard.get(0).add(PRICE);
        keyboard.get(0).add(TIME);
        keyboard.get(0).add(DISTANCE);
        keyboard.get(0).add(CHANGES);
		
        travels = new ArrayList<Travel>();
        
		for (int i = 0; i < alternatives.size(); i++) {
			String mean = alternatives.get(i).getMean();
			Double duration = alternatives.get(i).getDuration();
			Double cost = alternatives.get(i).getPrice();
			Double distance = alternatives.get(i).getDistance();
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
			
			travels.add(new Travel(mean, duration.toString(), costString, distanceString, shangesString));
			
		}

		switch(filter) {
			case TIME:
					Collections.sort(travels, Travel.timeComparator);
				break;
			case DISTANCE:
					Collections.sort(travels, Travel.distanceComparator);
				break;
			case CHANGES:
					Collections.sort(travels, Travel.changesComparator);
				break;
			default:
					Collections.sort(travels, Travel.priceComparator);
				break;
		}

		for (int i = 0; i < travels.size(); i++) {
			keyboard.add(keyboardRowButton(travels.get(i).getMean()));
		}
		
		
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	// keyboard con i risultato di Viaggia Trento
	private static ReplyKeyboardMarkup keyboardZoneViaggiaTrentoResult(
			long chatId, ArrayList<TripAlternative> alternatives, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (int i = 0; i < alternatives.size(); i++) {
			String mean = alternatives.get(i).getMean();
			Double duration = alternatives.get(i).getDuration();
			Double cost = alternatives.get(i).getPrice();
			Double distance = alternatives.get(i).getDistance();
			String durationString = duration.toString() + " min";
			String distanceString = distance.toString() + " Km";
			String costString = cost.toString() + " \u20ac";

			String finalString = mean + " [" + durationString + " - "
					+ distanceString + " - " + costString + "]";
			keyboard.add(keyboardRowButton(finalString));

		}
		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboard keyboardZoneRome2RioResult(Long chatId,
			ArrayList<TripAlternative> alternatives, Menu menu) {
		// TODO Auto-generated method stub
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (int i = 0; i < alternatives.size(); i++) {
			String mean = alternatives.get(i).getMean();
			keyboard.add(keyboardRowButton(mean));

		}

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;

	}

	private static ReplyKeyboardMarkup keyboardZone(long chatId,
			List<Parking> zone, Menu menu) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (Parking p : zone)
			keyboard.add(keyboardRowButton(p.getName()));

		keyboard.add(keyboardRowLocation());

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));

		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, menu);
		return replyKeyboardMarkup;
	}

	private static InlineKeyboardButton first(InlineKeyboardButton btn) {
		return btn.setText("« " + btn.getText());
	}

	private static InlineKeyboardButton second(InlineKeyboardButton btn) {
		return btn.setText("‹ " + btn.getText());
	}

	private static InlineKeyboardButton penultimate(InlineKeyboardButton btn) {
		return btn.setText(btn.getText() + " ›");
	}

	private static InlineKeyboardButton last(InlineKeyboardButton btn) {
		return btn.setText(btn.getText() + " »");
	}

	// endregion utilities

	// region keyboard

	public static ReplyKeyboardMarkup keyboardStart(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		// prima keyboard dopo lo start
		// chiedo all'utente se vuole usare la sua localizzazione come punto di
		// partenza
		// oppure scriverle

		// keyboard.add(keyboardLocationButton(LOCATION,false,true));
		keyboard.add(keyboardRowButton(MANUAL));

		// keyboard.add(keyboardRowButton(ROME2RIO));

		// keyboard.add(keyboardRowButton(TAXICOMMAND));
		// keyboard.add(keyboardRowButton(AUTOBUSCOMMAND));
		// keyboard.add(keyboardRowButton(TRAINSCOMMAND));
		// keyboard.add(keyboardRowButton(PARKINGSCOMMAND));
		// keyboard.add(keyboardRowButton(BIKESHARINGSCOMMAND));

		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, Menu.START);
		return replyKeyboardMarkup.setOneTimeKeyboad(true);
	}

	public static ReplyKeyboardMarkup keyboardLanguage(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(ITALIANO));
		keyboard.add(keyboardRowButton(ENGLISH));
		keyboard.add(keyboardRowButton(ESPANOL));

		replyKeyboardMarkup.setKeyboard(keyboard);

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		Current.setMenu(chatId, Menu.LANGUAGE);
		return replyKeyboardMarkup;
	}

	public static ReplyKeyboardMarkup keyboardParkings(long chatId,
			List<Parking> parkings) {
		return keyboardZone(chatId, parkings, Menu.PARKINGS);
	}

	// keyboard per la scrittura del FROM del viaggio
	public static ReplyKeyboardMarkup keyboardAskFrom(long chatId,
			List<String> sources) {
		return keyboardFrom(chatId, sources, Menu.TO);
	}

	// keyboard per la scrittura del To del viaggio
	public static ReplyKeyboardMarkup keyboardAskTo(long chatId,
			List<String> sources) {
		return keyboardTo(chatId, sources, Menu.SELEZIONE_SERVIZIO);
	}

	// keyboard per chiedere l'orario di partenza
	public static ReplyKeyboardMarkup keyboardAskTimeDeparture(long chatId,
			List<String> sources) {
		return keyboardTimeDeparture(chatId, sources, Menu.TIMEDEPARTURE);
	}

	// keyboard per chiedere il tipo di trasporto da usare

	public static ReplyKeyboardMarkup keyboardAskTransportType(long chatId,
			List<String> sources) {
		return keyboardTransportType(chatId, sources, Menu.TRANSPORTTYPE);
	}

	public static ReplyKeyboardMarkup keyboardRome2Rio(long chatId,
			List<String> sources) {
		return keyboardZoneRome2Rio(chatId, sources, Menu.ROME2RIO);
	}

	public static ReplyKeyboardMarkup keyboardRome2RioDestination(long chatId,
			List<String> sources) {
		return keyboardZoneRome2Rio(chatId, sources, Menu.ROME2RIODEST);
	}

	public static ReplyKeyboardMarkup keyboardRome2RioResult(long chatId,
			ArrayList<TripAlternative> alternatives, String filter) {
		return keyboardZoneRome2RioResult(chatId, alternatives,
				Menu.ROME2RIORESULT, filter);
	}

	public static ReplyKeyboardMarkup keyboardViaggiaTrentoResult(long chatId,
			ArrayList<TripAlternative> alternatives, String filter) {
		return keyboardZoneRome2RioResult(chatId, alternatives,
				Menu.ROME2RIORESULT,filter);
	}

	public static ReplyKeyboardMarkup keyboardCalcolaRome2Rio(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(CALCOLA));

		replyKeyboardMarkup.setKeyboard(keyboard);

	
		Current.setMenu(chatId, Menu.CALCOLA);
		return replyKeyboardMarkup;
	}

	public static ReplyKeyboardMarkup keyboardCalcolaViaggiaTrento(long chatId) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		keyboard.add(keyboardRowButton(CALCOLA));

		replyKeyboardMarkup.setKeyboard(keyboard);

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		;
		Current.setMenu(chatId, Menu.CALCOLAVIAGGIA);
		return replyKeyboardMarkup;
	}

	public static ReplyKeyboardMarkup keyboardBikeSharings(long chatId,
			List<Parking> bikeSharings) {
		return keyboardZone(chatId, bikeSharings, Menu.BIKESHARINGS);
	}

	public static ReplyKeyboardMarkup keyboardAutobus(long chatId,
			List<Route> autobus) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();
		List<String> autobusWithoutRepeats = new ArrayList<>();
		List<String> autobusNum = new ArrayList<>();
		List<String> autobusTxt = new ArrayList<>();

		for (Route route : autobus)
			if (!autobusWithoutRepeats.contains(route.getRouteShortName()))
				autobusWithoutRepeats.add(route.getRouteShortName());

		for (String string : autobusWithoutRepeats)
			if (NumberUtils.isNumber(string))
				autobusNum.add(string);
			else
				autobusTxt.add(string);

		Collections.sort(autobusNum, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		});

		autobusWithoutRepeats.clear();
		autobusWithoutRepeats.addAll(autobusNum);
		autobusWithoutRepeats.addAll(autobusTxt);

		keyboard.add(new KeyboardRow());
		int elementsInARow = 7;
		int i = 0;
		for (String string : autobusWithoutRepeats) {
			if (keyboard.get(i).size() == elementsInARow) {
				i++;
				keyboard.add(new KeyboardRow());
			}
			keyboard.get(i).add(string);
		}

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, Menu.AUTOBUS);
		return replyKeyboardMarkup;
	}

	public static ReplyKeyboardMarkup keyboardTrains(long chatId,
			List<Route> trains) {
		ReplyKeyboardMarkup replyKeyboardMarkup = keyboard();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (Route r : trains)
			keyboard.add(keyboardRowButton(r.getRouteLongName()));

		byte[] emojiBytes = new byte[] { (byte) 0xF0, (byte) 0x9F, (byte) 0x94,
				(byte) 0x99 };
		String backEmoticon = new String(emojiBytes, Charset.forName("UTF-8"));

		keyboard.add(keyboardRowButton(backEmoticon));
		replyKeyboardMarkup.setKeyboard(keyboard);

		Current.setMenu(chatId, Menu.TRAINS);
		return replyKeyboardMarkup;
	}

	// endregion keyboard

	// region inlineKeyboard

	private static InlineKeyboardMarkup inlineKeyboard(String id, int chosen,
			int lastValue, boolean isAutobus) {
		InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> inlineKeyboard = new ArrayList<>();

		List<InlineKeyboardButton> indexes = new ArrayList<>();
		// region indexes

		// BTNs must be an odd >= 5
		final int BTNs = 7;
		final int BTN_LAST = BTNs - 1;
		final int BTN_PENULTIMATE = BTN_LAST - 1;
		int MAGIC = 1;

		// useful if BTNs change value
		for (int i = 5; i < BTNs; i += 2)
			MAGIC++;

		for (int i = 0; i < BTNs; i++)
			indexes.add(new InlineKeyboardButton());

		if (chosen <= BTN_PENULTIMATE - 1)
			for (int i = 1, value = 1; i < BTN_LAST; i++, value++)
				indexes.get(i).setText(Integer.toString(value));
		else if (chosen >= lastValue - (2 * MAGIC))
			for (int i = 1, value = lastValue - (2 * MAGIC) - 1; i < BTN_LAST; i++, value++)
				indexes.get(i).setText(Integer.toString(value));
		else
			for (int i = 1, value = chosen - MAGIC; i < BTN_LAST; i++, value++)
				indexes.get(i).setText(Integer.toString(value));

		indexes.get(0).setText(Integer.toString(0));
		indexes.get(BTN_LAST).setText(Integer.toString(lastValue));

		for (InlineKeyboardButton btn : indexes)
			btn.setCallbackData(id + '~' + INDEX + '~' + btn.getText());

		if (chosen > BTN_PENULTIMATE - 1) {
			indexes.set(0, first(indexes.get(0)));
			indexes.set(1, second(indexes.get(1)));
		}

		if (chosen < lastValue - (2 * MAGIC)) {
			indexes.set(BTN_PENULTIMATE,
					penultimate(indexes.get(BTN_PENULTIMATE)));
			indexes.set(BTN_LAST, last(indexes.get(BTN_LAST)));
		}

		for (InlineKeyboardButton btn : indexes)
			if (btn.getText().equals(Integer.toString(chosen)))
				btn.setText("· " + btn.getText() + " ·").setCallbackData(
						id + '~' + CURRENT + '~' + Integer.toString(-1));

		// endregion indexes
		inlineKeyboard.add(indexes);

		Character character = id.charAt(id.length() - 1);

		if (isAutobus) {
			List<InlineKeyboardButton> andataReturn = new ArrayList<>();
			// region andataReturn
			switch (character) {
			case 'A':
				andataReturn.add(new InlineKeyboardButton().setText(RETURN)
						.setCallbackData(id + '~' + RETURN + '~' + chosen));
				break;

			case 'R':
				andataReturn.add(new InlineKeyboardButton().setText(ANDATA)
						.setCallbackData(id + '~' + ANDATA + '~' + chosen));
				break;
			}
			// endregion andataReturn
			if (!andataReturn.isEmpty())
				inlineKeyboard.add(andataReturn);
		}

		List<InlineKeyboardButton> now = new ArrayList<>();
		// region now
		now.add(new InlineKeyboardButton().setText(NOW).setCallbackData(
				id + '~' + NOW + '~' + chosen));
		// endregion now
		inlineKeyboard.add(now);

		replyKeyboardMarkup.setKeyboard(inlineKeyboard);
		return replyKeyboardMarkup;
	}

	public static InlineKeyboardMarkup inlineKeyboardAutobus(String id,
			int chosen, int lastValue) {
		return inlineKeyboard(id, chosen, lastValue, true);
	}

	public static InlineKeyboardMarkup inlineKeyboardTrain(String id,
			int chosen, int lastValue) {
		return inlineKeyboard(id, chosen, lastValue, false);
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
		
		
		mean = Pattern.compile(", train to", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement(" to"));
		
		mean = Pattern.compile("Train", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE84" + "Train"));
		
		mean = Pattern.compile("train", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE84" + "train"));
		
		mean = Pattern.compile("Night_trai", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE86" + "Night train"));
		
		mean = Pattern.compile("night_trai", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE86" + "night train"));
		
		mean = Pattern.compile("Bus", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE8C" + "Bus"));
		
		mean = Pattern.compile("bus", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE8C" + "bus"));
		
		mean = Pattern.compile("Night_bu", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude8d" + "Night bus"));
		
		mean = Pattern.compile("night_bu", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude8d" + "night bus"));
		
		mean = Pattern.compile("Drive", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE98" + "Drive"));
		
		mean = Pattern.compile("drive", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE98" + "drive"));
		
		mean = Pattern.compile("Car_ferr", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\u26F4" + "Car ferry"));
		
		mean = Pattern.compile("car_ferr", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\u26F4" + "car ferry"));
		
		mean = Pattern.compile("Fly", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDEEB" + "Fly"));
		
		mean = Pattern.compile("fly", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDEEB" + "fly"));
		
		mean = Pattern.compile("Shuttle", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDE90" + "Shuttle"));
		
		mean = Pattern.compile("shuttle", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDE90" + "shuttle"));
		
		mean = Pattern.compile("Rideshare", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83C\uDFCE" + "Rideshare"));
		
		mean = Pattern.compile("rideshare", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83C\uDFCE" + "rideshare"));
		
		mean = Pattern.compile("Eurotunnel", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\uD83D\uDD73" + "Eurotunnel"));
		
		mean = Pattern.compile("eurotunnel", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\uD83D\uDD73" + "eurotunnel"));
		
		mean = Pattern.compile("Taxi", Pattern.LITERAL).matcher(mean).replaceFirst(Matcher.quoteReplacement("\ud83d\ude95" + "Taxi"));
		
		mean = Pattern.compile("taxi", Pattern.LITERAL).matcher(mean).replaceAll(Matcher.quoteReplacement("\ud83d\ude95" + "taxi"));
		
		
		
		return mean;
	}

}
