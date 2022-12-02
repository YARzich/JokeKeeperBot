package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.DB.RepositoryManager;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessing {

    @Getter
    private static RepositoryManager repositoryManager;

    @Autowired
    public MessageProcessing(RepositoryManager manager) {
        repositoryManager = manager;
    }

    public void processing(ChatData chat, MessengerAPI messenger, Keyboard keyboard) {

        long chatId = chat.getChatId();
        String messageText = chat.getMessageText();

        String messageToSend;

        if(messageText.contains("/send") && chatId == BotVariables.TG_ADMIN_CHATID){
            String sendToAll = messageText.substring(messageText.indexOf(" "));
            Iterable<User> users = repositoryManager.getUserRepository().findAll();

            users.forEach(user -> messenger.sendMessage(chatId, sendToAll, new BaseKeyboard()));
        } else {

            if (messageText.equals("/help")) {
                messenger.sendMessage(chatId, BotVariables.HELP_TEXT, keyboard);
            } else {

                messageToSend = keyboard.checkMessage(chat);

                messenger.sendMessage(chatId, messageToSend, BotVariables.getKeyboard(chatId));
            }
        }
    }
}
