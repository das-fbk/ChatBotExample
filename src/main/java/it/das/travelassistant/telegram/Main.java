package it.das.travelassistant.telegram;

import it.das.travelassistant.telegram.updateshandlers.TravelAssistantBot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.BotSession;

public class Main {

	public static void main(String[] args) {

		
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			BotSession session = telegramBotsApi.registerBot(new TravelAssistantBot("ProvaDiMichaelViaggiaTrento_bot", "354712353:AAE8_QGWq1G4kpk3XrhZTSzGSISBoiOxcsU"));

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
