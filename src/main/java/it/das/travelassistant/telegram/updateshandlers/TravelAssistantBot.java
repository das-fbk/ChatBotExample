package it.das.travelassistant.telegram.updateshandlers;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DATEHOUR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.STARTCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ROME2RIO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEATS;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BLABLACAR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.VIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelRomeToRio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseStartViaggiaTrento;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseAlternatives;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaRome2Rio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.setKeyboardJourneyOption;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelViaggiaTrento;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textError;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textChooseViaggiaTrentoStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoTrip;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textChooseViaggiaTrentoDestination;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textCalculateTrip;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textChooseRomeBla;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioArrive;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartFrom;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartDestination;
import it.das.travelassistant.telegram.updateshandlers.messagging.Current;
import it.das.travelassistant.telegram.updateshandlers.messagging.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import utils.Rome2RioAPIWrapper;
import utils.TravelsRomeToRioAfterChoose;
import utils.BlaBlaCarAPIWrapper;
import utils.GoogleAPIWrapper;
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
	private Rome2RioAPIWrapper rome2RioWrapper;
	private ArrayList<Integer> userIDs;
	ArrayList<TripAlternativeRome2Rio> romeToRioAlternatives;
	ArrayList<TripAlternativeBlaBlaCar> blaBlaCarAlternatives;
	ArrayList<TravelsRomeToRioAfterChoose> travelsRomeToRioAfterChoose;
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
					this.setStart(message.getText().trim().replaceAll(" +", ""));
					Current.setMenu(chatId, Menu.TO);
					sendMessageDefault(message,textStartDestination(Current.getLanguage(chatId)));
	
					break;
				case TO:
					this.setDestination(message.getText().trim().replaceAll(" +", ""));
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
						case VIAGGIATRENTO:
							GoogleAPIWrapper google = new GoogleAPIWrapper();
							
							setStart(google.getGoogleAutocomplete(getStart()));
							String coorstart = google.getCoordinates(getStart());
							
							setDestination(google.getGoogleAutocomplete(getDestination()));
							String coordestination = google.getCoordinates(getDestination());
							
							sendMessageDefault(message, keyboardChooseStartViaggiaTrento(chatId, coorstart, coordestination), textViaggiaTrentoTrip(Current.getLanguage(chatId), getDifferentWayTravelViaggiaTrento()));
							break;
					}
					break;
				case CALCOLAROME2RIO:
						romeToRioAlternatives = new ArrayList<TripAlternativeRome2Rio>();
	
						rome2RioWrapper = new Rome2RioAPIWrapper();

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
							//stringa con tutto il percorso che mi viene data da Martina
							
							//trento - torino
							//String all = "Train;Trenitalia Frecce;Trento;Verona Porta Nuova*Shuttle;Azienda Trasporti Verona Srl;Verona;Verona Catullo Airport*Plane;999;Verona;Turin*Train;5T Torino;Caselle Aeroporto;Torino Dora"; 
							
							//trento - roma
							//String all = "Train;Trenitalia Frecce;Trento;Roma Termini"; 
							
							//roma - milano
							//String all = "Train;Trenitalia;Roma Termini;Fiumicino Aeroporto*Plane;999;Rome;Milan Linate*Bus;Milan Metro;aeroporto di linate;c.so p.ta vittoria"; 
							
							//trento - londra
							//String all = "Train;Trenitalia Frecce;Trento;Verona Porta Nuova*Train;Trenitalia Frecce;Verona Porta Nuova;Milano Centrale*Train;Trenord;Milano Centrale;Malpensa Aeroporto Terminal 1*Plane;999;Milan Malpensa;London Gatwick*Train;Gatwick Express;Gatwick Airport;London Victoria";
							
							//londra - newyork
							String all = "Train;Heathrow Express;London Paddington;Heathrow Terminals 1-3*Plane;999;London Heathrow;Philadelphia*Train;SEPTA;Airport Terminal A;University City*Walk;999;University City;Philadelphia 30th Street Station Amtrak*Train;Amtrak Acela Express;Philadelphia 30th Street Station Amtrak;New York Penn Station*Train;Long Island Rail Road;Penn Station;Flushing Main Street*Walk;999;Flushing Main Street;NewYork–Presbyterian/Queens"; 
							
							rome2RioWrapper = new Rome2RioAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							travelsRomeToRioAfterChoose = rome2RioWrapper.getRome2RioAfterChoose(all, from, to);
							
							
							for(int i = 0;i < travelsRomeToRioAfterChoose.size(); i++) {
								travelsRomeToRioAfterChoose.get(i).setVehicle(setKeyboardJourneyOption(travelsRomeToRioAfterChoose.get(i).getVehicle()));
							}
							
							sendMessageDefault(message,keyboardRome2RioAfterChoose(chatId), textRome2RioAfterChoose(Current.getLanguage(chatId), travelsRomeToRioAfterChoose.get(0)));
							travelsRomeToRioAfterChoose.remove(0);
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
						case SEATS:
							sendMessageDefault(message,keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, message.getText()), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), message.getText()));
							break;
						default:
							break;
					}
				break;
				
				case VIAGGIATRENTODESTINATION:
					
				break;
				
				case ROME2RIOAFTERCHOOSE:
					if(travelsRomeToRioAfterChoose.size() > 0) {
						sendMessageDefault(message,keyboardRome2RioAfterChoose(chatId), textRome2RioAfterChoose(Current.getLanguage(chatId), travelsRomeToRioAfterChoose.get(0)));
						travelsRomeToRioAfterChoose.remove(0);
					}else{
						sendMessageDefault(message, textRome2RioArrive(Current.getLanguage(chatId)));
					}
				break;
	
					
			}
		}
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