package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.Message;
import com.yaroslav.joke_keeper_bot.bot.MessageProcessing;

import java.util.List;

import static com.yaroslav.joke_keeper_bot.bot.BotVariables.*;

public class BaseKeyboard extends Keyboard {

    {
        keyboardRowsList.add(List.of(GET_JOKE, SET_JOKE));
        keyboardRowsList.add(List.of(FORGET_JOKES, SHOW_MONEY));
        keyboardRowsList.add(List.of(LEADERBOARD));
    }

    @Override
    public Message checkMessage(ChatData chat) {

        String messageText = chat.getMessageText();

        switch (messageText) {
            case "/start" -> {
                return new Message(chat.getChatId(), BotVariables.START_MESSAGE, null);
            }
            case "/cho" -> {
                if(chat.getChatId() == TG_ADMIN_CHATID){

                    ChatData chatData = MessageProcessing.getRepositoryManager().peekVerifiableJoke();

                    if(chatData == null) {
                        return new Message(chat.getChatId(), "Анекдотов пока не поступало", null);
                    }

                    return new Message(chat.getChatId(), chatData.getMessageText() + "\n"
                            + chatData.getFirstName() + (chatData.getLastName() != null?chatData.getLastName():""),
                            new JokeAnalysisKeyboard());
                }
                return new Message(chat.getChatId(), "ниче", null);
            }
            case GET_JOKE -> {

                ChatData chatData = MessageProcessing.getRepositoryManager().getJoke(chat);

                if(chatData == null) {
                    return new Message(chat.getChatId(), "Ого, ты просмотрел все анекдоты!\n"
                            + "Если считаешь что у нас мало анекдотов, то помни, что всегда можешь предложить свои)",
                            null);
                }

                return new Message(chat.getChatId(), chatData.getMessageText() + "\n" + chatData.getFirstName()
                        + " " + (chatData.getLastName() != null?chatData.getLastName():""), null);
            }
            case SET_JOKE -> {
                return new Message(chat.getChatId(), REQUEST_JOKE, new RequestKeyboard());
            }
            case FORGET_JOKES -> {
                MessageProcessing.getRepositoryManager().deleteViewedJokes(chat);
                return new Message(chat.getChatId(), "\uD83D\uDD74" + "\uD83D\uDCA5" +
                        "Вы забыли все анекдоты", null);
            }
            case SHOW_MONEY -> {
                return new Message(chat.getChatId(), "У вас "
                        + MessageProcessing.getRepositoryManager().getMoney(chat) + " монеток\n"
                        + "Заработать их можно предлагая свои анекдоты, которые пройдут проверку\n"
                        + "Но мой создатель к сожалению еще не придумал что с их помощью можно делать" +
                        " кроме как хвастаться друзьям\n"
                        + "Поэтому если у вас есть хорошая идея, вы можете написать её сюда -> @Yarzuki",
                        null);
            }
            case LEADERBOARD -> {

                StringBuilder sb = new StringBuilder();

                List<User> list = MessageProcessing.getRepositoryManager().getLeaderboard();

                for (int i = 0; i < list.size(); i++) {
                    sb.append(i + 1).append(". ").append(list.get(i).toString());

                    switch (i) {
                        case 0 -> sb.append(" - Большой молодец");
                        case 1 -> sb.append(" - Молодец чуть поменьше");
                        case 2 -> sb.append(" - Ну тоже ниче такой");
                    }

                    sb.append("\n");
                }

                return new Message(chat.getChatId(), sb.toString(), null);
            }
            default -> {
                return new Message(chat.getChatId(), BotVariables.defaultMessage(), null);
            }
        }
    }
}
