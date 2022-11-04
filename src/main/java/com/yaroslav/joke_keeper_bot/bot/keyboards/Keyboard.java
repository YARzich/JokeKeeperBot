package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.update_mods.UpdateMod;

import java.util.ArrayList;
import java.util.List;

public abstract class Keyboard implements UpdateMod {
    protected final List<List<String>> keyboardRowsList = new ArrayList<>();

    public List<List<String>> getKeyboardRowsList() {
        return keyboardRowsList;
    }
}
