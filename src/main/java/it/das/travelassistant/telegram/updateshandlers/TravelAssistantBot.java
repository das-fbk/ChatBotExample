package it.das.travelassistant.telegram.updateshandlers;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.MANUAL;

import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PRICE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.CHANGES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DATEHOUR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.DISTANCE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.TIME;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BIKE;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.FLIXBUS;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.EMTMALAGA;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.STARTCOMMAND;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.ROME2RIO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.LONDON;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.PARKING;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.SEATS;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.YES;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.YESEMAIL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.NOEMAIL;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.BLABLACAR;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Commands.VIAGGIATRENTO;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelRomeToRio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardViaggiaTrentoAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardLondonAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseSendEmail;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseViaggiaTrentoRouteType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseViaggiaTrentoTransportType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseStartViaggiaTrento;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseAlternatives;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardSendEmail;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaRome2Rio;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardChooseViaggiaTrentoYesNo;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardCalcolaBlaBlaCar;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.setKeyboardJourneyOption;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardLondonResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.getDifferentWayTravelViaggiaTrento;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Keyboards.keyboardStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textError;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiTrentoAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoYesNo;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textEmtMalagaResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoTrip;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textLondonResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textParking;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textLondonAfterChoose;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textCalculateTrip;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoRouteType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textViaggiaTrentoTransportType;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textCityBike;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textParkingLeg;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textChooseRomeBla;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioArrive;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textRome2RioResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textBlaBlaCarResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textFlixbusResult;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStart;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartFrom;
import static it.das.travelassistant.telegram.updateshandlers.messagging.Texts.textStartDestination;
import it.das.travelassistant.telegram.updateshandlers.messagging.Current;
import it.das.travelassistant.telegram.updateshandlers.messagging.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import utils.Rome2RioAPIWrapper;
import utils.Flixbus_EmtMalagaAPIWrapper;
import utils.LondonAPIWrapper;
import utils.ViaggiaTrentoAPIWrapper;
import utils.TravelsRomeToRioAfterChoose;
import utils.TravelsLondonAfterChoose;
import utils.CityBikeAPIWrapper;
import utils.CityBike;
import utils.TravelsViaggiaTrentoAfterChoose;
import utils.BlaBlaCarAPIWrapper;
import utils.GoogleAPIWrapper;
import utils.TripAlternativeRome2Rio;
import utils.TripAlternativeLondon;
import utils.TravelsFlixbus_EmtMalaga;
import utils.ParkingAPIWrapper;
import utils.ParkingTrentoRovereto;
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
	private String routeType;
	private String transportTypeV;
	private Rome2RioAPIWrapper rome2RioWrapper;
	private LondonAPIWrapper londonWrapper;
	private Flixbus_EmtMalagaAPIWrapper flixbusWrapper;
	private Flixbus_EmtMalagaAPIWrapper emtMalagaWrapper;
	private ArrayList<Integer> userIDs;
	ArrayList<TripAlternativeRome2Rio> romeToRioAlternatives;
	ArrayList<TripAlternativeLondon> londonAlternatives;
	ArrayList<TravelsFlixbus_EmtMalaga> flixbusAlternatives;
	ArrayList<TravelsFlixbus_EmtMalaga> emtMalagaAlternatives;
	ArrayList<TripAlternativeBlaBlaCar> blaBlaCarAlternatives;
	ArrayList<TravelsRomeToRioAfterChoose> travelsRomeToRioAfterChoose;
	ArrayList<TravelsLondonAfterChoose> travelsLondonAfterChoose;
	ArrayList<ArrayList<TravelsViaggiaTrentoAfterChoose>> travelsViaggiaTrentoAfterChoose;
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
					sendMessageDefault(message, keyboardChooseAlternatives(chatId, getStart().toLowerCase(), getDestination().toLowerCase()),textChooseRomeBla(Current.getLanguage(chatId)));
					break;
				
				case SELEZIONE_SERVIZIO:
					
					switch(message.getText()) {
						case ROME2RIO:
							romeToRioAlternatives = new ArrayList<TripAlternativeRome2Rio>();
							
							rome2RioWrapper = new Rome2RioAPIWrapper();

							from = this.getStart();
							to = this.getDestination();
							
							romeToRioAlternatives = rome2RioWrapper.getRome2RioAlternatives(from, to);
		
							if (romeToRioAlternatives.size() != 0) {
								sendMessageDefault(message,keyboardRome2RioResult(chatId, romeToRioAlternatives, "NULL"), textRome2RioResult(Current.getLanguage(chatId), getDifferentWayTravelRomeToRio(), ""));
							}
							break;
						case BLABLACAR:
							 blaBlaCarAlternatives = new ArrayList<TripAlternativeBlaBlaCar>();
							 
							 BlaBlaCarAPIWrapper blaBlaCarWrapper = new BlaBlaCarAPIWrapper();
							 
							 from = this.getStart();
							 to = this.getDestination();
							 
							 blaBlaCarAlternatives = blaBlaCarWrapper.getBlaBlaCarAlternatives(from, to);
							 
							 if (blaBlaCarAlternatives.size() != 0) {
								 sendMessageDefault(message, keyboardBlaBlaCarResult(chatId, blaBlaCarAlternatives, "NULL"), textBlaBlaCarResult(Current.getLanguage(chatId), getDifferentWayTravelBlaBlaCar(), ""));
							}
							break;
						case VIAGGIATRENTO:
							sendMessageDefault(message, keyboardChooseViaggiaTrentoYesNo(chatId), textViaggiaTrentoYesNo(Current.getLanguage(chatId)));
							break;
						case BIKE:
							CityBikeAPIWrapper citybike = new CityBikeAPIWrapper();
							
							ArrayList <CityBike> city = new ArrayList <CityBike>();
							
							city = citybike.getCityBikeAlternatives(getStart());
							
							sendMessageDefault(message, textCityBike(Current.getLanguage(chatId), city, getStart()));
							break;
						case PARKING:
							ParkingAPIWrapper parking = new ParkingAPIWrapper();
							ArrayList <ParkingTrentoRovereto> park = new ArrayList <ParkingTrentoRovereto>();
							String help = "";
							
							if(getStart().toLowerCase().equals("trento")){
								help = "COMUNE_DI_TRENTO";
							}else if(getStart().toLowerCase().equals("rovereto")){
								help = "COMUNE_DI_ROVERETO";
							}
							
							park = parking.getParkingAlternatives(help);
							
							sendMessageDefault(message, textParking(Current.getLanguage(chatId), park, getStart()));
							
							break;
						case LONDON:
							londonAlternatives = new ArrayList<TripAlternativeLondon>();
							
							londonWrapper = new LondonAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							londonAlternatives = londonWrapper.getLondonAlternatives(from, to);
							if (londonAlternatives.size() != 0) {
								sendMessageDefault(message,keyboardLondonResult(chatId, londonAlternatives, "NULL"), textLondonResult(Current.getLanguage(chatId), londonAlternatives, ""));
							}
							break;
						case FLIXBUS:
							flixbusAlternatives = new ArrayList<TravelsFlixbus_EmtMalaga>();
							
							flixbusWrapper = new Flixbus_EmtMalagaAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							flixbusAlternatives = flixbusWrapper.getFlixbusAlternatives(from, to);
							
							if (flixbusAlternatives.size() != 0) {
								sendMessageDefault(message, textFlixbusResult(Current.getLanguage(chatId), flixbusAlternatives));
							}
							
							break;
						case EMTMALAGA:
							emtMalagaAlternatives = new ArrayList<TravelsFlixbus_EmtMalaga>();
							
							emtMalagaWrapper = new Flixbus_EmtMalagaAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							emtMalagaAlternatives = emtMalagaWrapper.getEmtMalagaAlternatives(from, to);
							
							if (emtMalagaAlternatives.size() != 0) {
								sendMessageDefault(message, textEmtMalagaResult(Current.getLanguage(chatId), emtMalagaAlternatives));
							}
							break;
					}
					break;
				case VIAGGIATRENTOSINO:
					switch(message.getText()) {
						case NO:
							routeType = "fastest";
							transportTypeV = "TRANSIT";
							
							GoogleAPIWrapper google = new GoogleAPIWrapper();
							
							setStart(google.getGoogleAutocomplete(getStart()));
							String coorstart = google.getCoordinates(getStart());
							
							setDestination(google.getGoogleAutocomplete(getDestination()));
							String coordestination = google.getCoordinates(getDestination());
							
							sendMessageDefault(message, keyboardChooseStartViaggiaTrento(chatId, coorstart, coordestination, routeType, transportTypeV), textViaggiaTrentoTrip(Current.getLanguage(chatId), getDifferentWayTravelViaggiaTrento()));
						break;
						case YES:
							sendMessageDefault(message, keyboardChooseViaggiaTrentoRouteType(chatId), textViaggiaTrentoRouteType(Current.getLanguage(chatId)));
						break;
					}
					break;
					
				case VIAGGIATRENTOROUTETYPE:
					routeType = message.getText().toLowerCase();
					sendMessageDefault(message, keyboardChooseViaggiaTrentoTransportType(chatId), textViaggiaTrentoTransportType(Current.getLanguage(chatId)));
					break;
					
				case VIAGGIATRENTOTRANSPORTTYPE:
					transportTypeV = message.getText();
					GoogleAPIWrapper google = new GoogleAPIWrapper();
					
					setStart(google.getGoogleAutocomplete(getStart()));
					String coorstart = google.getCoordinates(getStart());
					
					setDestination(google.getGoogleAutocomplete(getDestination()));
					String coordestination = google.getCoordinates(getDestination());
					
					sendMessageDefault(message, keyboardChooseStartViaggiaTrento(chatId, coorstart, coordestination, routeType, transportTypeV), textViaggiaTrentoTrip(Current.getLanguage(chatId), getDifferentWayTravelViaggiaTrento()));
					
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
							//String now = "Shuttle;Azienda Trasporti Verona Srl;Verona;Verona Catullo Airport";
							
							//trento - roma
							//String all = "Train;Trenitalia Frecce;Trento;Roma Termini"; 
							//String now = "Train;Trenitalia Frecce;Trento;Roma Termini";
							
							//roma - milano
							//String all = "Train;Trenitalia;Roma Termini;Fiumicino Aeroporto*Plane;999;Rome;Milan Linate*Bus;Milan Metro;aeroporto di linate;c.so p.ta vittoria"; 
							//String now = "Plane;999;Rome;Milan Linate";
							
							//trento - londra
							//String all = "Train;Trenitalia Frecce;Trento;Verona Porta Nuova*Train;Trenitalia Frecce;Verona Porta Nuova;Milano Centrale*Train;Trenord;Milano Centrale;Malpensa Aeroporto Terminal 1*Plane;999;Milan Malpensa;London Gatwick*Train;Gatwick Express;Gatwick Airport;London Victoria";
							//String now = "Train;Trenitalia Frecce;Trento;Verona Porta Nuova";
							
							//londra - newyork
							String all = "Train;Heathrow Express;London Paddington;Heathrow Terminals 1-3*Plane;999;London Heathrow;Philadelphia*Train;SEPTA;Airport Terminal A;University City*Walk;999;University City;Philadelphia 30th Street Station Amtrak*Train;Amtrak Acela Express;Philadelphia 30th Street Station Amtrak;New York Penn Station*Train;Long Island Rail Road;Penn Station;Flushing Main Street*Walk;999;Flushing Main Street;NewYork–Presbyterian/Queens"; 
							String now = "Train;SEPTA;Airport Terminal A;University City";
							
							//trento - vienna
							//String all = "Train;Sudtirol Alto Adige;Trento;Bozen*Bus;Helloe;Bolzano;Vienna"; 
							//String now = "Bus;Helloe;Bolzano;Vienna";
							
							rome2RioWrapper = new Rome2RioAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							travelsRomeToRioAfterChoose = rome2RioWrapper.getRome2RioAfterChoose(all,from, to);
							
						
							ArrayList <String> help = new ArrayList <String>();
							
					        StringTokenizer stk1 = new StringTokenizer(now, ";");
					            
					        while (stk1.hasMoreTokens()) {
					        	String token1 = stk1.nextToken();
					        	help.add(token1);
					        }
					        
					        int t = 0;
					        
							for(int i = 0; i < travelsRomeToRioAfterChoose.size(); i++){
								if(travelsRomeToRioAfterChoose.get(i).getVehicle().equals(help.get(0)) &&
										travelsRomeToRioAfterChoose.get(i).getAgency().equals(help.get(1)) &&
										travelsRomeToRioAfterChoose.get(i).getStart().equals(help.get(2)) &&
										travelsRomeToRioAfterChoose.get(i).getArrive().equals(help.get(3))){
									
									travelsRomeToRioAfterChoose.get(i).setVehicle(setKeyboardJourneyOption(travelsRomeToRioAfterChoose.get(i).getVehicle()));
									t = i;
								}
							}
							
							sendMessageDefault(message,keyboardRome2RioAfterChoose(chatId), textRome2RioAfterChoose(Current.getLanguage(chatId), travelsRomeToRioAfterChoose.get(t)));
							break;
					}
					break;
				case LONDONRESULT:
					switch(message.getText()) {
						case TIME:
							sendMessageDefault(message,keyboardLondonResult(chatId, londonAlternatives, message.getText()), textLondonResult(Current.getLanguage(chatId), londonAlternatives, message.getText()));
							break;
						case CHANGES:
							sendMessageDefault(message,keyboardLondonResult(chatId, londonAlternatives, message.getText()),  textLondonResult(Current.getLanguage(chatId), londonAlternatives, message.getText()));
							break;
						default:
							//String all = "walking;SE15 1AA;Peckham Rye Station*national-rail;Peckham Rye Station;Kentish Town Station*walking;Kentish Town Station;NW5 1AA";
							
							String all = "walking;SE15 1AA;Peckham Library*bus;Peckham Library;Elephant & Castle / New Kent Road*walking;Elephant & Castle / New Kent Road;Elephant & Castle Rail Station*national-rail;Elephant & Castle Rail Station;Kentish Town Station*walking;Kentish Town Station;NW5 1AA";
							
							londonWrapper = new LondonAPIWrapper();
							
							from = this.getStart();
							to = this.getDestination();
							
							travelsLondonAfterChoose = londonWrapper.getLondonAfterChoose(all, from, to);
							
							for(int i = 0;i < travelsLondonAfterChoose.size(); i++) {
								travelsLondonAfterChoose.get(i).setVehicle(setKeyboardJourneyOption(travelsLondonAfterChoose.get(i).getVehicle()));
							}
							
							sendMessageDefault(message,keyboardLondonAfterChoose(chatId), textLondonAfterChoose(Current.getLanguage(chatId), travelsLondonAfterChoose.get(0)));
							travelsLondonAfterChoose.remove(0);
							
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
					if(message.getText().substring(message.getText().length() - 1).equals("h")) {
						
						//String now = "CAR;999;Via Giovanni Segantini;Piazza Fiera - P4";
						
						String now = "BICYCLE;999;Via Guardini fr. civ. 75;Via Fogazzaro / Via Gocciadoro";
						
						//Trento - rovereto
						//String now = "999;999;sidewalk;Trento FS";
						//ravina -  trento
						//String now = "12;05R;Povo Piazza Manci;Piazza Dante \"Dogana\"";
						//ravina - trento
						//
						//Trento - roveretoString now = "12;05R;Borino;Piazza Dante \"Dogana\"";
						//String now = "16;03A_Rov;Stazione Fs;Corso Rosmini Via Savioli";
						
						
						ViaggiaTrentoAPIWrapper viaggiaTrentoAPIWrapper = new ViaggiaTrentoAPIWrapper();
						
						travelsViaggiaTrentoAfterChoose = new ArrayList<ArrayList<TravelsViaggiaTrentoAfterChoose>> ();
				        
						ArrayList <String> help = new ArrayList <String>();
				        	
						StringTokenizer stk1 = new StringTokenizer(now, ";");
				       
				       while (stk1.hasMoreTokens()) {
				            String token1 = stk1.nextToken();
							help.add(token1);
				        }
				       
				       if(help.get(0).equals("CAR") && help.get(1).equals("999")){
				    	   GoogleAPIWrapper gooogle = new GoogleAPIWrapper();
				    	   
				    	   ArrayList <String> latLong = new ArrayList <String>();
				    	   
				    	   latLong = gooogle.getCoordinatesParking(help.get(3));
				    	   
				    	   Double latitudineMax = Double.parseDouble(latLong.get(0).substring(0, latLong.get(0).indexOf('.') + 3)) + 0.01;
				    	   Double longitudineMax = Double.parseDouble(latLong.get(1).substring(0, latLong.get(1).indexOf('.') + 3)) + 0.01;
				    	   Double latitudineMin = Double.parseDouble(latLong.get(0).substring(0, latLong.get(0).indexOf('.') + 3)) - 0.01;
				    	   Double longitudineMin = Double.parseDouble(latLong.get(1).substring(0, latLong.get(1).indexOf('.') + 3)) - 0.01;
				    	   
				    	   ParkingAPIWrapper parking = new ParkingAPIWrapper();
				    	   
							ArrayList <ParkingTrentoRovereto> park = new ArrayList <ParkingTrentoRovereto>();
							ArrayList <ParkingTrentoRovereto> park2 = new ArrayList <ParkingTrentoRovereto>();
							
							String helpp = "";
							
							if(getStart().toLowerCase().contains("trento")){
								helpp = "COMUNE_DI_TRENTO";
							}else if(getStart().toLowerCase().contains("rovereto")){
								helpp = "COMUNE_DI_ROVERETO";
							}
							
							park = parking.getParkingAlternatives(helpp);
							
							for(int i = 0;i < park.size();i++){
								
					    		latLong = gooogle.getCoordinatesParking(park.get(i).getDescription());
					    		if(latLong.size() == 2){
					    			if(Double.parseDouble(latLong.get(0)) < latitudineMax && Double.parseDouble(latLong.get(0)) > latitudineMin &&
											Double.parseDouble(latLong.get(1)) < longitudineMax && Double.parseDouble(latLong.get(1)) > longitudineMin){
											park2.add(park.get(i));
										}
					    		}
								
							}
							
							sendMessageDefault(message, keyboardViaggiaTrentoAfterChoose(chatId), textParking(Current.getLanguage(chatId), park2, getStart()));
				    	   
				       }else if(help.get(0).equals("BICYCLE") && help.get(1).equals("999")){
				    	   GoogleAPIWrapper gooogle = new GoogleAPIWrapper();
				    	   
				    	   ArrayList <String> latLong = new ArrayList <String>();
				    	   
				    	   latLong = gooogle.getCoordinatesParking(help.get(3));
				    	   
				    	   Double latitudineMax = Double.parseDouble(latLong.get(0).substring(0, latLong.get(0).indexOf('.') + 3)) + 0.01;
				    	   Double longitudineMax = Double.parseDouble(latLong.get(1).substring(0, latLong.get(1).indexOf('.') + 3)) + 0.01;
				    	   Double latitudineMin = Double.parseDouble(latLong.get(0).substring(0, latLong.get(0).indexOf('.') + 3)) - 0.01;
				    	   Double longitudineMin = Double.parseDouble(latLong.get(1).substring(0, latLong.get(1).indexOf('.') + 3)) - 0.01;
				    	   
				    	   System.out.println(latitudineMax);
				    	   System.out.println(longitudineMax);
				    	   System.out.println(latitudineMin);
				    	   System.out.println(longitudineMin);
				    	   
				    	   CityBikeAPIWrapper citybike = new CityBikeAPIWrapper();
							
							ArrayList <CityBike> city = new ArrayList <CityBike>();
							ArrayList <CityBike> city2 = new ArrayList <CityBike>();
							
							city = citybike.getCityBikeAlternatives(getStart());
							
							
							for(int i = 0;i < city.size();i++){
								
					    		latLong = gooogle.getCoordinatesParking(city.get(i).getStreet());
					    		
					    		if(latLong.size() == 2){
					    			if(Double.parseDouble(latLong.get(0)) < latitudineMax && Double.parseDouble(latLong.get(0)) > latitudineMin &&
											Double.parseDouble(latLong.get(1)) < longitudineMax && Double.parseDouble(latLong.get(1)) > longitudineMin){
					    					city2.add(city.get(i));
										}
					    		}
								
							}
							
							sendMessageDefault(message, keyboardViaggiaTrentoAfterChoose(chatId), textCityBike(Current.getLanguage(chatId), city, getStart()));
							
				       }else{
				    	   travelsViaggiaTrentoAfterChoose.add(viaggiaTrentoAPIWrapper.getViaggiaTrentoAfterChoose(help.get(0), help.get(1), help.get(2), help.get(3)));
					       sendMessageDefault(message,keyboardViaggiaTrentoAfterChoose(chatId), textViaggiTrentoAfterChoose(Current.getLanguage(chatId), travelsViaggiaTrentoAfterChoose.get(0)));
				       }
				       
					}else{
						sendMessageDefault(message, keyboardChooseSendEmail(chatId), "Do you wanna get a reminder by email?");	
					}
				break;
				
				case ROME2RIOAFTERCHOOSE:
						sendMessageDefault(message, keyboardChooseSendEmail(chatId), "Do you wanna get a reminder by email?");
				break;
				
				case LONDONAFTERCHOOSE:
					if(travelsLondonAfterChoose.size() > 0) {
						sendMessageDefault(message,keyboardLondonAfterChoose(chatId), textLondonAfterChoose(Current.getLanguage(chatId), travelsLondonAfterChoose.get(0)));
						travelsLondonAfterChoose.remove(0);
					}else{
						sendMessageDefault(message, keyboardChooseSendEmail(chatId), "Do you wanna get a reminder by email?");
					}
				break;
				
				case EMAILCHOOSE:
					switch(message.getText()) {
					case YESEMAIL:
						sendMessageDefault(message, keyboardSendEmail(chatId), "Please insert your email address");
						break;
					case NOEMAIL:
						sendMessageDefault(message, textRome2RioArrive(Current.getLanguage(chatId)));
						break;
					default:
						//bisogna salvare tutto il viaggio per poi mandarlo per il momento io semplicemnte uso quella di Rome2Rio
						
						String emailMessage = "";
						
				        for(int i = 0; i < travelsRomeToRioAfterChoose.size(); i++){
				        	
				        	emailMessage += travelsRomeToRioAfterChoose.get(i).getPosition()+" LEG\n"+
				        	    	"     From "+travelsRomeToRioAfterChoose.get(i).getStart()+"\n"+
				        	    	"     To "+travelsRomeToRioAfterChoose.get(i).getArrive()+"\n";
				        	    	String durationString = "";
				        	    	int rest = travelsRomeToRioAfterChoose.get(i).getDuration().intValue() % 60;
				        			int hour = travelsRomeToRioAfterChoose.get(i).getDuration().intValue() / 60;
				        			if(rest<10) {
				        				durationString = hour+".0"+rest+" h";
				        			}else {
				        				durationString = hour+"."+rest+" h";
				        			}
				        			emailMessage+="     \u23F3 "+durationString+"\n"+
				        			"     \u21e5 "+travelsRomeToRioAfterChoose.get(i).getDistance()+" Km"+"\n";
				        			if(travelsRomeToRioAfterChoose.get(i).getPrice() != -1) {
				        				emailMessage +="     \uD83D\uDCB0 "+travelsRomeToRioAfterChoose.get(i).getPrice()+" \u20ac"+"\n";
				        			}
				        			emailMessage +="     "+travelsRomeToRioAfterChoose.get(i).getVehicle();
				        			if(!travelsRomeToRioAfterChoose.get(i).getAgency().equals("999")) {
				        				emailMessage+=" - "+travelsRomeToRioAfterChoose.get(i).getAgency();
				        			}
				        			emailMessage +="\n\n";		
				        }
				        emailMessage +="\nThis is an automatically generated email, please don't reply\n\n";
						sendMessageDefault(message, "sending...");
						
						sendEmail(message.getText(), emailMessage, message);
						
						break;
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
	
	private void sendEmail(String email, String emailMessage, Message message){
		
		try{
		 	final String username = "AtlasBotAtlas@gmail.com";
	        final String password = "atlasbot";
	        // Get a Properties object to set the mailing configuration
	        // parameters
	        Properties props = System.getProperties();
	        props.setProperty("mail.smtp.host", "smtp.gmail.com");
	        props.setProperty("mail.smtp.port", "465");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put( "mail.debug", "true" );
	        //We create the session object with the authentication information
	        Session session = Session.getDefaultInstance(props, new Authenticator(){
	            @Override
	            protected PasswordAuthentication
	            getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	   
	        //Create a new message
	        MimeMessage msg = new MimeMessage(session);
	
	        //Set the FROM and TO fields –
	        msg.setFrom(new InternetAddress(username + ""));
	        
	        //sostituire la mia email con "email"
	        msg.setRecipients(MimeMessage.RecipientType.TO,
	        InternetAddress.parse(email,false));
	        msg.setSubject("Atlas Reminder");
	        
	        msg.setText("REMINDER\n"+new Date()+ "\n\n"+emailMessage);
	        msg.setSentDate(new Date());
	
	        System.out.println("\nTrying to send email...\n");
	
	        //We create the transport object to actually send the e-mail
	        Transport transport = session.getTransport("smtps");
	        transport.connect ("smtp.gmail.com", 465, username, password);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        System.out.println("\nEmail sent!\n");
	        sendMessageDefault(message, "The email has been sent\n\uD83C\uDFC1");
	    
		}catch(Exception e){
	        e.printStackTrace();
	    }
		
	}
	

}