package com.deviget.minesweeper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class GameOverException extends Exception {

    private static final long serialVersionUID = 4977580039388710504L;

    public GameOverException() {
        super("The cell had a mine ! Game Over !");
    }
}
