package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.JOKEISGOVNO;
import static com.yaroslav.joke_keeper_bot.bot.BotVariables.jokeGenres;

public class JokeAnalysisKeyboard extends Keyboard{

    {
        List<String> row;
        Iterator<String> genresIterator = jokeGenres.iterator();
        while (genresIterator.hasNext()) {

            row = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                row.add(genresIterator.next());
            }
            keyboardRowsList.add(row);
        }
        row = new ArrayList<>();
        row.add(JOKEISGOVNO);
    }

    @Override
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {

        String messageText = chat.getMessageText();

        if(jokeGenres.contains(messageText)) {
            messageProcessing.setKeyboard(new BaseKeyboard());
            messageProcessing.getRepositoryManager().registerJoke(chat, messageText);
            return "Так точно, Повелитель" + "\uD83D\uDC4C";
        }else if (messageText.equals(JOKEISGOVNO)) {
            messageProcessing.setKeyboard(new BaseKeyboard());
            return "Вот и я так думаю";
        }else {
            return messageProcessing.defaultMessage();
        }
    }
}
