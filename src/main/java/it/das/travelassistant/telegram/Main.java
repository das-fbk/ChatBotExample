package it.das.travelassistant.telegram;

import it.das.travelassistant.telegram.updateshandlers.TravelAssistantBot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.BotSession;

public class Main {

	public static void main(String[] args) {

		if (args == null || args.length != 2) {
			System.err.println("Require 2 parameters: botName botToken");
			System.exit(1);
		}

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			BotSession session = telegramBotsApi
					.registerBot(new TravelAssistantBot(args[0].trim(), args[1]
							.trim()));

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
