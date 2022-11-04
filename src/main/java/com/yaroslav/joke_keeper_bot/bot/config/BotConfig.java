package com.yaroslav.joke_keeper_bot.bot.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotConfig {

    private static final Map<String, String> getenv = System.getenv();

    public static final String BOT_NAME = getenv.get("BOT_NAME");

    public static final String BOT_TOKEN = getenv.get("BOT_TOKEN");
}
