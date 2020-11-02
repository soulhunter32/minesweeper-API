package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperServerErrorException;

public class InvalidBoardSettingsException extends MinesweeperServerErrorException {

    private static final long serialVersionUID = 4742192259555414034L;

    public InvalidBoardSettingsException(final String message) {
        super(message);
    }
}
