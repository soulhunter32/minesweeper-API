package com.deviget.minesweeper.exception;

public class ExistingUserException extends Exception {


    private static final long serialVersionUID = 5089524167351336546L;

    public ExistingUserException(final String username) {
        super("The user " + username + " already exists");
    }
}
