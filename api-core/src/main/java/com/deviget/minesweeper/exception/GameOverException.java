package com.deviget.minesweeper.exception;

public class GameOverException extends Exception {

    private static final long serialVersionUID = 4977580039388710504L;

    public GameOverException() {
        super("The cell had a mine ! Game Over !");
    }
}
