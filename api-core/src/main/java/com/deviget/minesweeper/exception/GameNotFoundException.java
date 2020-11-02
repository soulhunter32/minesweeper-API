package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperNotFoundException;

public class GameNotFoundException extends MinesweeperNotFoundException {

    private static final long serialVersionUID = 5718024353725380360L;

    public GameNotFoundException(final Integer gameId) {
        super("The game " + gameId + " was not found");
    }
}
