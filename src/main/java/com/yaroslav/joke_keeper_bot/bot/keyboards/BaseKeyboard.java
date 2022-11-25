package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.BotVariables;
import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
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
    public String checkMessage(ChatData chat, MessageProcessing messageProcessing) {

        String messageText = chat.getMessageText();

        switch (messageText) {
            case "/start" -> {
                messageProcessing.getRepositoryManager().registerUser(chat);
                return BotVariables.START_MESSAGE;
            }
            case "/cho" -> {
                if(chat.getChatId() == TG_ADMIN_CHATID){

                    ChatData chatData = messageProcessing.getRepositoryManager().peekVerifiableJoke();

                    if(chatData == null) {
                        return "Анекдотов пока не поступало";
                    }

                    messageProcessing.setKeyboard(new JokeAnalysisKeyboard());
                    return chatData.getMessageText() + "\n" + chatData.getFirstName() + (chatData.getLastName() != null?chatData.getLastName():"");
                }
                return "ниче";
            }
            case GET_JOKE -> {
                messageProcessing.setKeyboard(new BaseKeyboard());

                ChatData chatData = messageProcessing.getRepositoryManager().getJoke(chat);

                if(chatData == null) {
                    return "Ого, ты просмотрел все анекдоты!\n" + "Если считаешь что у нас мало анекдотов, то помни, что всегда можешь предложить свои)";
                }

                return chatData.getMessageText() + "\n" + chatData.getFirstName() + " " + (chatData.getLastName() != null?chatData.getLastName():"");
            }
            case SET_JOKE -> {
                messageProcessing.setKeyboard(new RequestKeyboard());
                return REQUEST_JOKE;
            }
            case FORGET_JOKES -> {
                messageProcessing.getRepositoryManager().deleteViewedJokes(chat);
                return "\uD83D\uDD74" + "\uD83D\uDCA5" + "Вы забыли все анекдоты";
            }
            case SHOW_MONEY -> {
                return "У вас " + messageProcessing.getRepositoryManager().getMoney(chat) + " монеток\n"
                        + "Заработать их можно предлагая свои анекдоты, которые пройдут проверку\n"
                        + "Но мой создатель к сожалению еще не придумал что с их помощью можно делать кроме как хвастаться друзьям\n"
                        + "Поэтому если у вас есть хорошая идея, вы можете написать её сюда -> @Yarzuki";
            }
            case LEADERBOARD -> {

                StringBuilder sb = new StringBuilder();

                List<User> list = messageProcessing.getRepositoryManager().getLeaderboard();

                for (int i = 0; i < list.size(); i++) {
                    sb.append(i + 1).append(". ").append(list.get(i).toString());

                    switch (i) {
                        case 0 -> sb.append(" - Большой молодец");
                        case 1 -> sb.append(" - Молодец чуть поменьше");
                        case 2 -> sb.append(" - Ну тоже ниче такой");
                    }

                    sb.append("\n");
                }

                return sb.toString();
            }
            default -> {
                return messageProcessing.defaultMessage();
            }
        }
    }
}
