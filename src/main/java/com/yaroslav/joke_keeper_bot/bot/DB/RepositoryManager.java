package com.yaroslav.joke_keeper_bot.bot.DB;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.JokeRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.UserRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.ViewedJokesRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.Joke;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.ViewedJokes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
@Slf4j
@Getter
@AllArgsConstructor
public class RepositoryManager {

    private final UserRepository userRepository;

    private final JokeRepository jokeRepository;

    private final ViewedJokesRepository viewedJokesRepository;

    private final Queue<ChatData> jokeQueue = new PriorityQueue<>();

    public void registerUser(ChatData chat) {
        if(userRepository.findById(chat.getChatId()).isEmpty()) {
            long chatId = chat.getChatId();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            user.setMoney(0L);

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
    public void registerJoke(ChatData chat, String genre) {
        Joke joke = new Joke();

        User user = userRepository.findById(chat.getChatId()).orElseThrow();

        joke.setJoke(chat.getMessageText());
        joke.setJoker(user);
        joke.setGenre(genre);

        ViewedJokes viewedJokes = new ViewedJokes();

        viewedJokes.setUser(user);
        viewedJokes.setJoke(joke);

        user.setMoney(user.getMoney() + 1);

        userRepository.save(user);

        viewedJokesRepository.save(viewedJokes);
        jokeRepository.save(joke);
        log.info("joke saved: " + joke);
    }

    public void addJoke(ChatData chat) {
        jokeQueue.add(chat);
    }

    public ChatData getVerifiableJoke() {
        return jokeQueue.poll();
    }

    //TODO: Сделать регистрацию просмотров
    public ChatData getJoke(ChatData chatData) {
        ChatData jokeData = new ChatData();

        Joke joke = jokeRepository.findById((long)(Math.random() * jokeRepository.countAllBy())).orElseThrow();

        User joker = joke.getJoker();

        jokeData.setChatId(joker.getChatId());
        jokeData.setFirstName(joker.getFirstName());
        jokeData.setLastName(joker.getLastName());
        jokeData.setUserName(joker.getUserName());
        jokeData.setMessageText(joke.getJoke());

        return jokeData;
    }

}