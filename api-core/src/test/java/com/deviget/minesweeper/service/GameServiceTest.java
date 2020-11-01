package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.GameNotFoundException;
import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.exception.InvalidGameStatusException;
import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.*;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.repository.IGameRepository;
import com.deviget.minesweeper.service.impl.GameService;
import com.deviget.minesweeper.util.CellUtils;
import com.deviget.minesweeper.util.GameUtils;
import com.deviget.minesweeper.util.ModelMapperUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(MockitoJUnitRunner.class)
@PrepareForTest({CellUtils.class, GameUtils.class})
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private ModelMapperUtils modelMapperUtils;

    @Mock
    private IGameRepository gameRepository;

    @Mock
    private IBoardService boardService;

    @Mock
    private IUserService userService;

    @Mock
    private ICellService cellService;

    @Mock
    private Board.BoardBuilder boardBuilder;

    @Mock
    private BoardSettings.BoardSettingsBuilder boardSettingsBuilder;

    @Mock
    private Cell.CellBuilder cellBuilder;

    @Mock
    private Game.GameBuilder gameBuilder;

    @Before
    public void setUp() {
        boardBuilder = mock(Board.BoardBuilder.class);
        boardSettingsBuilder = mock(BoardSettings.BoardSettingsBuilder.class);
        cellBuilder = mock(Cell.CellBuilder.class);
        gameBuilder = mock(Game.GameBuilder.class);
        PowerMockito.mockStatic(CellUtils.class);
        PowerMockito.mockStatic(GameUtils.class);
    }

    @Test(expected = InvalidBoardSettingsException.class)
    public void testCreateGame_withTooManyMines_shouldThrowInvalidBoardSettingsException() throws InvalidBoardSettingsException, UserNotFoundException {

        final BoardSettings boardSettings = mock(BoardSettings.class);

        when(boardService.startNewBoard(boardSettings)).thenThrow(new InvalidBoardSettingsException("Error"));

        gameService.createGame(anyInt(), boardSettings);
    }

    @Test(expected = UserNotFoundException.class)
    public void testCreateGame_withInvalidUser_shouldThrowUserNotFoundException() throws Exception {

        final int userId = 1;

        when(userService.findById(userId)).thenThrow(new UserNotFoundException(userId));

        gameService.createGame(userId, any(BoardSettings.class));
    }

    @Test
    public void testCreateGame_withValidUser_shouldCreateBoardSuccessfully() throws Exception {

        final int userId = 1;

        final User userMock = mock(User.class);
        final Game gameMock = mock(Game.class);
        final int totalMines = 10;

        final com.deviget.minesweeper.model.entity.Game gameEntityMock = mock(com.deviget.minesweeper.model.entity.Game.class);
        final Board boardMock = mock(Board.class);
        final BoardSettings boardSettings = mock(BoardSettings.class);
        when(boardSettings.getTotalMines()).thenReturn(totalMines);
        when(boardMock.getSettings()).thenReturn(boardSettings);
        when(gameMock.getBoard()).thenReturn(boardMock);
        when(userMock.getId()).thenReturn(userId);
        when(userService.findById(userId)).thenReturn(userMock);
        when(boardService.startNewBoard(any())).thenReturn(boardMock);
        when(gameRepository.save(gameEntityMock)).thenReturn(gameEntityMock);
        when(modelMapperUtils.map(any(Game.class), any(Class.class))).thenReturn(gameEntityMock);
        when(modelMapperUtils.map(gameEntityMock, Game.class)).thenReturn(gameMock);

        final Game newGame = gameService.createGame(userId, any(BoardSettings.class));

        assertEquals(totalMines, newGame.getBoard().getSettings().getTotalMines());
        verify(gameRepository, times(1)).save(gameEntityMock);
    }

    @Test(expected = GameNotFoundException.class)
    public void testFindById_withInvalidGameId_shouldThrowGameNotFoundException() throws Exception {

        final int gameId = 1;

        gameService.findById(gameId);
    }

    @Test(expected = InvalidGameStatusException.class)
    public void testFindById_withGameOverGameId_shouldThrowInvalidGameStatusException() throws Exception {

        final int gameId = 1;

        final com.deviget.minesweeper.model.entity.Game gameMock = mock(com.deviget.minesweeper.model.entity.Game.class);
        when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameMock));
        when(gameMock.isOver()).thenReturn(true);

        gameService.findById(gameId);
    }

    @Test
    public void testFindById_withValidNonOverGameId_shouldReturnGame() throws Exception {

        final int gameId = 1;

        final com.deviget.minesweeper.model.entity.Game gameEntityMock = mock(com.deviget.minesweeper.model.entity.Game.class);
        when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameEntityMock));
        when(gameEntityMock.isOver()).thenReturn(false);
        final Game gameMock = mock(Game.class);
        when(gameMock.getId()).thenReturn(gameId);
        when(modelMapperUtils.map(any(), any(Class.class))).thenReturn(gameMock);

        final Game gameFound = gameService.findById(gameId);

        assertEquals(gameId, gameFound.getId());
    }

    @Test(expected = GameNotFoundException.class)
    public void testFlagCell_withInValidGameId_shouldThrowGameNotFoundException() throws Exception {

        when(gameService.findById(anyInt())).thenThrow(new GameNotFoundException(anyInt()));

        gameService.flagCell(anyInt(), any(Cell.class), any(FlagTypeEnum.class));
    }

    @Test(expected = InvalidGameStatusException.class)
    public void testFlagCell_withGameOverGameId_shouldThrowInvalidGameStatusException() throws Exception {

        final GameService gameServiceSpy = spy(this.gameService);

        doThrow(new InvalidGameStatusException(GameStatusEnum.COMPLETED)).when(gameServiceSpy).findById(anyInt());

        gameServiceSpy.flagCell(anyInt(), any(Cell.class), any(FlagTypeEnum.class));
    }

    @Test
    public void testEndGameAsFailed_withValidNonOverGameId_shouldEndGame() throws Exception {

        final int gameId = 1;

        final com.deviget.minesweeper.model.entity.Game gameEntityMock = mock(com.deviget.minesweeper.model.entity.Game.class);
        when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameEntityMock));
        final Game gameMock = mock(Game.class);
        when(gameMock.getId()).thenReturn(gameId);

        PowerMockito.doNothing().when(GameUtils.class, "setElapsedTime", any(Game.class));

        gameService.endGameAsFailed(gameMock);

        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).saveAndFlush(gameEntityMock);
    }

    @Test
    public void testSaveGame_withValidGame_shouldSaveGame() {

        final int id = 5;

        final Game gameToSave = mock(Game.class);
        final com.deviget.minesweeper.model.entity.Game gameEntity = mock(com.deviget.minesweeper.model.entity.Game.class);

        final Game savedGame = mock(Game.class);
        when(savedGame.getId()).thenReturn(id);

        when(gameRepository.save(any())).thenReturn(gameEntity);

        when(modelMapperUtils.map(any(Game.class), any(Class.class))).thenReturn(gameEntity);
        when(modelMapperUtils.map(any(com.deviget.minesweeper.model.entity.Game.class), any(Class.class))).thenReturn(savedGame);

        final Game returnedGame = gameService.saveGame(gameToSave);

        assertNotNull(returnedGame);
        assertEquals(id, returnedGame.getId());
        assertEquals(id, returnedGame.getId());
    }
}