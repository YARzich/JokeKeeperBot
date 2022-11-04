package com.yaroslav.joke_keeper_bot.bot.telegram;

import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TgKeyboard {
    private final List<List<String>> keyboardRowsList;

    public TgKeyboard(Keyboard keyboard) {
        this.keyboardRowsList = keyboard.getKeyboardRowsList();
    }

    public void setKeyboard(SendMessage sendMessage) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        keyboardRowsList
                .forEach(list -> {
                    KeyboardRow keyboardRow = new KeyboardRow();
                    list.forEach(keyboardRow::add);
                    keyboardRows.add(keyboardRow);
                });

        keyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(keyboardMarkup);
    }
}
