package com.yaroslav.joke_keeper_bot.bot.DB.repositores;

import com.yaroslav.joke_keeper_bot.bot.DB.tables.ViewedJokes;
import org.springframework.data.repository.CrudRepository;

public interface ViewedJokesRepository extends CrudRepository<ViewedJokes, Long> {
}
