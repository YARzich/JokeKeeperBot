package com.yaroslav.joke_keeper_bot.bot.DB.services;

import com.yaroslav.joke_keeper_bot.bot.DB.repositores.UserRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.keyboards.BaseKeyboard;
import com.yaroslav.joke_keeper_bot.bot.keyboards.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(Long id, Keyboard newKeyboard) {
        User user = userRepository.findById(id).orElseThrow();
        String keyboard = newKeyboard.getClass().getName();
        user.setKeyboard(keyboard);
        return userRepository.save(user);
    }

    public Keyboard getKeyboard(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Keyboard keyboard;

        String stringKeyboard = user.getKeyboard();

        if(stringKeyboard == null) {
            return new BaseKeyboard();
        }

        try {
            keyboard = (Keyboard) Class.forName(stringKeyboard).getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не найдена клавиатура", e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return keyboard;
    }
}
