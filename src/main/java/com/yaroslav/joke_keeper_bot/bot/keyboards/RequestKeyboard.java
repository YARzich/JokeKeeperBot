package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.ArrayList;
import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.GO_BACK;

public class RequestKeyboard extends Keyboard{
    {
        List<String> row = new ArrayList<>();
        row.add(BotVariables.GO_BACK);
        keyboardRowsList.add(row);
    }

    @Override
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {
        String messageText = chat.getMessageText();

        switch (messageText) {

            case GO_BACK -> {
                messageProcessing.setKeyboard(new BaseKeyboard());
                return "Как надумаешь - пиши";
            }
            default -> {
                messageProcessing.setKeyboard(new BaseKeyboard());
                messageProcessing.getRepositoryManager().addJoke(chat);
                return "Спасибо, если твоя шутка окажется достойной, то ты об этом сразу же узнаешь";
            }

        }
    }
}
