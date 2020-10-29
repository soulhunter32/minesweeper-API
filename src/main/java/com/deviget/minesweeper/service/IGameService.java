package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;

public interface IGameService {

	Game saveGame(Game newGame);

	Game createGame(User user, BoardSettings boardSettings) throws InvalidBoardSettingsException;
}
