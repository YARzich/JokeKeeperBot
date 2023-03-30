package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.DB.RepositoryManager;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.JokeAnalysisKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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

        if(messageText.contains("/send") && chatId == BotVariables.TG_ADMIN_CHATID){
            String sendToAll = messageText.substring(messageText.indexOf(" "));
            Iterable<User> users = repositoryManager.getUserRepository().findAll();

            users.forEach(user -> messenger.sendMessage(new Message(chatId, sendToAll, null)));
        } else {
            if (messageText.equals("/help")) {
                messenger.sendMessage(new Message(chatId, BotVariables.HELP_TEXT, new BaseKeyboard()));
            } else {
                messenger.sendMessage(keyboard.checkMessage(chat));
            }
        }
    }
}
