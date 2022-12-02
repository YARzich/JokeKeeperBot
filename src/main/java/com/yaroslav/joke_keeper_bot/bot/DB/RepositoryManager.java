package com.yaroslav.joke_keeper_bot.bot.DB;

import com.yaroslav.joke_keeper_bot.bot.ChatData;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.JokeRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.repositores.UserRepository;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.Joke;
import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
@Getter
@AllArgsConstructor
public class RepositoryManager {

    private final UserRepository userRepository;

    private final JokeRepository jokeRepository;

    private final Queue<ChatData> jokeQueue = new LinkedList<>();

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
            user.setViewedJokes(new ArrayList<>());
            user.setJokes(new ArrayList<>());

            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
    @Transactional
    public void registerJoke(ChatData chat, String genre) {
        Joke joke = new Joke();

        User user = userRepository.findById(chat.getChatId()).orElseThrow();

        Hibernate.initialize(user.getJokes());
        Hibernate.initialize(user.getViewedJokes());

        joke.setJoke(chat.getMessageText());
        joke.setGenre(genre);
        joke.setViewedUsers(new ArrayList<>());

        user.setMoney(user.getMoney() + 1);
        user.addViewedJoke(joke);
        user.addJoke(joke);

        jokeRepository.save(joke);
        log.info("joke saved: " + joke);
    }

    public void addJoke(ChatData chat) {
        jokeQueue.add(chat);
    }

    public ChatData getVerifiableJoke() {
        return jokeQueue.poll();
    }

    public ChatData peekVerifiableJoke() {
        return jokeQueue.peek();
    }

    @Transactional
    public ChatData getJoke(ChatData chatData) {
        ChatData jokeData = new ChatData();

        User user = userRepository.findById(chatData.getChatId()).orElseThrow();
        Hibernate.initialize(user.getViewedJokes());
        List<Long> viewedJokes = user.getViewedJokes().stream().map(Joke::getJokeId).toList();

        List<Long> listNonViewedJoke = new ArrayList<>();

        for (long i = 1; i < jokeRepository.count() + 1; i++) {
            if(!viewedJokes.contains(i)) {
                listNonViewedJoke.add(i);
            }
        }

        if(listNonViewedJoke.isEmpty()) {
            return null;
        }

        Joke joke = jokeRepository
                .findById(listNonViewedJoke.get((int)(Math.random() * listNonViewedJoke.size()))).orElseThrow();

        user.addViewedJoke(joke);

        Hibernate.initialize(joke.getJoker());
        Hibernate.initialize(joke.getJoke());
        Hibernate.initialize(joke.getGenre());

        User joker = joke.getJoker();

        jokeData.setChatId(joker.getChatId());
        jokeData.setFirstName(joker.getFirstName());
        jokeData.setLastName(joker.getLastName());
        jokeData.setUserName(joker.getUserName());
        jokeData.setMessageText(joke.getJoke() +
                "\n\nЖанр: " + joke.getGenre());

        return jokeData;
    }

    @Transactional
    public void deleteViewedJokes(ChatData chatData) {
        User user = userRepository.findById(chatData.getChatId()).orElseThrow();
        Hibernate.initialize(user.getViewedJokes());
        user.removeViewedJokes();
    }

    public long getMoney(ChatData chatData) {
        return userRepository.findById(chatData.getChatId()).orElseThrow().getMoney();
    }

    @Transactional
        public List<User> getLeaderboard() {
        List<User> list = (List<User>) userRepository.findAll();

        list.sort((o1, o2) -> o2.getMoney().compareTo(o1.getMoney()));

        return list;
    }

}