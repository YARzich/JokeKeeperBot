package com.yaroslav.joke_keeper_bot.bot.DB.repositores;

import com.yaroslav.joke_keeper_bot.bot.DB.tables.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
