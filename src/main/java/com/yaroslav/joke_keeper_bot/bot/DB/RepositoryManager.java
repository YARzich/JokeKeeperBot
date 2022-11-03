package com.yaroslav.joke_keeper_bot.bot.DB;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.JokeRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.UserRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.ViewedJokesRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.Joke;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.ViewedJokes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
@Slf4j
public class RepositoryManager {

    @Autowired
    @Getter
    private UserRepository userRepository;

    @Autowired
    @Getter
    private JokeRepository jokeRepository;

    @Autowired
    @Getter
    private ViewedJokesRepository viewedJokesRepository;

    private final Queue<String> jokeQueue = new PriorityQueue<>();

    public void registerUser(ChatData chat) {

        if(userRepository.findById(chat.getChatId()).isEmpty()) {
            long chatId = chat.getChatId();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
    public void registerJoke(ChatData chat, String genre) {
        Joke joke = new Joke();

        User user = userRepository.findById(chat.getChatId()).get();

        joke.setJoke(chat.getMessageText());
        joke.setJoker(user);
        joke.setGenre(genre);

        ViewedJokes viewedJokes = new ViewedJokes();

        viewedJokes.setUser(user);
        viewedJokes.setJoke(joke);

        viewedJokesRepository.save(viewedJokes);
        jokeRepository.save(joke);
        log.info("joke saved: " + joke);
    }

    public void addJoke(ChatData chat) {
        jokeQueue.add(chat.getMessageText());
    }

    public String getJoke() {
        return jokeQueue.poll();
    }
}
