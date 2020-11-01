package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.model.dto.Board;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.impl.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

    public static final String DIFFICULTY_LEVEL = "difficultylevel";

    @InjectMocks
    private BoardService boardService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private Board.BoardBuilder boardBuilder;

    @Mock
    private BoardSettings.BoardSettingsBuilder boardSettingsBuilder;

    @Mock
    private Cell.CellBuilder cellBuilder;

    @Before
    public void setUp() {
        boardBuilder = mock(Board.BoardBuilder.class);
        boardSettingsBuilder = mock(BoardSettings.BoardSettingsBuilder.class);
        cellBuilder = mock(Cell.CellBuilder.class);
    }

    @Test(expected = InvalidBoardSettingsException.class)
    public void testStartBoard_withTooManyMinesAndLowDifficultyLevel_shouldThrowInvalidBoardSettingsException() throws InvalidBoardSettingsException {

        final BoardSettings boardSettings = mock(BoardSettings.class);
        when(boardSettings.getWidth()).thenReturn(10);
        when(boardSettings.getHeight()).thenReturn(10);
        when(boardSettings.getTotalMines()).thenReturn(100);

        ReflectionTestUtils.setField(boardService, DIFFICULTY_LEVEL, 0.1);

        boardService.startNewBoard(boardSettings);
    }

    @Test
    public void testStartBoard_withValidSettings_shouldCreateBoardWithSettingsAndPopulateCellsAndMinesAndAdjacentData() throws InvalidBoardSettingsException {

        final BoardSettings boardSettings = mock(BoardSettings.class);
        final int totalMines = 20;
        final int boardHeight = 10;
        final int boardWeight = 10;
        when(boardSettings.getWidth()).thenReturn(boardWeight);
        when(boardSettings.getHeight()).thenReturn(boardHeight);
        when(boardSettings.getTotalMines()).thenReturn(totalMines);

        ReflectionTestUtils.setField(boardService, DIFFICULTY_LEVEL, 0.35);

        final Board board = boardService.startNewBoard(boardSettings);

        assertEquals(boardWeight * boardHeight, board.getCellList().size());
        assertEquals(totalMines, board.getSettings().getTotalMines());
        assertEquals(totalMines, board.getCellList().stream().filter(Cell::isMine).count());
        assertEquals(0, board.getCellList().stream().filter(Cell::isFlagged).count());
        assertEquals(0, board.getCellList().stream().filter(Cell::isRevealed).count());
    }
}
