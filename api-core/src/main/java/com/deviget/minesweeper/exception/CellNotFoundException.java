package com.deviget.minesweeper.exception;

import com.deviget.minesweeper.model.dto.Cell;

public class CellNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4396573623121165297L;

	public CellNotFoundException(Cell cell) {
		super("The cell " + cell + " was not found");
	}

	public CellNotFoundException(Cell cell, Integer boardId) {
		super("The cell " + cell + " was not found for the board " + boardId);
	}
}
