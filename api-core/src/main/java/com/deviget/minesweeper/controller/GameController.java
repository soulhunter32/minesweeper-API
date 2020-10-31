package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.service.IUserService;
import com.sun.istack.NotNull;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This controller handles games endpoints.-
 */
@RestController
@RequestMapping("/games")
@Log4j2
@Api(value = "Game API for Minesweeper Application", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    @Autowired
    private IGameService gameService;

    @Autowired
    private IUserService userService;

    /**
     * Flags a cell in the current game's board.-
     */
    @ApiOperation(value = "Flags a Cell with a Red Flag or Question Mark")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cell Flagged", response = Cell.class),
            @ApiResponse(code = 404, message = "Can not flag cell. Game not found"),
            @ApiResponse(code = 404, message = "Can not flag cell. Cell not found"),
            @ApiResponse(code = 409, message = "Can not flag cell. Game has finished"),
            @ApiResponse(code = 409, message = "Can not flag cell. The cell exists and can not be saved. Data conflict"),
            @ApiResponse(code = 409, message = "Can not flag cell. Can not flag an already revealed cell"),
    })
    @PutMapping(value = "/{gameId}/flag-cell", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cell> flagCell(@ApiParam(value = "Game ID. Required", required = true, type = "int")
                                         @NotNull @PathVariable final Integer gameId,
                                         @ApiParam(value = "FlagType. Required", allowableValues = "FLAG_TYPE ,QUESTION_MARK", required = true, type = "FlagTypeEnum")
                                         @NotNull @PathParam("flagType") final FlagTypeEnum flagType,
                                         @ApiParam(value = "The Cell with the coordinates to flag the cell. Required", required = true, type = "Cell")
                                         @NotNull @RequestBody Cell flagCell) {
        Objects.requireNonNull(flagType, "A flag type must be specified");

        log.info("flagCell:: Entering Flag Cell for game {} and cell coordinates [{},{}] ...", gameId,
                flagCell.getXCoordinate(), flagCell.getYCoordinate() + " and flag type " + flagType);
        final Game game;
        try {
            game = gameService.findById(gameId);
        } catch (final GameNotFoundException e) {
            log.error("flagCell:: Game {} was not found !", gameId, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (final InvalidGameStatusException e) {
            log.error("flagCell:: Game {} is in an finished state !", gameId, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

        try {
            flagCell = gameService.flagCell(game, flagCell, flagType);
        } catch (final GameNotFoundException e) {
            log.error("flagCell:: Cell {} was not found !", flagCell, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (final ExistingCellException e) {
            log.error("flagCell:: Cell {} already exists !", flagCell, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (final CellFlaggedException e) {
            log.error("flagCell:: Cell {} is already flagged and cannot be revealed !", flagCell, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

        return new ResponseEntity<>(flagCell, HttpStatus.OK);
    }


    /**
     * Reveals a cell in the current game's board.-
     */
    @ApiOperation(value = "Reveals a Cell in the current game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cell revealed", response = Cell.class),
            @ApiResponse(code = 404, message = "Can not reveal cell. Game not found"),
            @ApiResponse(code = 404, message = "Cell does not exists"),
            @ApiResponse(code = 409, message = "Can not reveal cell. Game has finished"),
            @ApiResponse(code = 409, message = "Can not reveal cell. The cell exists and can not be saved. Data conflict"),
            @ApiResponse(code = 500, message = "The flag had a mine. Boom ! GAME OVER !"),
    })
    @PutMapping(value = "/{gameId}/reveal-cell", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cell> revealCell(@ApiParam(value = "Game ID. Required", required = true, type = "int")
                                           @NotNull @PathVariable final Integer gameId,
                                           @ApiParam(value = "The Cell with the coordinates to reveal the cell. Required", required = true, type = "Cell")
                                           @NotNull @RequestBody Cell revealCell) {

        log.info("revealCell:: Entering Reveal Cell for game {} and cell coordinates [{},{}] ...", gameId,
                revealCell.getXCoordinate(), revealCell.getYCoordinate());
        final Game game;
        try {
            game = gameService.findById(gameId);
        } catch (final GameNotFoundException e) {
            log.error("revealCell:: Game {} was not found !", gameId, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (final InvalidGameStatusException e) {
            log.error("flagCell:: Game {} is in an finished state !", gameId, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

        try {
            revealCell = gameService.revealCell(game, revealCell);
        } catch (final GameNotFoundException e) {
            log.error("revealCell:: Cell {} was not found !", revealCell, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (final ExistingCellException e) {
            log.error("revealCell:: Cell {} already exists !", revealCell, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (final GameOverException e) {
            gameService.endGame(game);
            log.error("revealCell:: CELL {} HAD A MINE ! BOOM - GAME OVER !", revealCell, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        if (GameStatusEnum.COMPLETED.equals(game.getStatus())) {
            game.setEndTime(LocalDateTime.now());
            gameService.saveGame(game);
        }
        return new ResponseEntity<>(revealCell, HttpStatus.OK);
    }
}
