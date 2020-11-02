package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.service.impl.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest extends AbstractControllerTest {

    @MockBean
    protected GameService gameServiceMock;

    @Test
    public void testFlagCell_withNonExistingGameId_shouldThrowGameNotFoundException() throws Exception {

        final int gameId = 1;
        final Cell cellToFlag = Cell.builder().build();
        when(gameServiceMock.flagCell(gameId, cellToFlag, FlagTypeEnum.RED_FLAG)).thenThrow(new GameNotFoundException(gameId));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + FlagTypeEnum.RED_FLAG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(GameNotFoundException.class.getCanonicalName()));
        assertEquals("The game " + gameId + " was not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testFlagCell_withInvalidCellData_shouldThrowExistingCellException() throws Exception {

        final int gameId = 1;
        final int cellId = 2;
        final Cell cellToFlag = Cell.builder().build();
        when(gameServiceMock.flagCell(gameId, cellToFlag, FlagTypeEnum.RED_FLAG)).thenThrow(new ExistingCellException(cellId));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + FlagTypeEnum.RED_FLAG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(ExistingCellException.class.getCanonicalName()));
        assertEquals("The cell " + cellId + " already exists", result.getResolvedException().getMessage());
    }

    @Test
    public void testFlagCell_withFlaggedCell_shouldThrowCellFlaggedException() throws Exception {

        final int gameId = 1;
        final Cell cellToFlag = Cell.builder().build();
        when(gameServiceMock.flagCell(gameId, cellToFlag, FlagTypeEnum.RED_FLAG)).thenThrow(new CellFlaggedException(cellToFlag));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + FlagTypeEnum.RED_FLAG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(CellFlaggedException.class.getCanonicalName()));
        assertEquals("The cell " + cellToFlag + " cannot be reveal, it's already flagged", result.getResolvedException().getMessage());
    }

    @Test
    public void testFlagCell_withInvalidCellCoordinates_shouldThrowCellNotFoundException() throws Exception {

        final int gameId = 1;
        final Cell cellToFlag = Cell.builder().build();
        when(gameServiceMock.flagCell(gameId, cellToFlag, FlagTypeEnum.RED_FLAG)).thenThrow(new CellNotFoundException(cellToFlag));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + FlagTypeEnum.RED_FLAG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(CellNotFoundException.class.getCanonicalName()));
        assertEquals("The cell " + cellToFlag + " was not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testFlagCell_withFinishedGameId_shouldThrowInvalidGameStatusException() throws Exception {

        final int gameId = 1;
        final Cell cellToFlag = Cell.builder().build();
        final GameStatusEnum gameStatus = GameStatusEnum.FAILED;
        when(gameServiceMock.flagCell(gameId, cellToFlag, FlagTypeEnum.RED_FLAG)).thenThrow(new InvalidGameStatusException(gameStatus));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + FlagTypeEnum.RED_FLAG)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(InvalidGameStatusException.class.getCanonicalName()));
        assertEquals("The game has finished [" + gameStatus + "], please create a new one", result.getResolvedException().getMessage());
    }

    @Test
    public void testFlagCell_withCellCoordinatesAndGameId_shouldSuccessfullyFlagCell() throws Exception {

        final int gameId = 1;
        final int cellId = 2;
        final FlagTypeEnum redFlag = FlagTypeEnum.RED_FLAG;
        final Cell cellToFlag = Cell.builder().id(cellId).build();
        final Cell flaggedCell = Cell.builder().id(cellId).isFlagged(true).flagType(redFlag).build();
        when(gameServiceMock.flagCell(gameId, cellToFlag, redFlag)).thenReturn(flaggedCell);

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/flag-cell?flagType=" + redFlag)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToFlag))).andReturn();

        final Cell responseCell = mapFromJson(result.getResponse().getContentAsString(), Cell.class);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responseCell);
        assertEquals(cellId, responseCell.getId());
        assertTrue(responseCell.isFlagged());
        assertEquals(redFlag, responseCell.getFlagType());
    }

    @Test
    public void testRevealCell_withNonValidGameId_shouldThrowGameNotFoundException() throws Exception {

        final int gameId = 1;
        final Cell cellToReveal = Cell.builder().build();

        when(gameServiceMock.findById(gameId)).thenThrow(new GameNotFoundException(gameId));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(GameNotFoundException.class.getCanonicalName()));
        assertEquals("The game " + gameId + " was not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testRevealCell_withFinishedGameId_shouldThrowInvalidGameStatusException() throws Exception {

        final int gameId = 1;
        final Cell cellToReveal = Cell.builder().build();
        when(gameServiceMock.findById(gameId)).thenThrow(new InvalidGameStatusException(GameStatusEnum.FAILED));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(InvalidGameStatusException.class.getCanonicalName()));
        assertEquals("The game has finished [" + GameStatusEnum.FAILED + "], please create a new one", result.getResolvedException().getMessage());
    }

    @Test
    public void testRevealCell_withInvalidCellData_shouldThrowExistingCellException() throws Exception {

        final int gameId = 1;
        final int cellId = 2;
        final Cell cellToReveal = Cell.builder().build();
        final Game gameFound = mock(Game.class);
        when(gameServiceMock.findById(gameId)).thenReturn(gameFound);
        when(gameServiceMock.revealCell(gameFound, cellToReveal)).thenThrow(new ExistingCellException(cellId));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(ExistingCellException.class.getCanonicalName()));
        assertEquals("The cell " + cellId + " already exists", result.getResolvedException().getMessage());
    }

    @Test
    public void testRevealCell_withMineCellCoordinates_shouldThrowGameOverException() throws Exception {

        final int gameId = 1;
        final int cellId = 2;
        final Cell cellToReveal = Cell.builder().build();
        final Game gameFound = mock(Game.class);
        when(gameServiceMock.findById(gameId)).thenReturn(gameFound);
        when(gameServiceMock.revealCell(gameFound, cellToReveal)).thenThrow(new GameOverException());

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(GameOverException.class.getCanonicalName()));
        assertEquals("The cell had a mine ! Game Over !", result.getResolvedException().getMessage());
    }

    @Test
    public void testRevealCell_withInvalidCellCoordinates_shouldThrowCellNotFoundException() throws Exception {

        final int gameId = 1;
        final Cell cellToReveal = Cell.builder().build();
        final Game gameFound = mock(Game.class);
        when(gameServiceMock.findById(gameId)).thenReturn(gameFound);
        when(gameServiceMock.revealCell(gameFound, cellToReveal)).thenThrow(new CellNotFoundException(cellToReveal));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getClass().getName().equals(CellNotFoundException.class.getCanonicalName()));
        assertEquals("The cell " + cellToReveal + " was not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testRevealCell_withValidCellCoordinates_shouldCompleteGameWithoutMines() throws Exception {

        final int gameId = 1;
        final Cell cellToReveal = Cell.builder().id(1).build();
        final Cell cellRevealed = Cell.builder().id(1).isRevealed(true).build();
        final Game gameFound = mock(Game.class);
        when(gameFound.getStatus()).thenReturn(GameStatusEnum.COMPLETED);
        when(gameServiceMock.findById(gameId)).thenReturn(gameFound);
        when(gameServiceMock.revealCell(gameFound, cellToReveal)).thenReturn(cellRevealed);

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.put("/games/" + gameId + "/reveal-cell")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(cellToReveal))).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(cellRevealed.getId(), cellToReveal.getId());
        assertTrue(cellRevealed.isRevealed());
        verify(gameServiceMock, times(1)).saveGame(gameFound);
    }

}
