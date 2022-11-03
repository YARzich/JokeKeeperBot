package com.yaroslav.joke_keeper_bot.telegram;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;
import com.yaroslav.joke_keeper_bot.bot.MessengerAPI;
import com.yaroslav.joke_keeper_bot.bot.config.BotConfig;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TgHandler extends TelegramLongPollingBot implements MessengerAPI {

    final BotConfig botConfig;

    @Autowired
    MessageProcessing messageProcessing;

    public TgHandler(BotConfig botConfig) {
        System.out.println(2);
        this.botConfig = botConfig;

        createMenu();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBOT_TOKEN();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            ChatData chatData = new ChatData();

            chatData.setChatId(update.getMessage().getChatId());
            chatData.setFirstName(update.getMessage().getChat().getFirstName());
            chatData.setLastName(update.getMessage().getChat().getLastName());
            chatData.setUserName(update.getMessage().getChat().getUserName());
            chatData.setMessageText(update.getMessage().getText());

            messageProcessing.processing(chatData, this);
        }

    }

    private void createMenu() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/help", "Узнать, че да как тут"));
        try {
            execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(long chatId, String message, Keyboard keyboard) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);


        if(keyboard != null) {
            TgKeyboard tgKeyboard = new TgKeyboard(keyboard);
            tgKeyboard.setKeyboard(sendMessage);
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }
}
