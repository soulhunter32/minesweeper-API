package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.exception.generic.MinesweeperConflictException;

public class ExistingUserException extends MinesweeperConflictException {


    private static final long serialVersionUID = 5089524167351336546L;

    public ExistingUserException(final String username) {
        super("The user " + username + " already exists");
    }
}
