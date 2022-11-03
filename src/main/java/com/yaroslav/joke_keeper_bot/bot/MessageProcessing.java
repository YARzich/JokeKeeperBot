package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.DB.RepositoryManager;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessing {

    @Getter
    @Autowired
    RepositoryManager repositoryManager;

    @Setter
    Keyboard keyboard;

    MessageProcessing() {
        keyboard = new BaseKeyboard();
    }

    public void processing(ChatData chat, MessengerAPI messenger) {

        long chatId = chat.getChatId();
        String messageText = chat.getMessageText();

        String messageToSend;

        if(messageText.contains("/send") && chatId == BotVariables.TG_ADMIN_CHATID){
            String sendToAll = messageText.substring(messageText.indexOf(" "));
            Iterable<User> users = repositoryManager.getUserRepository().findAll();

            users.forEach(user -> messenger.sendMessage(user.getChatId(), sendToAll, new BaseKeyboard()));
        }   

        messageToSend = keyboard.checkMessage(chat, this);

        messenger.sendMessage(chatId, messageToSend, keyboard);
    }

    public String defaultMessage() {
        return BotVariables.defaultMessageList.get((int)(Math.random() * BotVariables.defaultMessageList.size()));
    }
}
