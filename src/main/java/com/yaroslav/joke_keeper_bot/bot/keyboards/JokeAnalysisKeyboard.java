package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.JOKEISGOVNO;
import static com.yaroslav.joke_keeper_bot.bot.BotVariables.JOKE_GENRES;

public class JokeAnalysisKeyboard extends Keyboard{

    {
        // Количество кнопок на строке
        int lineSize = 3;

        // Идём по списку, шаг - количество кнопок
        for (int i = 0; i < JOKE_GENRES.size(); i += lineSize) {
            // Создаём подсписок из основного списка, от шага до двойного шага или размера списка
            keyboardRowsList.add(JOKE_GENRES.subList(i, Math.min(JOKE_GENRES.size(), i + lineSize)));
        }
        keyboardRowsList.add(List.of(JOKEISGOVNO));
    }

    @Override
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {
        String messageText = chat.getMessageText();

        String genre = chat.getMessageText();

        chat.setMessageText(messageProcessing.getRepositoryManager().getVerifiableJoke().getMessageText());

        if(JOKE_GENRES.contains(messageText)) {
            messageProcessing.setKeyboard(new BaseKeyboard());
            messageProcessing.getRepositoryManager().registerJoke(chat, genre);
            return "Так точно, Повелитель " + "\uD83D\uDC4C";
        }else if (messageText.equals(JOKEISGOVNO)) {
            messageProcessing.setKeyboard(new BaseKeyboard());
            return "Вот и я так думаю";
        }else {
            return messageProcessing.defaultMessage();
        }
    }
}
