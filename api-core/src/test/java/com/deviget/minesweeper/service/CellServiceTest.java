package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.ExistingCellException;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.repository.ICellRepository;
import com.deviget.minesweeper.service.impl.CellService;
import com.deviget.minesweeper.util.ModelMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CellServiceTest {

    @InjectMocks
    private CellService cellService;

    @Mock
    private ICellRepository cellRepository;

    @Mock
    private ModelMapperUtils modelMapperUtils;

    @Test(expected = ExistingCellException.class)
    public void testSaveCell_withNonExistingCell_shouldThrowExistingCellException() throws ExistingCellException {

        when(cellRepository.existsById(anyInt())).thenReturn(false);

        cellService.saveCell(mock(Cell.class));
    }

    @Test
    public void testSaveCell_withExistingCell_shouldSaveCell() throws ExistingCellException {

        final com.deviget.minesweeper.model.entity.Cell entityMock = mock(com.deviget.minesweeper.model.entity.Cell.class);
        final Cell cellMock = mock(Cell.class);
        when(cellRepository.existsById(anyInt())).thenReturn(true);
        when(cellRepository.saveAndFlush(entityMock)).thenReturn(entityMock);
        when(modelMapperUtils.map(any(com.deviget.minesweeper.model.dto.Cell.class), any(Class.class))).thenReturn(entityMock);
        when(modelMapperUtils.map(any(com.deviget.minesweeper.model.entity.Cell.class), any(Class.class))).thenReturn(cellMock);

        cellService.saveCell(cellMock);

        verify(cellRepository, times(1)).saveAndFlush(any(com.deviget.minesweeper.model.entity.Cell.class));
    }
}