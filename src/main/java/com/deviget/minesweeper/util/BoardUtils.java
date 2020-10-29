package com.deviget.minesweeper.util;

import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.Cell;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Board Utility helper.-
 */
public final class BoardUtils {


	/**
	 * Retrieves all boundary cells of an specific cell.-
	 *
	 * @param board the board to locate cells
	 * @param cell  the cell to use as center
	 * @return a List of Cells, 8 cells max
	 */
	public static List<Cell> findBoundaryCells(Board board, Cell cell) {

		return (board.getCellList().stream().filter(c ->
				CellUtils.isLeftUpperBoundary(cell, c) ||
						CellUtils.isLeftBoundary(cell, c) ||
						CellUtils.isLeftLowerBoundary(cell, c) ||
						CellUtils.isUpperBoundary(cell, c) ||
						CellUtils.isRightUpperBoundary(cell, c) ||
						CellUtils.isRightBoundary(cell, c) ||
						CellUtils.isRightLowerBoundary(cell, c)
		).collect(Collectors.toList()));
	}

	/**
	 * Verifies if all no mine cell are revealed.-
	 *
	 * @param board the board to verify
	 * @return true if the board is complete, false if not
	 */
	public static boolean isGameComplete(Board board) {
		return board.getCellList().stream().anyMatch(c -> (!c.isMine()) && c.isRevealed());
	}
}
