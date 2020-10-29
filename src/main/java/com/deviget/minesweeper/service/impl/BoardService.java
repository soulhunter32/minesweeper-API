package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.IBoardService;
import com.deviget.minesweeper.util.BoardUtils;
import com.deviget.minesweeper.util.CellUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

/**
 * Service in charge of all board related functionalities.-
 */
@Log4j2
@Service
public class BoardService implements IBoardService {

	@Value("${general.settings.difficulty-level:0.35}")
	private double difficultyLeve;

	@Autowired
	private IUserRepository userRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	private Board currentBoard;

	@Override
	public int revealCellIUserService(Cell cell) {
		return 0;
	}

	@Override
	public int findAdjacentData(Cell cell) {
		return 0;
	}

	@Override
	public void flagCell(Cell cell) {

	}

	/**
	 * Creates a new Board with the current settings and populates the cells with the amount of mines randomly
	 * assigned.-
	 *
	 * @param board
	 * @param settings the settings to create the new board
	 * @return the new created and ready-to-play board
	 */
	@Override
	public Board startBoard(Board board, BoardSettings settings) throws InvalidBoardSettingsException {

		validate(settings);

		currentBoard = Board.builder()
				.settings(settings)
				.build();

		populateCells();
		populateMines();
		setCellAdjacents();

		return currentBoard;
	}

	/**
	 * Evaluates every cell in the current board and marks the ones that has adjacent mine cells'.-
	 */
	private void setCellAdjacents() {
		currentBoard.getCellList().stream().filter(Predicate.not(Cell::isMine))
				.forEach(cell ->
						cell.setAdjacentMines((int) BoardUtils.findBoundaryCells(currentBoard, cell).stream().filter(Cell::isMine).count()));
	}

	/**
	 * Validate that the amount of mines for the new board. It evaluates the board size and a difficulty level to
	 * allow or not the amount of mines.-
	 *
	 * @param settings the settings to evaluate
	 * @throws InvalidBoardSettingsException if the amount of mines are excessive for the current board size
	 */
	private void validate(BoardSettings settings) throws InvalidBoardSettingsException {
		if (settings.getTotalMines() > settings.getHeight() * settings.getWidth() * difficultyLeve) {
			throw new InvalidBoardSettingsException("Too many mines, please lower the amount.");
		}
	}

	/**
	 * Populates all the mines randomly in the board cells'.-
	 */
	private void populateMines() {
		List<Cell> cellList = currentBoard.getCellList();
		int mineCount = 0;

		while (mineCount < currentBoard.getSettings().getTotalMines()) {
			cellList.stream().filter(cell -> (cell.getXCoordinate() == CellUtils.getRandomCellPosition(currentBoard.getSettings().getWidth()))
					&& (cell.getYCoordinate() == CellUtils.getRandomCellPosition(currentBoard.getSettings().getHeight())) && !cell.isMine())
					.findAny().ifPresent(c -> c.setMine(true));
			mineCount++;
		}
		currentBoard.setCellList(cellList);
	}

	/**
	 * Populates the cells in the current board using its settings.-
	 */
	private void populateCells() {
		for (int x = 1; x < currentBoard.getSettings().getWidth() + 1; x++) {
			for (int y = 1; y < currentBoard.getSettings().getHeight() + 1; y++) {
				currentBoard.getCellList().add(Cell.builder()
						.xCoordinate(x)
						.yCoordinate(y)
						.build());
			}
		}
	}
}
