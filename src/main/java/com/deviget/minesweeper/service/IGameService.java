package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;

public interface IGameService {
	Game createGameForUser(User user);

	Game saveGame(Game newGame);
}
