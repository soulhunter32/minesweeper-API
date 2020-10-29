package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.model.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing persistence of {@link Cell} entity.-
 */
@Repository
public interface ICellRepository extends JpaRepository<Cell, Integer> {
}
