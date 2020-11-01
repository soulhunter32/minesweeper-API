package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.BoardSettings;

public interface IBoardService {

    Board startNewBoard(BoardSettings settings) throws InvalidBoardSettingsException;
}
