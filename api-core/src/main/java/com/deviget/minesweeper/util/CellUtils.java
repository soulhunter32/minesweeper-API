package com.deviget.minesweeper.util;

import com.deviget.minesweeper.exception.CellFlaggedException;
import com.deviget.minesweeper.exception.CellNotFoundException;
import com.deviget.minesweeper.exception.GameOverException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Cell Utility helper.-
 */
@Log4j2
public final class CellUtils {

    /**
     * Evaluates if a cell is right-boundary to the center cell.-
     *
     * @param centerCell the main center cell
     * @param targetCell the target cell to evaluate
     * @return tru if the targetCell us right-boundary to the centerCell
     */
    public static boolean isRightBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isRightUpperBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isRightLowerBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isUpperBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isLowerBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isLeftBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isLeftUpperBoundary(final Cell centerCell, final Cell targetCell) {
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
    public static boolean isLeftLowerBoundary(final Cell centerCell, final Cell targetCell) {
        return targetCell.getXCoordinate() == centerCell.getXCoordinate() - 1
                && targetCell.getYCoordinate() == centerCell.getYCoordinate() - 1;
    }

    /**
     * Retrieves a random position for the current board.-
     *
     * @return a random board position
     */
    public static int getRandomCellPosition(final int boundary) {
        return (new Random().nextInt(boundary - 1) + 1);
    }

    /**
     * Finds a cell in the current board and flags it.-
     *
     * @param board    the board containing the cell
     * @param flagCell the cell to flag
     * @param flagType the type of the flag
     * @return the flagged Cell
     */
    public static Cell flagCell(final Board board, final Cell flagCell, final FlagTypeEnum flagType) throws CellNotFoundException,
            CellFlaggedException {
        final Cell cellFound = retrieveCellFromBoard(board, flagCell);
        if (!cellFound.isRevealed()) {
            cellFound.setFlagType(flagType);
        } else {
            throw new CellFlaggedException(cellFound);
        }
        return cellFound;
    }

    /**
     * Finds a cell in the current game and reveals it. If the cell to reveal has no adjacent mines, it automatically
     * reveals all boundary cells until adjacents mines are found. If all cells were revealed, the game is completed
     * and the game won,
     *
     * @param game     the game containing the cell
     * @param flagCell the cell to reveal
     * @return the flagged Cell
     */
    public static Cell revealCell(final Game game, final Cell flagCell) throws CellNotFoundException, GameOverException {
        final Cell cellFound = retrieveCellFromBoard(game.getBoard(), flagCell);
        if (cellFound.isMine()) {
            throw new GameOverException();
        } else if (!cellFound.isMine() && cellFound.getAdjacentMines() == 0) {
            revealNonMineCells(game.getBoard(), cellFound);
        }
        cellFound.setRevealed(true);

        if (BoardUtils.isGameComplete(game.getBoard())) {
            log.info("revealCell:: Game complete !");
            game.setStatus(GameStatusEnum.COMPLETED);
            game.setEndTime(LocalDateTime.now());
            GameUtils.setElapsedTime(game);
        }
        return cellFound;
    }

    /**
     * Finds  all boundary cells until adjacents mines are found.-
     *
     * @param board    the board containing the cell
     * @param mainCell the center cell to find its boundary cells
     */
    private static void revealNonMineCells(final Board board, final Cell mainCell) throws CellNotFoundException,
            GameOverException {

        mainCell.setRevealed(true);
        BoardUtils.findBoundaryCells(board, mainCell).stream().filter(c -> !c.isRevealed() && c.getAdjacentMines() == 0).
                forEach(c -> revealNonMineCells(board, c));
    }

    /**
     * Retrieves a specific cell from its board.-
     *
     * @param board          the board to retrieve the cell from
     * @param cellToRetrieve the cell data to retrieve
     * @return the cell if found
     */
    private static Cell retrieveCellFromBoard(final Board board, final Cell cellToRetrieve) {
        return board.getCellList().stream().filter(cell -> cell.getXCoordinate() == cellToRetrieve.getXCoordinate()
                && cell.getYCoordinate() == cellToRetrieve.getYCoordinate()).findAny()
                .orElseThrow(() -> new CellNotFoundException(cellToRetrieve));
    }
}
