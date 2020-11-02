package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperNotFoundException;
import com.deviget.minesweeper.model.dto.Cell;

public class CellNotFoundException extends MinesweeperNotFoundException {

    private static final long serialVersionUID = -7350389344067959406L;

    public CellNotFoundException(final Cell cell) {
        super("The cell " + cell + " was not found");
    }

    public CellNotFoundException(final Cell cell, final Integer boardId) {
        super("The cell " + cell + " was not found for the board " + boardId);
    }
}
