package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.*;

public class BaseKeyboard extends Keyboard {

    { keyboardRowsList.add(List.of(GET_JOKE, SET_JOKE)); }

    @Override
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {

        String messageText = chat.getMessageText();

        switch (messageText) {
            case "/start" -> {
                messageProcessing.getRepositoryManager().registerUser(chat);
                return BotVariables.START_MESSAGE;
            }
            case "/help" -> {
                messageProcessing.setKeyboard(new BaseKeyboard());
                return BotVariables.HELP_TEXT;
            }
            case "/cho" -> {
                if(chat.getChatId() == TG_ADMIN_CHATID){
                    messageProcessing.setKeyboard(new JokeAnalysisKeyboard());

                    ChatData chatData = messageProcessing.getRepositoryManager().getVerifiableJoke();
                    return chatData == null?
                            "Анекдотов пока не поступало":
                            chatData.getMessageText() + "\n" + chatData.getFirstName() + chatData.getLastName();
                }
                return "ниче";
            }
            case GET_JOKE -> {
                messageProcessing.setKeyboard(new BaseKeyboard());

                ChatData chatData = messageProcessing.getRepositoryManager().getJoke(chat);

                return chatData.getMessageText() + "\n" + chatData.getFirstName() + chatData.getLastName();
            }
            case SET_JOKE -> {
                messageProcessing.setKeyboard(new RequestKeyboard());
                return REQUEST_JOKE;
            }
            default -> {
                return messageProcessing.defaultMessage();
            }
        }
    }
}
