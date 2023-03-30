package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.Message;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.*;

public class RequestKeyboard extends Keyboard {

    { keyboardRowsList.add(List.of(GO_BACK)); }

    @Override
    public Message checkMessage(ChatData chat) {
        String messageText = chat.getMessageText();

        switch (messageText) {
            case GET_JOKE, LEADERBOARD, SHOW_MONEY, FORGET_JOKES, SET_JOKE -> {
                return new Message(chat.getChatId(), BotVariables.defaultMessage(), null);
            }

        }

        if (GO_BACK.equals(messageText)) {
            return new Message(chat.getChatId(), "Как надумаешь - пиши", new BaseKeyboard());
        }else if(messageText.matches("(.){1,9}")){
            return new Message(chat.getChatId(), "Анекдот должен быть больше десяти символов", null);
        } else {
            MessageProcessing.getRepositoryManager().addJoke(chat);
            return new Message(chat.getChatId(),
                    "Спасибо, если твоя шутка окажется достойной, то ты об этом сразу же узнаешь",
                    new BaseKeyboard());
        }
    }
}
