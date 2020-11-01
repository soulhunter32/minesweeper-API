package com.deviget.minesweeper.exception;

public class ExistingCellException extends Exception {

    private static final long serialVersionUID = 4977580039388710504L;

    public ExistingCellException(final Integer cellId) {
        super("The cell " + cellId + " already exists");
    }
}
