package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.ExistingCellException;
import com.deviget.minesweeper.model.dto.Cell;

public interface ICellService {
    Cell saveCell(Cell cell) throws ExistingCellException;
}
