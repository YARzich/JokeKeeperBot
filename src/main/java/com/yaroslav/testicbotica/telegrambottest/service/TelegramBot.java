package com.yaroslav.testicbotica.telegrambottest.service;

import com.yaroslav.testicbotica.telegrambottest.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getKey();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            switch (update.getMessage().getText()) {
                case "/start":
                    startCommand(update.getMessage().getChat().getId(), update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(update.getMessage().getChatId(), "Нихуя не отвечу, потому что.... а ваще, пиши \"/start\" что бы узнать");
            }

        }

    }

    private void startCommand(long chatId, String name) {

        String answer = "Здарова " + name + " бот нихуя не робит, возвращайся как нибудь потом, когда автор доучится ыыыы :3";
        log.info("Replied to user " + name);

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String message) {

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }
}
