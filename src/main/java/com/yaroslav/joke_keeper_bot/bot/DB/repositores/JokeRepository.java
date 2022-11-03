package com.yaroslav.joke_keeper_bot.bot.DB.repositores;

import com.yaroslav.joke_keeper_bot.bot.DB.tables.Joke;
import org.springframework.data.repository.CrudRepository;

public interface JokeRepository extends CrudRepository<Joke, Long> {
}
