package com.deviget.minesweeper.exception.generic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Marker class for generify existing/conflict exceptions.-
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class MinesweeperConflictException extends Exception {
    private static final long serialVersionUID = 52328844877646631L;

    public MinesweeperConflictException() {
    }

    public MinesweeperConflictException(final String message) {
        super(message);
    }

    public MinesweeperConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MinesweeperConflictException(final Throwable cause) {
        super(cause);
    }

    public MinesweeperConflictException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
