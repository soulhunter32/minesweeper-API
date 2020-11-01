package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;

public interface IGameService {

    Game saveGame(Game newGame);

    Game createGame(int userId, BoardSettings boardSettings) throws InvalidBoardSettingsException, UserNotFoundException;

    Game findById(Integer gameId) throws GameNotFoundException, InvalidGameStatusException;

    Cell flagCell(int game, Cell flagCell, FlagTypeEnum flagType) throws CellNotFoundException, ExistingCellException,
            CellFlaggedException, InvalidGameStatusException, GameNotFoundException;

    Cell revealCell(Game game, Cell flagCell) throws CellNotFoundException, ExistingCellException, GameOverException;

    void endGameAsFailed(Game game);
}
