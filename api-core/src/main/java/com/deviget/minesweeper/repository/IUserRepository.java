package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing persistence of {@link User} entity.-
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
