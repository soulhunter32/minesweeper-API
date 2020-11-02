package com.deviget.minesweeper.exception.generic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Marker class for generify server error exceptions.-
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MinesweeperServerErrorException extends Exception {
    private static final long serialVersionUID = 5405543919723613343L;

    public MinesweeperServerErrorException() {
    }

    public MinesweeperServerErrorException(final String message) {
        super(message);
    }

    public MinesweeperServerErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MinesweeperServerErrorException(final Throwable cause) {
        super(cause);
    }

    public MinesweeperServerErrorException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
