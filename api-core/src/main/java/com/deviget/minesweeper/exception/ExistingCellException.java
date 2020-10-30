package com.deviget.minesweeper.exception;

public class ExistingCellException extends RuntimeException {

	private static final long serialVersionUID = 4977580039388710504L;

	public ExistingCellException(Integer cellId) {
		super("The cell " + cellId + " already exists");
	}
}
