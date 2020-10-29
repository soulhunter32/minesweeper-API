package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.*;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.repository.IGameRepository;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.IBoardService;
import com.deviget.minesweeper.service.ICellService;
import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.util.CellUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Service in charge of all game related functionalities.-
 */
@Log4j2
@Service
public class GameService implements IGameService {

	@Autowired
	private IGameRepository gameRepository;

	@Autowired
	private IBoardService boardService;

	@Autowired
	private ICellService cellService;

	@Autowired
	private IUserRepository userRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	/**
	 * Creates a new game for the current user with the current settings.-
	 *
	 * @param user          the user to create the game for
	 * @param boardSettings the settings for the new board
	 * @return the create board
	 * @throws InvalidBoardSettingsException if the {@link BoardService} are not valid
	 */
	@Override
	public Game createGame(User user, BoardSettings boardSettings) throws InvalidBoardSettingsException {
		Game game = Game.builder()
				.user(user)
				.board(Board.builder().settings(boardSettings).build())
				.build();
		Board board = boardService.startBoard(game.getBoard(), boardSettings);

		game.setBoard(board);

		com.deviget.minesweeper.model.entity.Game newGame = modelMapper.map(game,
				com.deviget.minesweeper.model.entity.Game.class);

		newGame.setUser(userRepository.findById(user.getUserId()).get());

		return modelMapper.map(gameRepository.save(newGame), Game.class);
	}

	/**
	 * Finds a game by its id.-
	 *
	 * @param gameId the game id too look for
	 * @return the game found for the id
	 * @throws GameNotFoundException if the game was not found
	 */
	@Override
	public Game findById(Integer gameId) throws GameNotFoundException, InvalidGameStatusException {
		com.deviget.minesweeper.model.entity.Game gameFound =
				gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
		if (gameFound.isOver()) {
			throw new InvalidGameStatusException(gameFound.getStatus());
		}
		return modelMapper.map(gameFound, Game.class);
	}

	/**
	 * Flags a a Cell for the current game's board.-
	 *
	 * @param game     the current game
	 * @param flagCell the cell to flag
	 * @param flagType the type of flag to assign
	 * @return the flagged cell
	 */
	@Override
	public Cell flagCell(Game game, Cell flagCell, FlagTypeEnum flagType) throws CellNotFoundException,
			ExistingCellException, CellFlaggedException {
		return cellService.saveCell(CellUtils.flagCell(game.getBoard(), flagCell, flagType));
	}

	/**
	 * Reveals the current cell for the current game.-
	 *
	 * @param game       the game where the board' s cell is located
	 * @param revealCell the cell to reveal
	 * @return the revealed cell
	 * @throws CellNotFoundException
	 * @throws ExistingCellException
	 */
	@Override
	public Cell revealCell(Game game, Cell revealCell) throws CellNotFoundException, ExistingCellException,
			GameOverException {
		return cellService.saveCell(CellUtils.revealCell(game, revealCell));
	}

	/**
	 * Ends the current game.-
	 *
	 * @param game the game to end
	 */
	@Override
	public void endGame(Game game) {
		com.deviget.minesweeper.model.entity.Game gameToEnd = gameRepository.findById(game.getId()).get();
		gameToEnd.setEndTime(LocalDateTime.now());


		Duration elapsedTime = Duration.between(gameToEnd.getCreateTime(), gameToEnd.getEndTime());

		gameToEnd.setElapsedTime(String.valueOf(elapsedTime.toMinutesPart()).concat(" minutes and ")
				.concat(String.valueOf(elapsedTime.toSecondsPart())));
		gameToEnd.setStatus(GameStatusEnum.FAILED);
		gameRepository.saveAndFlush(gameToEnd);
	}

	/**
	 * Saves the current game.-
	 *
	 * @param game the game to save
	 * @return the current saved game
	 */
	@Override
	public Game saveGame(Game game) {
		game.setEditTime(LocalDateTime.now());
		return modelMapper.map(
				gameRepository.save(modelMapper.map(game, com.deviget.minesweeper.model.entity.Game.class)),
				Game.class);
	}
}
