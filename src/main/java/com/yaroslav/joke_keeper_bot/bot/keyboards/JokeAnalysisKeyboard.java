package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.Message;
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
    public Message checkMessage(ChatData chat) {
        String genre = chat.getMessageText();

        ChatData joke = MessageProcessing.getRepositoryManager().getVerifiableJoke();

        if(JOKE_GENRES.contains(genre)) {
            MessageProcessing.getRepositoryManager().registerJoke(joke, genre);
            return new Message(chat.getChatId(), "Так точно, Повелитель " + "\uD83D\uDC4C",
                    new BaseKeyboard());
        }else if (genre.equals(JOKEISGOVNO)) {
            return new Message(chat.getChatId(), "Вот и я так думаю", new BaseKeyboard());
        }else {
            return new Message(chat.getChatId(), BotVariables.defaultMessage(), null);
        }
    }
}
