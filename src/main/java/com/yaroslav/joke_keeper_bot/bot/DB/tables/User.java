package com.yaroslav.joke_keeper_bot.bot.DB.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "usersDataTable")
@Getter
@Setter
public class User {

    @Id
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;

    private Timestamp registeredAt;

    private Long money;

    @OneToMany(mappedBy = "joker", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Joke> jokes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "joke_view",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "joke_id")
    )
    private List<Joke> viewedJokes;

    public void addViewedJoke(Joke joke) {
        viewedJokes.add(joke);
    }

    public void addJoke(Joke joke) {
        jokes.add(joke);
        joke.setJoker(this);
    }

}
