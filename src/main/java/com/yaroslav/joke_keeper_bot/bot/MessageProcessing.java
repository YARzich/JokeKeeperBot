package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.DB.RepositoryManager;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProcessing {

    @Getter
    private final RepositoryManager repositoryManager;

    @Setter
    private Keyboard keyboard = new BaseKeyboard();

    public void processing(ChatData chat, MessengerAPI messenger) {
        long chatId = chat.getChatId();
        String messageText = chat.getMessageText();

        String messageToSend;

        if(messageText.contains("/send") && chatId == BotVariables.TG_ADMIN_CHATID){
            String sendToAll = messageText.substring(messageText.indexOf(" "));
            Iterable<User> users = repositoryManager.getUserRepository().findAll();

            users.forEach(user -> messenger.sendMessage(user.getChatId(), sendToAll, new BaseKeyboard()));
        } else {

            if (messageText.equals("/help")) {
                setKeyboard(new BaseKeyboard());
                messenger.sendMessage(chatId, BotVariables.HELP_TEXT, keyboard);
            } else {

                messageToSend = keyboard.checkMessage(chat, this);

                messenger.sendMessage(chatId, messageToSend, keyboard);
            }
        }
    }

    public String defaultMessage() {
        return BotVariables.DEFAULT_MESSAGE_LIST.get((int)(Math.random() * BotVariables.DEFAULT_MESSAGE_LIST.size()));
    }
}
