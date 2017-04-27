package it.das.travelassistant.telegram.updateshandlers;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ANDATA;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.AUTOBUSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BACKCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CURRENT;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ENGLISH;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ESPANOL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.HELPCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.INDEX;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ITALIANO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.LANGUAGECOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NOW;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.RETURN;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.STARTCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TRAINSCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.inlineKeyboardAutobus;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.inlineKeyboardTrain;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravel;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardAskFrom;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardAskTo;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardAskTransportType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardBikeSharings;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaRome2Rio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaViaggiaTrento;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardLanguage;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardParkings;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioDestination;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textAutobus;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textAutobusHelp;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textBikeSharings;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textBikeSharingsHelp;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textError;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textLanguage;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textLanguageChange;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textParking;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textParkingsHelp;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioCalcola;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartMain;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartRome2Rio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartRome2RioDestination;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textTrain;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textTrainHelp;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textTransportType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoCalcola;
import it.das.travelassistant.telegram.updateshandlers.messagging.Current;
import it.das.travelassistant.telegram.updateshandlers.messagging.Database;
import it.das.travelassistant.telegram.updateshandlers.messagging.Language;
import it.das.travelassistant.telegram.updateshandlers.messagging.Menu;
import it.das.travelassistant.telegram.updateshandlers.messagging.Texts;
import it.sayservice.platform.smartplanner.data.message.otpbeans.Parking;
import it.sayservice.platform.smartplanner.data.message.otpbeans.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import utils.BlaBlaCarAPIWrapper;
import utils.GoogleAPIWrapper;
import utils.Rome2RioAPIWrapper;
import utils.TripAlternative;
import utils.ViaggiaTrentoAPIWrapper;
import eu.trentorise.smartcampus.mobilityservice.MobilityServiceException;
import eu.trentorise.smartcampus.mobilityservice.model.TimeTable;

/**
 * Created by antbucc
 */
public class TravelAssistantBot extends TelegramLongPollingBot {

	private String token, name;
	private String proximity;
	private String timeDeparture;
	private String transportType;
	private ArrayList<Integer> userIDs;
	ArrayList<TripAlternative> alternatives1;

	public ArrayList<Integer> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(ArrayList<Integer> userIDs) {
		this.userIDs = userIDs;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(String timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

	public String getProximity() {
		return proximity;
	}

	public void setProximity(String proximity) {
		this.proximity = proximity;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLongit() {
		return longit;
	}

	public void setLongit(Double longit) {
		this.longit = longit;
	}

	private Double lat, longit;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDestination() {
		return destination;
	}

	public void setStart(String start) {
		this.start = start;
	}

	private String start;
	private String destination;

	public String getStart() {
		return start;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public TravelAssistantBot(String name, String token) {
		super();
		this.token = token;
		this.name = name;
		this.userIDs = new ArrayList<Integer>();

	}

	@Override
	public String getBotUsername() {
		return name;
	}

	@Override
	public String getBotToken() {
		return token;
	}

	@Override
	public void onUpdateReceived(Update update) {

		// memorizza ID utente per le notifiche

		try {
			if (update.hasCallbackQuery())
				handleIncomingCallbackQuery(update.getCallbackQuery());

			if (update.hasMessage()) {
				Message message = update.getMessage();
				if (message.hasText())
					handleIncomingTextMessage(message);
				if (message.hasLocation())
					handleIncomingPositionMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final ConcurrentHashMap<Integer, Integer> userState = new ConcurrentHashMap<>();

	// Incoming Messages Handler
	private void handleIncomingTextMessage(Message message)
			throws TelegramApiException, MobilityServiceException,
			ExecutionException, IOException {

		Long chatId = message.getChatId();
		System.out.println("chatID: " + chatId);

		Integer userID = message.getFrom().getId();
		this.userIDs.add(userID);
		System.out.println(userID);

		switch (message.getText()) {
		// region commands

		case LANGUAGECOMMAND:
			sendMessageDefault(message, keyboardLanguage(chatId),
					textLanguage(Current.getLanguage(chatId)));
			break;

		case STARTCOMMAND:
			// SendMessage sendMessage = new
			// SendMessage().setChatId("305720808");

			// sendMessage.setText("INVIO NOTIDFICA");

			// sendMessage(sendMessage);
			sendMessageDefault(message, keyboardStart(chatId), textStart(Current.getLanguage(chatId)));
			break;

		case BACKCOMMAND:
			sendMessageDefault(message, keyboardStart(chatId), textStartMain(Current.getLanguage(chatId)));
			break;
		case MANUAL:
			// Inserimento Manuale, Chiedi la citta' di partenza
			sendMessageDefault(message, keyboardAskFrom(chatId, Database.getRome2RioSource()), textStartRome2Rio(Current.getLanguage(chatId)));
			break;

		// end messages-based commands

		default:
			System.out.println("MENU: " + Current.getMenu(chatId));
			switch (Current.getMenu(chatId)) {
			// menu messages
			case START:
				sendMessageDefaultWithReply(message, keyboardStart(chatId),
						textError(Current.getLanguage(chatId)));
				/*
				 * NOTIFICA AI CHAT ID SendMessage sendMessage1 = new
				 * SendMessage() .setChatId("305720808");
				 * 
				 * sendMessage1
				 * .setText("--- IL TUO TRENO E' IN RITARDO di 10 MIN ---");
				 * SendMessage sendMessage2 = new SendMessage()
				 * .setChatId("301878958");
				 * 
				 * sendMessage2
				 * .setText("--- IL TUO TRENO E' IN RITARDO di 10 MIN ---");
				 * 
				 * sendMessage(sendMessage2); SendMessage sendMessage3 = new
				 * SendMessage() .setChatId("16747501");
				 * 
				 * sendMessage3
				 * .setText("--- IL TUO TRENO E' IN RITARDO di 10 MIN ---");
				 * 
				 * sendMessage(sendMessage3);
				 */
				break;
			case FROM:
				sendMessageDefault(message,keyboardAskFrom(chatId,Database.getRome2RioDestination()),textStartRome2RioDestination(Current.getLanguage(chatId)));
				break;
			case TO:

				// memorizza la citta di partenza

				this.setStart(message.getText());
				sendMessageDefault(message,keyboardAskTo(chatId, Database.getRome2RioDestination()),textStartRome2RioDestination(Current.getLanguage(chatId)));

				break;
			case SELEZIONE_SERVIZIO:
				//
				this.setDestination(message.getText());
				// qui devo capire se e' un servizio locale o globale in base
				// alla citta' di partenza e di arrivo
				System.out.println(this.getStart());
				System.out.println(this.getDestination());
				String proximity = this.calculateProximity(this.getStart(),
						this.getDestination());
				this.setProximity(proximity);
				/*
				 * if (proximity.equals("locale")) { // servizio locale -
				 * Viaggia Trento // ask More information before to use Viaggia
				 * Trento sendMessageDefault( message,
				 * keyboardAskTimeDeparture(chatId,
				 * Database.getRome2RioDestination()),
				 * textViaggiaTimeDeparture(Current .getLanguage(chatId)));
				 * 
				 * } else { // servizio globale - Rome2Rio
				 * sendMessageDefault(message, keyboardCalcolaRome2Rio(chatId),
				 * textRome2RioCalcola(Current.getLanguage(chatId)));
				 * 
				 * }
				 */
				sendMessageDefault(message, keyboardCalcolaRome2Rio(chatId),textRome2RioCalcola(Current.getLanguage(chatId)));

				break;
			case TIMEDEPARTURE:
				// memorizza l'orario di partenza e chiedi il modo di trasposto
				this.setTimeDeparture(message.getText());
				sendMessageDefault(
						message,
						keyboardAskTransportType(chatId,
								Database.getRome2RioDestination()),
						textTransportType(Current.getLanguage(chatId)));
			case TRANSPORTTYPE:
				// memorizza l'orario di partenza e chiedi di calcolare le
				// alternative
				System.out.println(message.getText());
				this.setTransportType(message.getText());
				sendMessageDefault(message,
						keyboardCalcolaViaggiaTrento(chatId),
						textViaggiaTrentoCalcola(Current.getLanguage(chatId)));
				break;
			case CALCOLAVIAGGIA:

				System.out.println(message.getText());
				// chiamo il servizio Viagga Trento e elabora le soluzioni di
				// viaggio.
				ArrayList<TripAlternative> alternatives = new ArrayList<TripAlternative>();
				ViaggiaTrentoAPIWrapper viaggiaWrapper = new ViaggiaTrentoAPIWrapper();

				// prepare input(from,to,date)
				System.out.println("FROM: " + this.getStart());
				System.out.println("TO: " + this.getDestination());

				// chiama il servizio google per estrarre lat e long del FROM e
				// del TO

				GoogleAPIWrapper googleAPI = new GoogleAPIWrapper();
				String From = googleAPI.getCoordinates(this.getStart());
				String To = googleAPI.getCoordinates(this.getDestination());
				String time = this.getTimeDeparture();

				// chiama il servizio Viaggia Trento per prendere le alternative

				boolean nontrovate = true;

				String from = this.getStart();
				String to = this.getDestination();
				alternatives = viaggiaWrapper.getViaggiaAlternatives(From, To,
						time);
				/*
				 * alternatives = viaggiaWrapper.getAlternatives(From,To,time);
				 * 
				 * if(alternatives.size() != 0) { // visualizzo le alternative
				 * come bottoni sendMessageDefault(message,
				 * keyboardViaggiaTrentoResult(chatId, alternatives),
				 * textRome2RioResult(Current.getLanguage(chatId))); nontrovate
				 * = false; }
				 */
				break;

			case LANGUAGE:
				// region menu.LANGUAGE
				switch (message.getText()) {
				case ITALIANO:
					Current.setLanguage(chatId, Language.ITALIANO);
					sendMessageDefault(message, keyboardLanguage(chatId),
							textLanguageChange(Current.getLanguage(chatId)));
					break;
				case ENGLISH:
					Current.setLanguage(chatId, Language.ENGLISH);
					sendMessageDefault(message, keyboardLanguage(chatId),
							textLanguageChange(Current.getLanguage(chatId)));
					break;
				case ESPANOL:
					Current.setLanguage(chatId, Language.ESPANOL);
					sendMessageDefault(message, keyboardLanguage(chatId),
							textLanguageChange(Current.getLanguage(chatId)));
					break;
				default:
					sendMessageDefaultWithReply(message, keyboardStart(chatId),
							textError(Current.getLanguage(chatId)));
					break;
				}
				// endregion menu.LANGUAGE
				break;
			/*
			 * case START: // region menu.START
			 * System.out.println("qui: "+message.getText()); switch
			 * (message.getText()) {
			 * 
			 * 
			 * case MANUAL: // sendMessageDefault(message,
			 * keyboardRome2Rio(chatId, Database.getRome2RioSource()),
			 * textStartRome2Rio(Current.getLanguage(chatId)));
			 * System.out.println("Inserimenti Manauel dall'utente"); break;
			 * case ROME2RIO:
			 * 
			 * sendMessageDefault(message, keyboardRome2Rio(chatId,
			 * Database.getRome2RioSource()),
			 * textStartRome2Rio(Current.getLanguage(chatId))); break;
			 * 
			 * case HELPCOMMAND: sendMessageDefault(message,
			 * keyboardStart(chatId),
			 * textStartHelp(Current.getLanguage(chatId))); break; case
			 * TAXICOMMAND: sendMessageDefault(message, keyboardStart(chatId),
			 * textStartTaxi(Database.getTaxiContacts())); break; case
			 * AUTOBUSCOMMAND: sendMessageDefault(message,
			 * keyboardAutobus(chatId, Database.getAutobusRoutes()),
			 * textStartAutobus(Current.getLanguage(chatId))); break; case
			 * TRAINSCOMMAND: sendMessageDefault(message, keyboardTrains(chatId,
			 * Database.getTrainsRoutes()),
			 * textStartTrains(Current.getLanguage(chatId))); break; case
			 * PARKINGSCOMMAND: sendMessageDefault(message,
			 * keyboardParkings(chatId, Database.getParkings()),
			 * textStartParkings(Current.getLanguage(chatId))); break; case
			 * BIKESHARINGSCOMMAND: sendMessageDefault(message,
			 * keyboardBikeSharings(chatId, Database.getBikeSharings()),
			 * textStartBikeSharings(Current.getLanguage(chatId))); break;
			 * default: sendMessageDefaultWithReply(message,
			 * keyboardStart(chatId), textError(Current.getLanguage(chatId)));
			 * break; } // endregion start menu break;
			 */
			case AUTOBUS:
				// region menu.AUTOBUS
				switch (message.getText()) {
				case HELPCOMMAND:
					sendMessageDefault(message,
							textAutobusHelp(Current.getLanguage(chatId)));
					break;
				default:
					autobus(message);
					break;
				}
				// endregion menu.AUTOBUS
				break;
			case TRAINS:
				// region menu.TRAINS
				switch (message.getText()) {
				case HELPCOMMAND:
					sendMessageDefault(message,
							textTrainHelp(Current.getLanguage(chatId)));
					break;
				default:
					trains(message);
					break;
				}
				// endregion menu.TRAINS
				break;
			case PARKINGS:
				// region menu.PARKINGS
				switch (message.getText()) {
				case HELPCOMMAND:
					sendMessageDefault(message,
							textParkingsHelp(Current.getLanguage(chatId)));
					break;
				default:
					zone(message, Menu.PARKINGS);
					break;
				}
				// endregion menu.PARKINGS
				break;
			case BIKESHARINGS:
				// region menu.BIKESHARINGS
				switch (message.getText()) {
				case HELPCOMMAND:
					sendMessageDefault(message,
							textBikeSharingsHelp(Current.getLanguage(chatId)));
					break;
				default:
					zone(message, Menu.BIKESHARINGS);
					break;
				}
				// endregion menu.BIKESHARINGS
				break;
			case ROME2RIO:
				// region menu.ROME2RIO
				this.setStart(message.getText());

				sendMessageDefault(
						message,
						keyboardRome2RioDestination(chatId,
								Database.getRome2RioDestination()),
						textStartRome2RioDestination(Current
								.getLanguage(chatId)));

				break;
			case ROME2RIODEST:
				// region menu.ROME2RIO
				this.setDestination(message.getText());

				sendMessageDefault(message, keyboardCalcolaRome2Rio(chatId),
						textRome2RioCalcola(Current.getLanguage(chatId)));
				break;
			case ROME2RIORESULT:
				switch(message.getText()) {
					case PRICE:
						sendMessageDefault(message,keyboardRome2RioResult(chatId, alternatives1, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravel(), message.getText()));
						break;
					case TIME:
						sendMessageDefault(message,keyboardRome2RioResult(chatId, alternatives1, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravel(), message.getText()));
						break;
					case CHANGES:
						sendMessageDefault(message,keyboardRome2RioResult(chatId, alternatives1, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravel(), message.getText()));
						break;
					case DISTANCE:
						sendMessageDefault(message,keyboardRome2RioResult(chatId, alternatives1, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravel(), message.getText()));
						break;
					default:
						break;
				}
				break;

			case CALCOLA:

				alternatives1 = new ArrayList<TripAlternative>();
				// qui dovresti selezionare o Rome2Rio o ViaggiaTrento in base
				// alla proximity
				
				//if (this.getProximity().equals("globale")) {

					Rome2RioAPIWrapper rome2RioWrapper = new Rome2RioAPIWrapper();

					boolean nontrovate1 = true;

					String from1 = this.getStart();
					String to1 = this.getDestination();
					alternatives1 = rome2RioWrapper.getRome2RioAlternatives(from1, to1);

					if (alternatives1.size() != 0) {
						// visualizzo le alternative come bottoni
						sendMessageDefault(message,keyboardRome2RioResult(chatId, alternatives1, "NULL"), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravel(), ""));
						nontrovate1 = false;
					}

				//} else {
					// ask more informations before to call the Viaggia Trento
					// Service
					//sendMessageDefault(message, keyboardAskTo(chatId, Database.getRome2RioDestination()), textStartRome2RioDestination(Current.getLanguage(chatId)));
				//}

				break;
			}
		}
	}

	private String calculateProximity(String start, String destination) {
		// TODO Auto-generated method stub
		String result = "";

		if (start.contains("trento") || destination.contains("rovereto")) {
			result = "locale";

		} else {
			result = "globale";
		}

		return result;
	}

	private void handleIncomingPositionMessage(Message message)
			throws TelegramApiException, MobilityServiceException,
			ExecutionException {

		Long chatId = message.getChatId();

		// memorizza la lat e long dell'utente
		this.setLat(message.getLocation().getLatitude());
		this.setLongit(message.getLocation().getLongitude());
		System.out.println(Current.getMenu(message.getChatId()));

		// qui calcola la posizione corrente usando il wrapper di Google API
		GoogleAPIWrapper googleAPI = new GoogleAPIWrapper();
		String indirizzo = googleAPI
				.getAddress(this.getLat(), this.getLongit());
		System.out.println("result: " + indirizzo);
		String[] parts = indirizzo.split(",");
		System.out.println(parts[2]);

		String[] subparts = parts[2].split(" ");
		String citta = subparts[2];

		this.setStart(citta);

		switch (Current.getMenu(message.getChatId())) {
		case START:
			// qui do il messaggio che puo' selezionare il servizio ROME2RIO
			// OPPURE VIAGGIA TRENTO
			sendMessageDefault(
					message,
					keyboardRome2RioDestination(chatId,
							Database.getRome2RioDestination()),
					textStartRome2RioDestination(Current.getLanguage(chatId)));

			// error(message);
			break;
		/*
		 * case AUTOBUS: error(message); break; case TRAINS: error(message);
		 * break; case LANGUAGE: error(message); break; case PARKINGS:
		 * sendMessageDefault(message,
		 * Keyboards.keyboardParkings(message.getChatId(),
		 * Database.getParkings()),
		 * textParkingsNear(Database.findNear(Database.getParkings(),
		 * message.getLocation(), 1.5),
		 * Current.getLanguage(message.getChatId()))); break; case BIKESHARINGS:
		 * sendMessageDefault(message,
		 * Keyboards.keyboardBikeSharings(message.getChatId(),
		 * Database.getBikeSharings()),
		 * textBikeSharingsNear(Database.findNear(Database.getBikeSharings(),
		 * message.getLocation(),
		 * 0.5),Current.getLanguage(message.getChatId()))); break;
		 */
		}
	}

	private void handleIncomingCallbackQuery(CallbackQuery cbq)
			throws TelegramApiException, MobilityServiceException,
			ExecutionException {
		Message message = cbq.getMessage();

		if (message.getText().startsWith(AUTOBUSCOMMAND))
			autobusEdit(cbq);
		else if (message.getText().startsWith(TRAINSCOMMAND)) {
			trainsEdit(cbq);
		}
	}

	// endregion handlers

	// region voids

	private void autobus(Message message) throws TelegramApiException,
			MobilityServiceException, ExecutionException {
		String routeId = Database.findAutobusAndataRouteId(message.getText());

		if (routeId == null)
			error(message);
		else {
			TimeTable timeTable = Database.getAutobusTimetable(routeId);
			int index = Database.findCurrentIndex(timeTable);
			sendMessageDefault(
					message,
					inlineKeyboardAutobus(routeId, index, timeTable.getTimes()
							.size() - 1),
					textAutobus(message.getText(), timeTable, index));
		}
	}

	private void trains(Message message) throws TelegramApiException,
			MobilityServiceException, ExecutionException {
		String routeId = Database.findTrainRouteId(message.getText());

		if (routeId == null) {
			error(message);
		} else {
			TimeTable timeTable = Database.getTrainTimetable(routeId);
			int index = Database.findCurrentIndex(timeTable);
			sendMessageDefault(
					message,
					inlineKeyboardTrain(routeId, index, timeTable.getTimes()
							.size() - 1),
					textTrain(message.getText(), timeTable, index));
		}
	}

	private void autobusEdit(CallbackQuery cbq)
			throws MobilityServiceException, TelegramApiException,
			ExecutionException {

		String routeId = cbq.getData().substring(0, cbq.getData().indexOf('~'));
		String option = cbq.getData().substring(cbq.getData().indexOf('~') + 1,
				cbq.getData().lastIndexOf('~'));
		int chosen = Integer.parseInt(cbq.getData().substring(
				cbq.getData().lastIndexOf('~') + 1));

		TimeTable timeTable;

		switch (option) {
		case CURRENT:
			// DO NOTHING
			break;

		case INDEX:
			timeTable = Database.getAutobusTimetable(routeId);

			autobusSendEdit(routeId, chosen, timeTable, cbq);
			break;

		case RETURN:
			routeId = routeId.replace('A', 'R');
			timeTable = Database.getAutobusTimetable(routeId);

			autobusSendEdit(routeId, chosen, timeTable, cbq);
			break;

		case ANDATA:
			routeId = routeId.replace('R', 'A');
			timeTable = Database.getAutobusTimetable(routeId);

			autobusSendEdit(routeId, chosen, timeTable, cbq);
			break;

		case NOW:
			timeTable = Database.getAutobusTimetable(routeId);
			int now = Database.findCurrentIndex(timeTable);

			if (now != chosen)
				autobusSendEdit(routeId, now, timeTable, cbq);
			break;
		}

		answerCallbackQuery(cbq, AUTOBUSCOMMAND);
	}

	private void trainsEdit(CallbackQuery cbq) throws MobilityServiceException,
			TelegramApiException, ExecutionException {

		String routeId = cbq.getData().substring(0, cbq.getData().indexOf('~'));
		String option = cbq.getData().substring(cbq.getData().indexOf('~') + 1,
				cbq.getData().lastIndexOf('~'));
		int chosen = Integer.parseInt(cbq.getData().substring(
				cbq.getData().lastIndexOf('~') + 1));

		TimeTable timeTable;

		switch (option) {
		case CURRENT:
			// DO NOTHING
			break;

		case INDEX:
			timeTable = Database.getTrainTimetable(routeId);

			trainSendEdit(routeId, chosen, timeTable, cbq);
			break;

		case NOW:
			timeTable = Database.getTrainTimetable(routeId);
			int now = Database.findCurrentIndex(timeTable);

			if (now != chosen)
				trainSendEdit(routeId, now, timeTable, cbq);
			break;
		}

		answerCallbackQuery(cbq, TRAINSCOMMAND);
	}

	private void autobusSendEdit(String routeId, int chosen,
			TimeTable timeTable, CallbackQuery cbq)
			throws MobilityServiceException, TelegramApiException,
			ExecutionException {
		String nameAutobus = "";

		for (Route route : Database.getAutobusRoutes())
			if (route.getId().getId().equals(routeId))
				nameAutobus = route.getRouteShortName();

		editMessageDefault(
				cbq.getMessage(),
				inlineKeyboardAutobus(routeId, chosen, timeTable.getTimes()
						.size() - 1),
				textAutobus(nameAutobus, timeTable, chosen));
	}

	private void trainSendEdit(String routeId, int chosen, TimeTable timeTable,
			CallbackQuery cbq) throws MobilityServiceException,
			TelegramApiException, ExecutionException {
		String nameTrain = "";

		for (Route route : Database.getTrainsRoutes())
			if (route.getId().getId().equals(routeId))
				nameTrain = route.getRouteLongName();

		editMessageDefault(
				cbq.getMessage(),
				inlineKeyboardTrain(routeId, chosen, timeTable.getTimes()
						.size() - 1), textTrain(nameTrain, timeTable, chosen));
	}

	private void zone(Message message, Menu menu) throws TelegramApiException,
			MobilityServiceException, ExecutionException {
		boolean flag = false;
		List<Parking> parkings = menu == Menu.PARKINGS ? Database.getParkings()
				: menu == Menu.BIKESHARINGS ? Database.getBikeSharings()
						: new ArrayList<>();

		String text = message.getText();

		for (Parking p : parkings)
			if (p.getName().equals(text)) {
				switch (menu) {
				case PARKINGS:
					sendMessageDefault(
							message,
							keyboardParkings(message.getChatId(), parkings),
							textParking(p,
									Current.getLanguage(message.getChatId())));
					break;
				case BIKESHARINGS:
					sendMessageDefault(
							message,
							keyboardBikeSharings(message.getChatId(), parkings),
							textBikeSharings(p,
									Current.getLanguage(message.getChatId())));
					break;
				default:
					break;
				}
				sendVenueDefault(message, (float) p.getPosition()[0],
						(float) p.getPosition()[1]);
				flag = true;
			}

		if (!flag)
			error(message);
	}

	private void error(Message message) throws TelegramApiException {
		sendMessageDefaultWithReply(message, null,
				Texts.textError(Current.getLanguage(message.getChatId())));
	}

	// endregion voids

	// region utilities

	private void answerCallbackQuery(CallbackQuery cbq, String aCbqText)
			throws TelegramApiException {
		AnswerCallbackQuery aCbq = new AnswerCallbackQuery();
		aCbq.setCallbackQueryId(cbq.getId());
		aCbq.setText(aCbqText);
		answerCallbackQuery(aCbq);
	}

	private void editMessageDefault(Message message,
			InlineKeyboardMarkup keyboard, String messageText)
			throws TelegramApiException {
		EditMessageText edit = new EditMessageText();
		edit.enableMarkdown(true);
		edit.setMessageId(message.getMessageId());
		edit.setChatId(message.getChatId().toString());
		edit.setText(messageText);
		edit.setReplyMarkup(keyboard);
		editMessageText(edit);
	}

	private void sendMessageDefaultWithReply(Message message,
			ReplyKeyboard keyboard, String text) throws TelegramApiException {
		SendMessage sendMessage = new SendMessage().setChatId(
				message.getChatId().toString()).enableMarkdown(true);
		sendMessage.setText(text);
		sendMessage.setReplyMarkup(keyboard);
		sendMessage.setReplyToMessageId(message.getMessageId());

		sendMessage(sendMessage);
	}

	private void sendMessageHTML(Message message, String link)
			throws TelegramApiException {

		SendMessage message1 = new SendMessage().setChatId(
				message.getChatId().toString()).enableHtml(true);
		// String text1 =
		// "<b>bold</b> <i>italic</i> <a href='http://google.com'>link</a>";
		/*
		 * InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		 * List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		 * List<InlineKeyboardButton> rowInline = new ArrayList<>();
		 * rowInline.add(new InlineKeyboardButton().setText("<b>dsfds</b>")
		 * .setCallbackData("update_msg_text")); // Set the keyboard to the
		 * markup rowsInline.add(rowInline); // Add it to the message
		 * markupInline.setKeyboard(rowsInline);
		 * 
		 * message1.setReplyMarkup(markupInline);
		 * {"duration":22800,"pager":{"total"
		 * :42,"pages":5,"limit":10,"page":1},"
		 * distance":585,"trips":[{"freeway":true,"
		 * distance":{"unity":"km","value":537},
		 * "departure_date":"23/03/2017 10:30:13",
		 * "permanent_id":"764254730-lido-di-ostia-verona","seats":3,
		 * "departure_place":{"country_code":"IT","city_name":"Ostia"
		 * ,"address":
		 * "Viale Capitan Consalvo, 2, 00122 Lido di Ostia RM, Italia"
		 * ,"latitude":41.7321867,"longitude":12.2784487},
		 * "arrival_place":{"country_code":"IT","city_name":"Verona",
		 * "address":"Corso Porta Nuova, 55, 37122 Verona VR, Italia","
		 * latitude":45.43532,"longitude":10.99022},"frequency":"UNIQUE","
		 * locations_to_display
		 * ":["Ostia","Verona"],"duration":{"unity":"s","value":21000},
		 * "price_with_commission"
		 * :{"symbol":"£","string_value":"£29.50","currency":"GBP","
		 * price_color":"
		 * BLACK","value":29.5},"price_without_commission":{"symbol":"£","
		 * string_value
		 * ":"£26.00","currency":"GBP","price_color":"BLACK","value":26},"s
		 * eats_left
		 * ":3,"car":{"comfort_nb_star":3,"model":"SORENTO","make":"KIA","
		 * comfort"
		 * :"Comfortable"},"price":{"symbol":"£","string_value":"£26.00"
		 * ,"currency":"
		 * GBP","price_color":"BLACK","value":26},"is_comfort":true,"links":
		 * {"_front"
		 * :"https://www.blablacar.co.uk/trip-lido-di-ostia-verona-764254730"
		 * ,"_threads":
		 * "https://api.blablacar.com/api/v2/trips/764254730-lido-di-ostia-verona/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/764254730-lido-di-ostia-verona?_locale=en_GB"
		 * },"commission":{"symbol":"£","string_value":"£3.50","currency":"GBP",
		 * "value"
		 * :3.5},"booking_type":"online","viaggio_rosa":false,"booking_mode"
		 * :"auto"
		 * },{"freeway":true,"distance":{"unity":"km","value":612},"departure_date"
		 * :
		 * "23/03/2017 17:00:00","permanent_id":"765069785-roma-trento","seats":
		 * 3
		 * ,"departure_place":{"country_code":"IT","city_name":"Rome","address":
		 * "EUR Palasport, 00144 Roma, Italia"
		 * ,"latitude":41.8301391,"longitude":
		 * 12.4660062},"arrival_place":{"country_code"
		 * :"IT","city_name":"Trento",
		 * "address":"Stazione ferroviaria, Trento","latitude"
		 * :46.072057,"longitude"
		 * :11.119602},"answer_delay":12,"frequency":"UNIQUE"
		 * ,"locations_to_display"
		 * :["Rome","Trento"],"duration":{"unity":"s","value"
		 * :25200},"price_with_commission"
		 * :{"symbol":"£","string_value":"£27.00",
		 * "currency":"GBP","price_color":
		 * "BLACK","value":27},"price_without_commission"
		 * :{"symbol":"£","string_value"
		 * :"£24.00","currency":"GBP","price_color":
		 * "BLACK","value":24},"seats_left"
		 * :2,"car":{"comfort_nb_star":3,"model":
		 * "GLA 200 CDI","make":"MERCEDES",
		 * "comfort":"Comfortable"},"price":{"symbol"
		 * :"£","string_value":"£24.00"
		 * ,"currency":"GBP","price_color":"BLACK","value"
		 * :24},"is_comfort":true,"links":{"_front":
		 * "https://www.blablacar.co.uk/trip-roma-trento-765069785","_threads":
		 * "https://api.blablacar.com/api/v2/trips/765069785-roma-trento/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/765069785-roma-trento?_locale=en_GB"
		 * },"arrival_meeting_point":{"country_code":"IT","city_name":"Trento",
		 * "full_name"
		 * :"Stazione ferroviaria, Trento","address":"Piazza Dante, 38121 Trento"
		 * ,
		 * "latitude":46.072057,"name":"Stazione ferroviaria","id":1867,"properties"
		 * :[],"longitude":11.119602,"tags":[]},"commission":{"symbol":"£",
		 * "string_value"
		 * :"£3.00","currency":"GBP","value":3},"booking_type":"online"
		 * ,"viaggio_rosa"
		 * :false,"booking_mode":"manual"},{"departure_meeting_point"
		 * :{"country_code"
		 * :"IT","city_name":"Viterbo","full_name":"Shell, Viterbo"
		 * ,"address":"Via dell'Industria, 01100 Viterbo VT, Italy"
		 * ,"latitude":42.4372311
		 * ,"name":"Shell","id":626281,"properties":[],"longitude"
		 * :12.0946304,"tags"
		 * :[]},"freeway":true,"distance":{"unity":"km","value"
		 * :575},"departure_date"
		 * :"23/03/2017 17:00:25","permanent_id":"765890430-viterbo-bolzano"
		 * ,"seats"
		 * :2,"departure_place":{"country_code":"IT","city_name":"Viterbo"
		 * ,"address"
		 * :"Shell, Viterbo","latitude":42.4372311,"longitude":12.0946304
		 * },"arrival_place"
		 * :{"country_code":"IT","city_name":"Bolzano","address"
		 * :"Stazione ferroviaria, Bolzano"
		 * ,"latitude":46.496883,"longitude":11.358459
		 * },"answer_delay":12,"frequency"
		 * :"UNIQUE","locations_to_display":["Viterbo"
		 * ,"Bolzano"],"duration":{"unity"
		 * :"s","value":22800},"price_with_commission"
		 * :{"symbol":"£","string_value"
		 * :"£36.00","currency":"GBP","price_color":
		 * "BLACK","value":36},"price_without_commission"
		 * :{"symbol":"£","string_value"
		 * :"£32.00","currency":"GBP","price_color":
		 * "BLACK","value":32},"seats_left"
		 * :2,"car":{"comfort_nb_star":2,"model":
		 * "GENESIS","make":"HYUNDAI","comfort"
		 * :"Normal"},"price":{"symbol":"£","string_value"
		 * :"£32.00","currency":"GBP"
		 * ,"price_color":"BLACK","value":32},"is_comfort"
		 * :false,"links":{"_front"
		 * :"https://www.blablacar.co.uk/trip-viterbo-bolzano-765890430"
		 * ,"_threads":
		 * "https://api.blablacar.com/api/v2/trips/765890430-viterbo-bolzano/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/765890430-viterbo-bolzano?_locale=en_GB"
		 * },"arrival_meeting_point":{"country_code":"IT","city_name":"Bolzano",
		 * "full_name":"Stazione ferroviaria, Bolzano","address":
		 * "Piazza della Stazione, 1, 39100 Bolzano, Provincia di Bolzano - Alto Adige, Italia"
		 * ,
		 * "latitude":46.496883,"name":"Stazione ferroviaria","id":1559,"properties"
		 * :[],"longitude":11.358459,"tags":[]},"commission":{"symbol":"£",
		 * "string_value"
		 * :"£4.00","currency":"GBP","value":4},"booking_type":"online"
		 * ,"viaggio_rosa"
		 * :false,"booking_mode":"manual"},{"freeway":true,"distance"
		 * :{"unity":"km"
		 * ,"value":593},"departure_date":"24/03/2017 06:30:36","permanent_id"
		 * :"765783130-roma-trento"
		 * ,"seats":3,"departure_place":{"country_code":"IT"
		 * ,"city_name":"Rome","address"
		 * :"Anagnina, 00173 Roma, Italia","latitude"
		 * :41.8426285,"longitude":12.5864169
		 * },"arrival_place":{"country_code":"IT"
		 * ,"city_name":"Trento","address":
		 * "Via Giovanni Antonio Scopoli, 23-25, 38121 Trento, Italia"
		 * ,"latitude":46.0765746,"longitude":11.117922},"frequency":"UNIQUE",
		 * "locations_to_display"
		 * :["Rome","Trento","Baselga di Piné"],"duration":
		 * {"unity":"s","value":28200
		 * },"price_with_commission":{"symbol":"£","string_value"
		 * :"£30.50","currency"
		 * :"GBP","price_color":"BLACK","value":30.5},"price_without_commission"
		 * :
		 * {"symbol":"£","string_value":"£27.00","currency":"GBP","price_color":
		 * "BLACK"
		 * ,"value":27},"seats_left":1,"car":{"comfort_nb_star":3,"model":
		 * "MiTo",
		 * "make":"ALFA ROMEO","comfort":"Comfortable"},"price":{"symbol":
		 * "£","string_value"
		 * :"£27.00","currency":"GBP","price_color":"BLACK","value"
		 * :27},"is_comfort":false,"links":{"_front":
		 * "https://www.blablacar.co.uk/trip-roma-trento-765783130","_threads":
		 * "https://api.blablacar.com/api/v2/trips/765783130-roma-trento/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/765783130-roma-trento?_locale=en_GB"
		 * },"commission":{"symbol":"£","string_value":"£3.50","currency":"GBP",
		 * "value"
		 * :3.5},"booking_type":"online","viaggio_rosa":false,"booking_mode"
		 * :"auto"
		 * },{"freeway":true,"distance":{"unity":"km","value":526},"departure_date"
		 * :
		 * "24/03/2017 08:00:00","permanent_id":"765404565-roma-vicenza","seats"
		 * :
		 * 3,"departure_place":{"country_code":"IT","city_name":"Rome","address"
		 * :
		 * "Roma, RM","latitude":41.9027835,"longitude":12.4963655},"arrival_place"
		 * :{"country_code":"IT","city_name":"Vicenza","address":"Vicenza, VI",
		 * "latitude"
		 * :45.5454787,"longitude":11.5354214},"answer_delay":3,"frequency"
		 * :"UNIQUE"
		 * ,"locations_to_display":["Rome","Vicenza"],"duration":{"unity"
		 * :"s","value"
		 * :28200},"price_with_commission":{"symbol":"£","string_value"
		 * :"£25.00","currency":"GBP","price_color":"BLACK","value":25},
		 * "price_without_commission"
		 * :{"symbol":"£","string_value":"£22.00","currency"
		 * :"GBP","price_color":
		 * "BLACK","value":22},"seats_left":3,"car":{"comfort_nb_star"
		 * :3,"model":
		 * "CLK","make":"MERCEDES","comfort":"Comfortable"},"price":{"symbol"
		 * :"£"
		 * ,"string_value":"£22.00","currency":"GBP","price_color":"BLACK","value"
		 * :22},"is_comfort":true,"links":{"_front":
		 * "https://www.blablacar.co.uk/trip-roma-vicenza-765404565","_threads":
		 * "https://api.blablacar.com/api/v2/trips/765404565-roma-vicenza/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/765404565-roma-vicenza?_locale=en_GB"
		 * },"commission":{"symbol":"£","string_value":"£3.00","currency":"GBP",
		 * "value"
		 * :3},"booking_type":"online","viaggio_rosa":false,"booking_mode"
		 * :"manual"
		 * },{"freeway":true,"distance":{"unity":"km","value":661},"departure_date"
		 * :
		 * "24/03/2017 12:00:00","permanent_id":"764924385-roma-tirolo","seats":
		 * 1
		 * ,"departure_place":{"country_code":"IT","city_name":"Rome","address":
		 * "Quartiere XXVIII Monte Sacro Alto, Roma, Italia"
		 * ,"latitude":41.94701004
		 * ,"longitude":12.549881935},"arrival_place":{"country_code"
		 * :"IT","city_name"
		 * :"Tirolo","address":"nessuna","latitude":46.688980103
		 * ,"longitude":11.160562515
		 * },"answer_delay":3,"frequency":"UNIQUE","locations_to_display"
		 * :["Rome","Tirolo","Milan"],"duration":{"unity":"s","value":25800},
		 * "price_with_commission"
		 * :{"symbol":"£","string_value":"£36.00","currency"
		 * :"GBP","price_color":
		 * "BLACK","value":36},"price_without_commission":{"symbol"
		 * :"£","string_value"
		 * :"£32.00","currency":"GBP","price_color":"BLACK","value"
		 * :32},"seats_left"
		 * :1,"car":{"comfort_nb_star":2,"model":"GOLF VI","make"
		 * :"VOLKSWAGEN","comfort"
		 * :"Normal"},"price":{"symbol":"£","string_value"
		 * :"£32.00","currency":"GBP"
		 * ,"price_color":"BLACK","value":32},"is_comfort"
		 * :false,"links":{"_front"
		 * :"https://www.blablacar.co.uk/trip-roma-tirolo-764924385","_threads":
		 * "https://api.blablacar.com/api/v2/trips/764924385-roma-tirolo/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/764924385-roma-tirolo?_locale=en_GB"
		 * },"commission":{"symbol":"£","string_value":"£4.00","currency":"GBP",
		 * "value"
		 * :4},"booking_type":"online","viaggio_rosa":false,"booking_mode"
		 * :"manual"
		 * },{"departure_meeting_point":{"country_code":"IT","city_name"
		 * :"Viterbo","full_name":"Municipio di Viterbo, Viterbo","address":
		 * "Via della Pescheria, 01100 Viterbo VT, Italy"
		 * ,"latitude":42.4172851,"name"
		 * :"Municipio di Viterbo","id":144519,"properties"
		 * :[],"longitude":12.1044078
		 * ,"tags":[]},"freeway":true,"distance":{"unity"
		 * :"km","value":440},"departure_date"
		 * :"24/03/2017 12:30:57","permanent_id"
		 * :"763715410-viterbo-verona","seats"
		 * :2,"departure_place":{"country_code"
		 * :"IT","city_name":"Viterbo","address"
		 * :"01100 Viterbo VT, Italia","latitude"
		 * :42.4206766,"longitude":12.107669
		 * },"arrival_place":{"country_code":"IT"
		 * ,"city_name":"Verona","address":
		 * "Verona VR, Italia","latitude":45.4383842
		 * ,"longitude":10.9916215},"answer_delay"
		 * :12,"frequency":"UNIQUE","locations_to_display"
		 * :["Viterbo","Verona","Brescia"
		 * ],"duration":{"unity":"s","value":18000}
		 * ,"price_with_commission":{"symbol"
		 * :"£","string_value":"£25.00","currency"
		 * :"GBP","price_color":"BLACK","value"
		 * :25},"price_without_commission":{"symbol"
		 * :"£","string_value":"£22.00",
		 * "currency":"GBP","price_color":"BLACK","value"
		 * :22},"seats_left":2,"car"
		 * :{"comfort_nb_star":3,"model":"A3","make":"AUDI"
		 * ,"comfort":"Comfortable"
		 * },"price":{"symbol":"£","string_value":"£22.00"
		 * ,"currency":"GBP","price_color"
		 * :"BLACK","value":22},"is_comfort":true,"links":{"_front":
		 * "https://www.blablacar.co.uk/trip-viterbo-verona-763715410"
		 * ,"_threads":
		 * "https://api.blablacar.com/api/v2/trips/763715410-viterbo-verona/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/763715410-viterbo-verona?_locale=en_GB"
		 * },"commission":{"symbol":"£","string_value":"£3.00","currency":"GBP",
		 * "value"
		 * :3},"booking_type":"online","viaggio_rosa":false,"booking_mode"
		 * :"manual"
		 * },{"departure_meeting_point":{"country_code":"IT","city_name"
		 * :"Viterbo","full_name":"Stazione di Viterbo, Viterbo","address":
		 * "Via Sauro Nazario, 30, 01100 Viterbo VT, Italy"
		 * ,"latitude":42.4241245
		 * ,"name":"Stazione di Viterbo","id":729030,"properties"
		 * :[],"longitude":
		 * 12.1083313,"tags":[]},"freeway":true,"distance":{"unity"
		 * :"km","value":
		 * 577},"departure_date":"24/03/2017 12:40:53","permanent_id"
		 * :"758158420-viterbo-bolzano"
		 * ,"seats":2,"departure_place":{"country_code"
		 * :"IT","city_name":"Viterbo"
		 * ,"address":"Viterbo, Viterbo","latitude":42.42421
		 * ,"longitude":12.10797
		 * },"arrival_place":{"country_code":"IT","city_name"
		 * :"Bolzano","address"
		 * :"Stazione ferroviaria, Bolzano","latitude":46.496883
		 * ,"longitude":11.358459
		 * },"answer_delay":3,"frequency":"UNIQUE","locations_to_display"
		 * :["Viterbo","Bolzano"],"duration":{"unity":"s","value":23400},
		 * "price_with_commission"
		 * :{"symbol":"£","string_value":"£36.00","currency"
		 * :"GBP","price_color":
		 * "BLACK","value":36},"price_without_commission":{"symbol"
		 * :"£","string_value"
		 * :"£32.00","currency":"GBP","price_color":"BLACK","value"
		 * :32},"seats_left"
		 * :2,"car":{"comfort_nb_star":2,"model":"GENESIS","make"
		 * :"HYUNDAI","comfort"
		 * :"Normal"},"price":{"symbol":"£","string_value":"£32.00"
		 * ,"currency":"GBP"
		 * ,"price_color":"BLACK","value":32},"is_comfort":false
		 * ,"links":{"_front"
		 * :"https://www.blablacar.co.uk/trip-viterbo-bolzano-758158420"
		 * ,"_threads":
		 * "https://api.blablacar.com/api/v2/trips/758158420-viterbo-bolzano/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/758158420-viterbo-bolzano?_locale=en_GB"
		 * },"arrival_meeting_point":{"country_code":"IT","city_name":"Bolzano",
		 * "full_name":"Stazione ferroviaria, Bolzano","address":
		 * "Piazza della Stazione, 1, 39100 Bolzano, Provincia di Bolzano - Alto Adige, Italia"
		 * ,
		 * "latitude":46.496883,"name":"Stazione ferroviaria","id":1559,"properties"
		 * :[],"longitude":11.358459,"tags":[]},"commission":{"symbol":"£",
		 * "string_value"
		 * :"£4.00","currency":"GBP","value":4},"booking_type":"online"
		 * ,"viaggio_rosa"
		 * :false,"booking_mode":"manual"},{"freeway":true,"distance"
		 * :{"unity":"km"
		 * ,"value":586},"departure_date":"24/03/2017 16:30:49","permanent_id"
		 * :"765605015-roma-trento"
		 * ,"seats":3,"departure_place":{"country_code":"IT"
		 * ,"city_name":"Rome","address"
		 * :"Piazzale Flaminio, 00196 Roma, Italia",
		 * "latitude":41.9118797,"longitude"
		 * :12.4756918},"arrival_place":{"country_code"
		 * :"IT","city_name":"Trento"
		 * ,"address":"Stazione ferroviaria, Trento","latitude"
		 * :46.072057,"longitude"
		 * :11.119602},"frequency":"UNIQUE","locations_to_display"
		 * :["Rome","Trento"
		 * ],"duration":{"unity":"s","value":25200},"price_with_commission"
		 * :{"symbol"
		 * :"£","string_value":"£36.00","currency":"GBP","price_color":
		 * "BLACK","value"
		 * :36},"price_without_commission":{"symbol":"£","string_value"
		 * :"£32.00",
		 * "currency":"GBP","price_color":"BLACK","value":32},"seats_left"
		 * :3,"car"
		 * :{"comfort_nb_star":4,"model":"508","make":"PEUGEOT","comfort"
		 * :"Luxury"
		 * },"price":{"symbol":"£","string_value":"£32.00","currency":"GBP"
		 * ,"price_color"
		 * :"BLACK","value":32},"is_comfort":false,"links":{"_front"
		 * :"https://www.blablacar.co.uk/trip-roma-trento-765605015","_threads":
		 * "https://api.blablacar.com/api/v2/trips/765605015-roma-trento/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/765605015-roma-trento?_locale=en_GB"
		 * },"arrival_meeting_point":{"country_code":"IT","city_name":"Trento",
		 * "full_name"
		 * :"Stazione ferroviaria, Trento","address":"Piazza Dante, 38121 Trento"
		 * ,
		 * "latitude":46.072057,"name":"Stazione ferroviaria","id":1867,"properties"
		 * :[],"longitude":11.119602,"tags":[]},"commission":{"symbol":"£",
		 * "string_value"
		 * :"£4.00","currency":"GBP","value":4},"booking_type":"online"
		 * ,"viaggio_rosa"
		 * :false,"booking_mode":"auto"},{"freeway":true,"distance"
		 * :{"unity":"km"
		 * ,"value":631},"departure_date":"25/03/2017 09:00:00","permanent_id"
		 * :"766369705-via-alberto-lionello-201-00139-roma-rm-italia-bolzano"
		 * ,"seats":2,"departure_place":{"country_code":"IT","city_name":
		 * "Via Alberto Lionello, 201, 00139 Roma RM, Italia"
		 * ,"address":"Via Alberto Lionello, 201, 00139 Roma RM, Italia"
		 * ,"latitude"
		 * :41.9718671,"longitude":12.5403218},"arrival_place":{"country_code"
		 * :"IT"
		 * ,"city_name":"Bolzano","address":"Piazza della Vittoria, Bolzano"
		 * ,"latitude"
		 * :46.5008315,"longitude":11.3423534},"answer_delay":12,"frequency"
		 * :"UNIQUE"
		 * ,"locations_to_display":["Bolzano"],"duration":{"unity":"s","value"
		 * :22200
		 * },"price_with_commission":{"symbol":"£","string_value":"£35.00",
		 * "currency"
		 * :"GBP","price_color":"BLACK","value":35},"price_without_commission"
		 * :{"symbol"
		 * :"£","string_value":"£31.00","currency":"GBP","price_color":
		 * "BLACK","value"
		 * :31},"seats_left":2,"car":{"comfort_nb_star":1,"model":
		 * "MiTo","make":"ALFA ROMEO"
		 * ,"comfort":"Basic"},"price":{"symbol":"£","string_value"
		 * :"£31.00","currency"
		 * :"GBP","price_color":"BLACK","value":31},"is_comfort"
		 * :true,"links":{"_front":
		 * "https://www.blablacar.co.uk/trip-via-alberto-lionello-201-00139-roma-rm-italia-bolzano-766369705"
		 * ,"_threads":
		 * "https://api.blablacar.com/api/v2/trips/766369705-via-alberto-lionello-201-00139-roma-rm-italia-bolzano/threads?_locale=en_GB"
		 * ,"_self":
		 * "https://api.blablacar.com/api/v2/trips/766369705-via-alberto-lionello-201-00139-roma-rm-italia-bolzano?_locale=en_GB"
		 * },"arrival_meeting_point":{"country_code":"IT","city_name":"Bolzano",
		 * "full_name":"Piazza della Vittoria, Bolzano","address":
		 * "Piazza della Vittoria, 38 39100 Bolzano BZ Italia"
		 * ,"latitude":46.5008315
		 * ,"name":"Piazza della Vittoria","id":1557,"properties"
		 * :[],"longitude":
		 * 11.3423534,"tags":[]},"commission":{"symbol":"£","string_value"
		 * :"£4.00"
		 * ,"currency":"GBP","value":4},"booking_type":"online","viaggio_rosa"
		 * :false,"booking_mode":"manual"}],"tracktor":
		 * "eNp1U8Fu2zAM/Redo0CyJcvuH/Q69DYMhmLTKTFZCmQ561r030dHsZOl28nke3p8FEV/sCGGkT2xb2G0bHfJ2i7MPsXf9O2BqOeXlXA2YZoXUMl9IwpTl3qlgj9eOVnsVVOVlV65U4QOJ2IG6ybYsRSo6ksET8GS/dOQ4Du7ai+MMqYpM3FnJveykEbV1YV5tOptgvYAR/Tsyc/OXRHw/ZpfvNsI0+zSRE4F1Yl4agdi2xPEjtpkT4QO6BLEjNsu4ZkKbT6ZJP0Hew1z/NvygtxZniJ20I727QG4KTLwc1zzCezSXE5+hRG2k2e0xyOGNobJrljEHmiW4xBiWjF4o7sg+A42j9ewPERObNfBKbUJx43vbHwsMl3iPJ9ljixDbYg9RCLs1BEUbY/z1m0YhglIJXbM4YgUSfF5nTH2dOw7M5UqtDKl4A77wHvkYUpo+Rli8MtWmkqLqjG15rROlqd1dQivG6FIeEYa/yHwQ3Dv1l85Wk9J3BeNEkpX11pnGrR/zyaqKVS5mWAMLgtKI7WSN5NbX7qWulbFf/wroYX82jP9Go0RmjSWW3eAmAJdPHhwLvBCSC6ELJssiyPHZB2dXCv/+PwDHFM5ag=="
		 * ,"top_trips":[],"lowest_price":16,"links":{"_front":
		 * "https://www.blablacar.co.uk/search?fn=Roma&tn=Trento"
		 * ,"_self":"https://api.blablacar.com/api/v2/trips?_locale=en_GB"
		 * },"full_trips_count"
		 * :1,"savings":120,"recommended_price":40,"facets":[
		 * {"name":"date","type"
		 * :"custom","items":[{"is_selected":false,"count":3
		 * ,"value":"23/03/2017 16:06:53"
		 * },{"is_selected":false,"count":6,"value"
		 * :"24/03/2017 16:06:53"},{"is_selected"
		 * :false,"count":2,"value":"25/03/2017 16:06:53"
		 * },{"is_selected":false,"count"
		 * :6,"value":"26/03/2017 16:06:53"},{"is_selected"
		 * :false,"count":3,"value"
		 * :"27/03/2017 16:06:53"},{"is_selected":false,"count"
		 * :2,"value":"28/03/2017 16:06:53"
		 * },{"is_selected":false,"count":2,"value"
		 * :"02/04/2017 16:06:53"},{"is_selected"
		 * :false,"count":1,"value":"05/04/2017 16:06:53"
		 * },{"is_selected":false,"count"
		 * :1,"value":"08/04/2017 16:06:53"},{"is_selected"
		 * :false,"count":3,"value"
		 * :"09/04/2017 16:06:53"},{"is_selected":false,"count"
		 * :1,"value":"12/04/2017 16:06:53"
		 * },{"is_selected":false,"count":1,"value"
		 * :"14/04/2017 16:06:53"},{"is_selected"
		 * :false,"count":1,"value":"16/04/2017 16:06:53"
		 * },{"is_selected":false,"count"
		 * :3,"value":"18/04/2017 16:06:53"},{"is_selected"
		 * :false,"count":1,"value"
		 * :"21/04/2017 16:06:53"},{"is_selected":false,"count"
		 * :1,"value":"24/04/2017 16:06:53"
		 * },{"is_selected":false,"count":1,"value"
		 * :"28/04/2017 16:06:53"},{"is_selected"
		 * :false,"count":1,"value":"05/05/2017 16:06:53"
		 * },{"is_selected":false,"count"
		 * :1,"value":"08/05/2017 16:06:53"},{"is_selected"
		 * :false,"count":1,"value"
		 * :"11/05/2017 16:06:53"},{"is_selected":false,"count"
		 * :1,"value":"27/07/2017 16:06:53"
		 * }]},{"name":"hour","type":"custom","items"
		 * :[{"is_selected":true,"count"
		 * :1,"value":6},{"is_selected":false,"count"
		 * :1,"value":7},{"is_selected"
		 * :false,"count":1,"value":8},{"is_selected"
		 * :false,"count":1,"value":9},
		 * {"is_selected":false,"count":1,"value":10}
		 * ,{"is_selected":false,"count"
		 * :1,"value":11},{"is_selected":false,"count"
		 * :1,"value":12},{"is_selected"
		 * :false,"count":1,"value":13},{"is_selected"
		 * :false,"count":1,"value":14
		 * },{"is_selected":false,"count":1,"value":15
		 * },{"is_selected":false,"count"
		 * :1,"value":16},{"is_selected":false,"count"
		 * :1,"value":17},{"is_selected"
		 * :false,"count":1,"value":18},{"is_selected"
		 * :false,"count":1,"value":19
		 * },{"is_selected":false,"count":1,"value":20
		 * },{"is_selected":false,"count"
		 * :1,"value":21},{"is_selected":true,"count"
		 * :1,"value":22}]},{"name":"price"
		 * ,"type":"custom","items":[{"is_selected"
		 * :true,"count":1,"value":16},{"is_selected"
		 * :false,"count":1,"value":17}
		 * ,{"is_selected":false,"count":1,"value":18
		 * },{"is_selected":false,"count"
		 * :1,"value":19},{"is_selected":false,"count"
		 * :1,"value":20},{"is_selected"
		 * :false,"count":1,"value":21},{"is_selected"
		 * :false,"count":1,"value":22
		 * },{"is_selected":false,"count":1,"value":23
		 * },{"is_selected":false,"count"
		 * :1,"value":24},{"is_selected":false,"count"
		 * :1,"value":25},{"is_selected"
		 * :false,"count":1,"value":26},{"is_selected"
		 * :false,"count":1,"value":27
		 * },{"is_selected":false,"count":1,"value":28
		 * },{"is_selected":false,"count"
		 * :1,"value":29},{"is_selected":false,"count"
		 * :1,"value":30},{"is_selected"
		 * :false,"count":1,"value":31},{"is_selected"
		 * :false,"count":1,"value":32
		 * },{"is_selected":false,"count":1,"value":33
		 * },{"is_selected":false,"count"
		 * :1,"value":34},{"is_selected":true,"count"
		 * :1,"value":35}]},{"name":"photo"
		 * ,"type":"radio","items":[{"is_selected"
		 * :false,"count":36,"value":"true"
		 * },{"is_selected":false,"count":42,"value"
		 * :"false"}]},{"name":"experience"
		 * ,"type":"radio","items":[{"is_selected"
		 * :false,"count":11,"value":"4"},
		 * {"is_selected":false,"count":21,"value"
		 * :"3"},{"is_selected":false,"count"
		 * :25,"value":"2"},{"is_selected":false
		 * ,"count":29,"value":"1"},{"is_selected":true,"count":42}]}]}
		 */

		message1.setText("<a href='" + link + "'>RIDE 1</a>");
		try {
			sendMessage(message1); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

		// sendMessage.setReplyMarkup(keyboard);
		// sendMessage(sendMessage);
	}

	/*
	 * bot.sendMessage(chat_id=chat_id, ... text='<b>bold</b> <i>italic</i> <a
	 * href="http://google.com">link</a>.', ...
	 * parse_mode=telegram.ParseMode.HTML)
	 */
	private void sendMessageDefault(Message message, ReplyKeyboard keyboard,
			String text) throws TelegramApiException {
		SendMessage sendMessage = new SendMessage().setChatId(
				message.getChatId().toString()).enableMarkdown(true);
		
			sendMessage.setText(text);
			sendMessage.setReplyMarkup(keyboard);
			sendMessage(sendMessage);

		

	}

	private void sendMessageDefault(Message message, String text)
			throws TelegramApiException {
		sendMessageDefault(message, null, text);
	}

	private void sendVenueDefault(Message message, Float latitude,
			Float longitude) throws TelegramApiException {
		SendVenue sendVenue = new SendVenue().setChatId(message.getChatId()
				.toString());
		sendVenue.setLatitude(latitude);
		sendVenue.setLongitude(longitude);

		sendVenue(sendVenue);
	}

	// endregion utilities

}