package com.yaroslav.joke_keeper_bot.bot.DB.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "jokesDataTable")
@Getter
@Setter
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long jokeId;

    @Lob
    @Column(length = 2000)
    private String joke;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "joker_id")
    private User joker;


    private String genre;

    @ManyToMany(mappedBy = "viewedJokes", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<User> viewedUsers;
}
