package com.yaroslav.joke_keeper_bot.bot;

import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import lombok.Getter;

import java.util.List;

@Getter
public class Message {
    private long chatId;
    private String message;
    private Keyboard keyboard;
    private List<List<Button>> rowsInLine;

    public Message(long chatId, String message, Keyboard keyboard) {
        this.chatId = chatId;
        this.message = message;
        this.keyboard = keyboard;
    }

    public Message(long chatId, String message, Keyboard keyboard, List<List<Button>> rowsInLine) {
        this.chatId = chatId;
        this.message = message;
        this.keyboard = keyboard;
        this.rowsInLine = rowsInLine;
    }
}

class Button <Button> {
    Button button;
}
