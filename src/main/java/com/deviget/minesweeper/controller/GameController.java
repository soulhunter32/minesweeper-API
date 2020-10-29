package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.service.IUserService;
import com.sun.istack.NotNull;
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
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IUserService userService;

	/**
	 * Flags a cell in the current game's board.-
	 */
	@PutMapping(value = "/{gameId}/flag-cell", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cell> flagCell(@NotNull @PathVariable Integer gameId,
	                                     @NotNull @PathParam("flagType") FlagTypeEnum flagType,
	                                     @NotNull @RequestBody Cell flagCell) {
		Objects.requireNonNull(flagType, "A flag type must be specified");

		log.info("flagCell:: Entering Flag Cell for game {} and cell coordinates [{},{}] ...", gameId,
				flagCell.getXCoordinate(), flagCell.getYCoordinate() + " and flag type " + flagType);
		Game game;
		try {
			game = gameService.findById(gameId);
		} catch (GameNotFoundException e) {
			log.error("flagCell:: Game {} was not found !", gameId, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (InvalidGameStatusException e) {
			log.error("flagCell:: Game {} is in an finished state !", gameId, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		try {
			flagCell = gameService.flagCell(game, flagCell, flagType);
		} catch (GameNotFoundException e) {
			log.error("flagCell:: Cell {} was not found !", flagCell, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (ExistingCellException e) {
			log.error("flagCell:: Cell {} already exists !", flagCell, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (CellFlaggedException e) {
			log.error("flagCell:: Cell {} is already flagged and cannot be revealed !", flagCell, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		return new ResponseEntity<Cell>(flagCell, HttpStatus.OK);
	}


	/**
	 * Reveals a cell in the current game's board.-
	 */
	@PutMapping(value = "/{gameId}/reveal-cell", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cell> revealCell(@NotNull @PathVariable Integer gameId,
	                                       @NotNull @RequestBody Cell revealCell) {

		log.info("revealCell:: Entering Reveal Cell for game {} and cell coordinates [{},{}] ...", gameId,
				revealCell.getXCoordinate(), revealCell.getYCoordinate());
		Game game;
		try {
			game = gameService.findById(gameId);
		} catch (GameNotFoundException e) {
			log.error("revealCell:: Game {} was not found !", gameId, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (InvalidGameStatusException e) {
			log.error("flagCell:: Game {} is in an finished state !", gameId, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		try {
			revealCell = gameService.revealCell(game, revealCell);
		} catch (GameNotFoundException e) {
			log.error("revealCell:: Cell {} was not found !", revealCell, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (ExistingCellException e) {
			log.error("revealCell:: Cell {} already exists !", revealCell, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (GameOverException e) {
			gameService.endGame(game);
			log.error("revealCell:: CELL {} HAD A MINE ! BOOM - GAME OVER !", revealCell, e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}

		if (GameStatusEnum.COMPLETED.equals(game.getStatus())) {
			game.setEndTime(LocalDateTime.now());
			gameService.saveGame(game);
		}
		return new ResponseEntity<Cell>(revealCell, HttpStatus.OK);
	}
}
