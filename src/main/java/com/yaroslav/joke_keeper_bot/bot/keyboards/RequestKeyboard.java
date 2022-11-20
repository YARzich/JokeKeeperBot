package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.GO_BACK;

public class RequestKeyboard extends Keyboard {

    { keyboardRowsList.add(List.of(GO_BACK)); }

    @Override
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {
        String messageText = chat.getMessageText();

        if (GO_BACK.equals(messageText)) {
            messageProcessing.setKeyboard(new BaseKeyboard());
            return "Как надумаешь - пиши";
        }else if(messageText.matches("(.){1,9}")){
            return "Анекдот должен быть больше десяти символов";
        } else {
            messageProcessing.setKeyboard(new BaseKeyboard());
            messageProcessing.getRepositoryManager().addJoke(chat);
            return "Спасибо, если твоя шутка окажется достойной, то ты об этом сразу же узнаешь";
        }
    }
}
