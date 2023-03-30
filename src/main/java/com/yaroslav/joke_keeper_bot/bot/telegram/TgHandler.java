package com.yaroslav.joke_keeper_bot.bot.telegram;

import com.yaroslav.joke_keeper_bot.bot.*;
import com.yaroslav.joke_keeper_bot.bot.DB.RepositoryManager;
import com.yaroslav.joke_keeper_bot.bot.DB.services.UserService;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static com.yaroslav.joke_keeper_bot.bot.config.BotConfig.BOT_NAME;
import static com.yaroslav.joke_keeper_bot.bot.config.BotConfig.BOT_TOKEN;

@Slf4j
@Component
@AllArgsConstructor
public class TgHandler extends TelegramLongPollingBot implements MessengerAPI {
    {
        createMenu();
    }

    private final MessageProcessing messageProcessing;

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
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

            MessageProcessing.getRepositoryManager().registerUser(chatData);

            messageProcessing.processing(chatData, this, MessageProcessing.getRepositoryManager()
                    .getUserService().getKeyboard(chatData.getChatId()));
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChat().getId();

            if(callbackData.equals("rating_gpt_button")) {

            }
        }
    }

    private void createMenu() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/help", "Узнать, че да как тут"));
        try {
            execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bots command list: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getMessage());

        if(message.getKeyboard() != null) {
            sendMessage.setReplyMarkup(new TgKeyboard(message.getKeyboard()).getKeyboard());
            MessageProcessing.getRepositoryManager().getUserService().updateUser(message.getChatId(), message.getKeyboard());
         }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }
}
