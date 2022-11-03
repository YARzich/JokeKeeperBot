package com.yaroslav.joke_keeper_bot.bot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatData {
    private long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private String messageText;
}
