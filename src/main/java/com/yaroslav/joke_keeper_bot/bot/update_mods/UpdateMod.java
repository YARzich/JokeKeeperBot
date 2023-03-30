package com.yaroslav.joke_keeper_bot.bot.update_mods;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.Message;

@FunctionalInterface
public interface UpdateMod {

    Message checkMessage(ChatData chat);
}
