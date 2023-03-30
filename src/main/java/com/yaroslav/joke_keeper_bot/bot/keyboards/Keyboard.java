package com.yaroslav.joke_keeper_bot.bot.keyboards;

import com.yaroslav.joke_keeper_bot.bot.update_mods.UpdateMod;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Keyboard implements UpdateMod {
    protected final List<List<String>> keyboardRowsList = new ArrayList<>();
}
