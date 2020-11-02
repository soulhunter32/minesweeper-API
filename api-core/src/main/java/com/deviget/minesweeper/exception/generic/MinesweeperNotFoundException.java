package com.deviget.minesweeper.exception.generic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Marker class for generify not found exceptions.-
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MinesweeperNotFoundException extends Exception {
    private static final long serialVersionUID = 1183452837429898426L;

    public MinesweeperNotFoundException() {
    }

    public MinesweeperNotFoundException(final String message) {
        super(message);
    }

    public MinesweeperNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MinesweeperNotFoundException(final Throwable cause) {
        super(cause);
    }

    public MinesweeperNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
