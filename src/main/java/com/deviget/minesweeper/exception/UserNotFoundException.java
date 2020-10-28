package com.deviget.minesweeper.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4396573623121165297L;

	public UserNotFoundException(Integer userId) {
		super("The user " + userId + " was not found");
	}
}
