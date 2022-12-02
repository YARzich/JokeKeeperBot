package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotVariables {

    public static final Long TG_ADMIN_CHATID = Long.valueOf(System.getenv().get("TG_ADMIN_CHATID"));

    public static final String HELP_TEXT = """
            Это бот - хранитель анекдотов.
            Здесь ты можешь как посмотреть множество разных анекдотов, так и самому пополнить базу любимым анекдотом.
            Каждый предложенный анекдот рассматривает наша комиссия и за хороший анекдот начисляются монетки(Пока функционала с монетками никакого нет, просто циферка на которую можно любоваться)

            Если есть какие то предложения и идеи, то можно написать админу сюда ->>> @Yarzuki""";
    public static final String FUNCTION_SOON = "Функционала пока что нет, но вы держитесь " + "⏳";
    public static final String GET_JOKE = "Получить шутеечку " + "\uD83E\uDD23";
    public static final String SET_JOKE = "Поделиться шутеечкой " + "\uD83E\uDD21";
    public static final String FORGET_JOKES = "Забыть анекдоты " + "\uD83E\uDDE0";
    public static final String SHOW_MONEY = "Узнать сколько у тебя монеток " + "\uD83E\uDD11";
    public static final String LEADERBOARD = "Список лидеров " + "\uD83C\uDFC6";
    public static final String REQUEST_JOKE = "Пиши шукту ниже и я ее рассмотрю. Если она окажется хорошей, то получишь монетку " + "\uD83E\uDD11";
    public static final String GO_BACK = "Передумать";

    public static final String JOKEISGOVNO = "Недостоин " + "❌";
    public static final String START_MESSAGE = "Здарова, если что то не знаешь об этом боте или хочешь узнать поподробнее пиши /help";
    public static final List<String> DEFAULT_MESSAGE_LIST = List.of("Ты о чем бро?", "Я тебя не понимаю...", "Таким словам меня не учили(",
            "Какой то ты непонятный, чувак", "Мой автор не такой умный, что бы я знал как отвечать");

    public static final List<String> JOKE_GENRES = List.of("Черный юмор", "Сложные", "Пошлые", "Тупые", "Классические", "Актуальные");

    private static final Map<Long, Keyboard> keyboardMap = new HashMap<>();

    static {
        MessageProcessing.getRepositoryManager().getUserRepository().findAll().forEach(x -> keyboardMap.put(x.getChatId(), new BaseKeyboard()));
    }

    public static String defaultMessage() {
        return BotVariables.DEFAULT_MESSAGE_LIST.get((int)(Math.random() * BotVariables.DEFAULT_MESSAGE_LIST.size()));
    }

    public static Keyboard getKeyboard(Long userId) {
        return keyboardMap.get(userId);
    }
    public static void setKeyboard(Long userId, Keyboard keyboard) {
        keyboardMap.put(userId, keyboard);
    }
}
