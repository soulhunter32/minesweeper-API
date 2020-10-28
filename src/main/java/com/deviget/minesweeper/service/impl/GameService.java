package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.repository.IGameRepository;
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

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Creates a new game for the current user.-
	 *
	 * @param user the user to whom create the new game.-
	 * @return a created {@link Game}
	 */
	@Override
	public Game createGameForUser(User user) {
		return null;
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
