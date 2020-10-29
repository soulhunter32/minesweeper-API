package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Cell;

public interface IBoardService {

	int revealCellIUserService(Cell cell);

	int findAdjacentData(Cell cell);

	void flagCell(Cell cell);

	Board startBoard(Board board, BoardSettings settings) throws InvalidBoardSettingsException;
}
