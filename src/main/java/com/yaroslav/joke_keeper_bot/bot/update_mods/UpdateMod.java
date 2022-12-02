package com.yaroslav.joke_keeper_bot.bot.update_mods;

import com.yaroslav.joke_keeper_bot.bot.ChatData;

@FunctionalInterface
public interface UpdateMod {

    String checkMessage(ChatData chat);
}
