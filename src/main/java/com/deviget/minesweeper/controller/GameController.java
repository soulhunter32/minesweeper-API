package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.ExistingCellException;
import com.deviget.minesweeper.exception.GameNotFoundException;
import com.deviget.minesweeper.model.dto.Cell;
import com.deviget.minesweeper.model.dto.Game;
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

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> revealCell(@RequestParam("xCoordinate") int xCoordinate,
	                                         @RequestParam("yCoordinate") int yCoordinate) {
		log.info("revealCell:: Entering Reveal Cell endpoint with coordinates: [x,y] -> [{}{}]...", xCoordinate,
				yCoordinate);

		return ResponseEntity.ok("Checked ok!");
	}

	/**
	 * Flags a cell in the current game's board.-
	 */
	@PutMapping(value = "/{gameId}/flag-cell", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cell> flagCell(@NotNull @PathVariable Integer gameId, @NotNull @RequestBody Cell flagCell) {
		log.info("flagCell:: Entering Flag Cell for game {} and cell coordinates [{},{}] ...", gameId,
				flagCell.getXCoordinate(), flagCell.getYCoordinate());
		Game game;
		try {
			game = gameService.findById(gameId);
		} catch (GameNotFoundException e) {
			log.error("flagCell:: Game {} was not found !", gameId, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

		try {
			flagCell = gameService.flagCell(game, flagCell);
		} catch (GameNotFoundException e) {
			log.error("flagCell:: Cell {} was not found !", flagCell, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (ExistingCellException e) {
			log.error("flagCell:: Cell {} already exists !", flagCell, e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		return new ResponseEntity<Cell>(flagCell, HttpStatus.OK);
	}
}
