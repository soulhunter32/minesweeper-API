package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing persistence of {@link User} entity.-
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
}
