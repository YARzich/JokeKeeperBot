package com.yaroslav.joke_keeper_bot.bot;

import java.util.List;

public class BotVariables {

    public static final Long TG_ADMIN_CHATID = Long.valueOf(System.getenv().get("TG_ADMIN_CHATID"));

    public static final String HELP_TEXT = "Кароче суть такова...\nБот вообще не готов, так что, сиди, тыкай и не бузи\nНемного погодя бот будет просто бомба";
    public static final String FUNCTION_SOON = "Функционала пока что нет, но вы держитесь" + "⏳";
    public static final String GET_JOKE = "Получить шутеечку" + "\uD83E\uDD21";
    public static final String SET_JOKE = "Поделиться шутеечкой" + "\uD83D\uDCB0";
    public static final String REQUEST_JOKE = "Пиши шукту ниже и я ее рассмотрю. Если она окажется хорошей, то получешь монетку" + "\uD83E\uDD11";
    public static final String GO_BACK = "Передумать";

    public static final String JOKEISGOVNO = "Анекдот - херня " + "❌";
    public static final String START_MESSAGE = "ыыы";
    public static final List<String> defaultMessageList = List.of("Ты о чем бро?", "Я тебя не понимаю...", "Таким словам меня не учили(", "Какой то ты непонятный, чувак", "Мой автор не такой умный, что бы я знал как отвечать");

    public static final List<String> jokeGenres = List.of("Черный юмор", "Короткие", "Сложные", "Пошлые", "Тупые", "Классические", "Актуальные");
}
