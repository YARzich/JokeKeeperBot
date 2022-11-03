package com.yaroslav.joke_keeper_bot.bot.DB.tables;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity(name = "usersDataTable")
@Getter
@Setter
@ToString
public class User {

    @Id
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;

    private Timestamp registeredAt;

    private Long money;

    @OneToMany(mappedBy = "joker", cascade = CascadeType.ALL)
    private List<Joke> joke;

    @OneToMany(mappedBy = "user")
    private Set<ViewedJokes> viewedJokes;

}
