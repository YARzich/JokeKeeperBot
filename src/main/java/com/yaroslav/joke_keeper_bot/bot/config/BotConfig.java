package com.yaroslav.joke_keeper_bot.bot.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class BotConfig {

    private static final Map<String, String> getenv = System.getenv();

    @Getter
    private final String BOT_NAME = getenv.get("BOT_NAME");

    @Getter
    private final String BOT_TOKEN = getenv.get("BOT_TOKEN");

}
