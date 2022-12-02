package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.*;

public class RequestKeyboard extends Keyboard {

    { keyboardRowsList.add(List.of(GO_BACK)); }

    @Override
    public String checkMessage(ChatData chat) {
        String messageText = chat.getMessageText();

        switch (messageText) {
            case GET_JOKE, LEADERBOARD, SHOW_MONEY, FORGET_JOKES, SET_JOKE -> {
                return BotVariables.defaultMessage();
            }

        }

        if (GO_BACK.equals(messageText)) {
            BotVariables.setKeyboard(chat.getChatId(), new BaseKeyboard());
            return "Как надумаешь - пиши";
        }else if(messageText.matches("(.){1,9}")){
            return "Анекдот должен быть больше десяти символов";
        } else {
            BotVariables.setKeyboard(chat.getChatId(), new BaseKeyboard());
            MessageProcessing.getRepositoryManager().addJoke(chat);
            return "Спасибо, если твоя шутка окажется достойной, то ты об этом сразу же узнаешь";
        }
    }
}
