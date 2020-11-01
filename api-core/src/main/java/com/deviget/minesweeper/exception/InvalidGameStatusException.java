package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.model.enums.GameStatusEnum;

public class InvalidGameStatusException extends Exception {

    private static final long serialVersionUID = 4977580039388710504L;

    public InvalidGameStatusException(final GameStatusEnum status) {
        super("The game has finished [" + status + "], please create a new one");
    }
}
