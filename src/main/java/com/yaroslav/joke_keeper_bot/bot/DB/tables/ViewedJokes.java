package com.yaroslav.joke_keeper_bot.bot.DB.tables;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "viewedJokesDataTable")
@Getter
@Setter
@ToString
public class ViewedJokes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Joke joke;
}
