package com.deviget.minesweeper.exception;

public class GameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5718024353725380360L;

	public GameNotFoundException(Integer gameId) {
		super("The game " + gameId + " was not found");
	}
}
