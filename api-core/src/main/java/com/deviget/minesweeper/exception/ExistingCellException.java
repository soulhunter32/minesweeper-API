package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperConflictException;

public class ExistingCellException extends MinesweeperConflictException {

    private static final long serialVersionUID = 4977580039388710504L;

    public ExistingCellException(final Integer cellId) {
        super("The cell " + cellId + " already exists");
    }
}
