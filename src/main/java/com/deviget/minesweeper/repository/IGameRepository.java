package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing persistence of {@link Game} entity.-
 */
@Repository
public interface IGameRepository extends CrudRepository<Game, Integer> {
}
