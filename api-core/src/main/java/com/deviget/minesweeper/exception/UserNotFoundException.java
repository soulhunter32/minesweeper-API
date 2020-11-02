package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperNotFoundException;

public class UserNotFoundException extends MinesweeperNotFoundException {

    private static final long serialVersionUID = 4396573623121165297L;

    public UserNotFoundException(final Integer userId) {
        super("The user " + userId + " was not found");
    }
}
