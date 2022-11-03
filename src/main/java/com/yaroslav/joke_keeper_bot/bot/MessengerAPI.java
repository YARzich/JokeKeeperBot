package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;

public interface MessengerAPI {

    void sendMessage(long chatId, String message, Keyboard Keyboard);
}
