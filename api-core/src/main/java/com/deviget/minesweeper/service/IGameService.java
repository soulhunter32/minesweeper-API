package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;

public interface IGameService {

    Game saveGame(Game newGame);

    Game createGame(User user, BoardSettings boardSettings) throws InvalidBoardSettingsException;

    Game findById(Integer gameId) throws GameNotFoundException, InvalidGameStatusException;

    Cell flagCell(Game game, Cell flagCell, FlagTypeEnum flagType) throws CellNotFoundException, ExistingCellException;

    Cell revealCell(Game game, Cell flagCell) throws CellNotFoundException,
            ExistingCellException, GameOverException, CellFlaggedException;

    void endGame(Game game);
}
