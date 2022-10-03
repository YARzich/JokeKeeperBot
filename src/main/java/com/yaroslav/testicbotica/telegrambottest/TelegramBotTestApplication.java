package com.yaroslav.testicbotica.telegrambottest;

import com.yaroslav.testicbotica.telegrambottest.service.TelegramBot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

@SpringBootApplication
public class TelegramBotTestApplication {
	private static final Map<String, String> getenv = System.getenv();

	public static void main(String[] args) {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TelegramBot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN")));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
