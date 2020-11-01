package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.model.dto.Cell;

public class CellFlaggedException extends Exception {

    private static final long serialVersionUID = -4708131317783130669L;

    public CellFlaggedException(final Cell cell) {
        super("The cell " + cell + " cannot be reveal, it's already flagged");
    }
}
