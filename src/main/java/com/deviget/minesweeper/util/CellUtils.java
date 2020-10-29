package com.deviget.minesweeper.util;

import com.deviget.minesweeper.exception.CellNotFoundException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.Cell;

import java.util.Random;

/**
 * Cell Utility helper.-
 */
public final class CellUtils {

	/**
	 * Evaluates if a cell is right-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us right-boundary to the centerCell
	 */
	public static boolean isRightBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() + 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate();
	}

	/**
	 * Evaluates if a cell is right-upper-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us right-upper-boundary to the centerCell
	 */
	public static boolean isRightUpperBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() + 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() + 1;
	}

	/**
	 * Evaluates if a cell is right-lower-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us right-lower-boundary to the centerCell
	 */
	public static boolean isRightLowerBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() + 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() - 1;
	}

	/**
	 * Evaluates if a cell is upper-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us upper-boundary to the centerCell
	 */
	public static boolean isUpperBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate()
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() + 1;
	}

	/**
	 * Evaluates if a cell is lower-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us lower-boundary to the centerCell
	 */
	public static boolean isLowerBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate()
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() - 1;
	}


	/**
	 * Evaluates if a cell is left-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us left-boundary to the centerCell
	 */
	public static boolean isLeftBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() - 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate();
	}

	/**
	 * Evaluates if a cell is left-upper-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us left-upper-boundary to the centerCell
	 */
	public static boolean isLeftUpperBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() - 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() + 1;
	}

	/**
	 * Evaluates if a cell is left-lower-boundary to the center cell.-
	 *
	 * @param centerCell the main center cell
	 * @param targetCell the target cell to evaluate
	 * @return tru if the targetCell us left-lower-boundary to the centerCell
	 */
	public static boolean isLeftLowerBoundary(Cell centerCell, Cell targetCell) {
		return targetCell.getXCoordinate() == centerCell.getXCoordinate() - 1
				&& targetCell.getYCoordinate() == centerCell.getYCoordinate() - 1;
	}

	/**
	 * Retrieves a random position for the current board.-
	 *
	 * @return a random board position
	 */
	public static int getRandomCellPosition(int boundary) {
		return (new Random().nextInt(boundary - 1) + 1);
	}

	/**
	 * Finds a cell in the current board and flags it.-
	 *
	 * @param board    the board containing the cell
	 * @param flagCell the cell to flag
	 * @return the flagged Cell
	 */
	public static Cell flagCell(Board board, Cell flagCell) throws CellNotFoundException {
		Cell cellFound = board.getCellList().stream().filter(cell -> cell.getXCoordinate() == flagCell.getXCoordinate()
				&& cell.getYCoordinate() == flagCell.getYCoordinate()).findAny()
				.orElseThrow(() -> new CellNotFoundException(flagCell));
		cellFound.setFlagged(true);
		return cellFound;
	}
}
