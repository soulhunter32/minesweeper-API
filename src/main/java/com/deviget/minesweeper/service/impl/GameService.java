package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.repository.IGameRepository;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.IBoardService;
import com.deviget.minesweeper.service.IGameService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private IUserRepository userRepository;

	private ModelMapper modelMapper = new ModelMapper();

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
	 * Save the current game.-
	 *
	 * @param game the game to save
	 * @return the current saved game
	 */
	@Override
	public Game saveGame(Game game) {
		return modelMapper.map(
				gameRepository.save(modelMapper.map(game, com.deviget.minesweeper.model.entity.Game.class)),
				Game.class);
	}
}
