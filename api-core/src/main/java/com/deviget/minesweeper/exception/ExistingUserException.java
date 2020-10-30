package com.deviget.minesweeper.exception;

public class ExistingUserException extends RuntimeException {


	private static final long serialVersionUID = 5089524167351336546L;

	public ExistingUserException(String username) {
		super("The user " + username + " already exists");
	}
}
