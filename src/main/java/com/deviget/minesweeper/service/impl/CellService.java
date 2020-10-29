package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.ExistingCellException;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.repository.ICellRepository;
import com.deviget.minesweeper.service.ICellService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service in charge of all cell related functionalities.-
 */
@Log4j2
@Service
public class CellService implements ICellService {

	@Autowired
	private ICellRepository cellRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	/**
	 * Saves the current Cell.-
	 *
	 * @param cell the cell data to save
	 * @return the saved cell
	 * @throws ExistingCellException
	 */
	@Override
	public Cell saveCell(Cell cell) throws ExistingCellException {

		if (cellRepository.existsById(cell.getId())) {
			return modelMapper.map(cellRepository.saveAndFlush(modelMapper.map(cell,
					com.deviget.minesweeper.model.entity.Cell.class)), Cell.class);
		} else {
			throw new ExistingCellException(cell.getId());
		}
	}
}
