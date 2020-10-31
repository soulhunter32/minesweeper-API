package com.deviget.minesweeper.exception;

public class InvalidBoardSettingsException extends Throwable {

    private static final long serialVersionUID = 4742192259555414034L;

    public InvalidBoardSettingsException(final String s) {
        super(s);
    }
}
