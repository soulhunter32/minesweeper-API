package com.deviget.minesweeper.util;

import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.Cell;

import java.util.ArrayList;
import java.util.List;

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

		List<Cell> boundaryCellList = new ArrayList<>();

		board.getCellList().stream().filter(c ->
				CellUtils.isLeftUpperBoundary(cell, c) ||
						CellUtils.isLeftBoundary(cell, c) ||
						CellUtils.isLeftLowerBoundary(cell, c) ||
						CellUtils.isUpperBoundary(cell, c) ||
						CellUtils.isRightUpperBoundary(cell, c) ||
						CellUtils.isRightBoundary(cell, c) ||
						CellUtils.isRightLowerBoundary(cell, c)
		).findFirst().ifPresent(boundaryCellList::add);

		return boundaryCellList;
	}
}
