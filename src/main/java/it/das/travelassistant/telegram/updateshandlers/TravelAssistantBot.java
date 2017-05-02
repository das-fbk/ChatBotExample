package it.das.travelassistant.telegram.updateshandlers;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DATEHOUR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.STARTCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ROME2RIO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEAT;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BLABLACAR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelRomeToRio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseAlternatives;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaRome2Rio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textError;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textCalculateTrip;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textChooseRomeBla;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartFrom;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartDestination;
import it.das.travelassistant.telegram.updateshandlers.messagging.Current;
import it.das.travelassistant.telegram.updateshandlers.messagging.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import utils.Rome2RioAPIWrapper;
import utils.BlaBlaCarAPIWrapper;
import utils.TripAlternativeRome2Rio;
import utils.TripAlternativeBlaBlaCar;
import eu.trentorise.smartcampus.mobilityservice.MobilityServiceException;

/**
 * Created by antbucc
 */
public class TravelAssistantBot extends TelegramLongPollingBot {

	private String token, name;
	private String proximity;
	private String timeDeparture;
	private String transportType;
	private ArrayList<Integer> userIDs;
	ArrayList<TripAlternativeRome2Rio> romeToRioAlternatives;
	ArrayList<TripAlternativeBlaBlaCar> blaBlaCarAlternatives;
	String from;
	String to;

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

		try {
			if (update.hasCallbackQuery())
				handleIncomingCallbackQuery(update.getCallbackQuery());

			if (update.hasMessage()) {
				Message message = update.getMessage();
				if (message.hasText())
					handleIncomingTextMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void handleIncomingTextMessage(Message message)
			throws TelegramApiException, MobilityServiceException,
			ExecutionException, IOException {

		Long chatId = message.getChatId();
		System.out.println("chatID: " + chatId);

		Integer userID = message.getFrom().getId();
		this.userIDs.add(userID);
		System.out.println(userID);

		switch (message.getText()) {

			case STARTCOMMAND:
				sendMessageDefault(message, keyboardStart(chatId), textStart(Current.getLanguage(chatId)));
				break;
	
			case MANUAL:
				Current.setMenu(chatId, Menu.FROM);
				sendMessageDefault(message, textStartFrom(Current.getLanguage(chatId)));
				break;

		default:
			switch (Current.getMenu(chatId)) {
			
				case START:
					sendMessageDefaultWithReply(message, keyboardStart(chatId), textError(Current.getLanguage(chatId)));
					break;

				case FROM:
					this.setStart(message.getText());
					Current.setMenu(chatId, Menu.TO);
					sendMessageDefault(message,textStartDestination(Current.getLanguage(chatId)));
	
					break;
				case TO:
					this.setDestination(message.getText());
					sendMessageDefault(message, keyboardChooseAlternatives(chatId),textChooseRomeBla(Current.getLanguage(chatId)));
					break;
				
				case SELEZIONE_SERVIZIO:
					switch(message.getText()) {
						case ROME2RIO:
							sendMessageDefault(message, keyboardCalcolaRome2Rio(chatId), textCalculateTrip(Current.getLanguage(chatId)));
							break;
						case BLABLACAR:
							sendMessageDefault(message, keyboardCalcolaBlaBlaCar(chatId), textCalculateTrip(Current.getLanguage(chatId)));
							break;
					}
					break;
				case CALCOLAROME2RIO:
						romeToRioAlternatives = new ArrayList<TripAlternativeRome2Rio>();
	
						Rome2RioAPIWrapper rome2RioWrapper = new Rome2RioAPIWrapper();

						from = this.getStart();
						to = this.getDestination();
						romeToRioAlternatives = rome2RioWrapper.getRome2RioAlternatives(from, to);
	
						if (romeToRioAlternatives.size() != 0) {
							sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, "NULL"), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), ""));
						}
					break;
				case CALCOLABLABLACAR:
					 blaBlaCarAlternatives = new ArrayList<TripAlternativeBlaBlaCar>();
					 
					 BlaBlaCarAPIWrapper blaBlaCarWrapper = new BlaBlaCarAPIWrapper();
					 
					 from = this.getStart();
					 to = this.getDestination();
					 blaBlaCarAlternatives = blaBlaCarWrapper.getBlaBlaCarAlternatives(from, to);
					 
					 if (blaBlaCarAlternatives.size() != 0) {
						 sendMessageDefault(message, keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, "NULL"), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), ""));
					}
					 
				break;
				
				case ROME2RIORESULT:
					switch(message.getText()) {
						case PRICE:
							sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), message.getText()));
							break;
						case TIME:
							sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), message.getText()));
							break;
						case CHANGES:
							sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), message.getText()));
							break;
						case DISTANCE:
							sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, message.getText()), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), message.getText()));
							break;
						default:
							break;
					}
				break;
				
				case BLABLACARRESULT:
					switch(message.getText()) {
						case DATEHOUR:
							sendMessageDefault(message,keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, message.getText()), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), message.getText()));
							break;
						case PRICE:
							sendMessageDefault(message,keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, message.getText()), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), message.getText()));
							break;
						case SEAT:
							sendMessageDefault(message,keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, message.getText()), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), message.getText()));
							break;
						default:
							break;
					}
				break;
	
					
			}
		}
	}

	private void handleIncomingCallbackQuery(CallbackQuery cbq)
			throws TelegramApiException, MobilityServiceException,
			ExecutionException {
		Message message = cbq.getMessage();

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

}