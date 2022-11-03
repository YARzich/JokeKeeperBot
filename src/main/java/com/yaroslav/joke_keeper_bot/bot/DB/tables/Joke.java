package com.yaroslav.joke_keeper_bot.bot.DB.tables;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "jokesDataTable")
@Getter
@Setter
@ToString
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jokeId;

    private String joke;

    @ManyToOne(cascade = CascadeType.ALL)
    private User joker;

    private String genre;

    @OneToMany(mappedBy = "joke")
    private Set<ViewedJokes> overlooked;
}
