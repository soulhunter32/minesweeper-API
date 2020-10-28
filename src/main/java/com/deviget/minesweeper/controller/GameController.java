package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
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

	/**
	 * Creates a new game for the provided user
	 *
	 * @return a {@link ResponseEntity} of {@link Game} with the new created game
	 */
	@PostMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> createGame(@NotNull @PathVariable("userId") Integer userId) {
		log.info("createGame:: Entering Create new game for user {} ...", userId);
		User user;

		try {
			user = userService.findById(userId);
		} catch (UserNotFoundException e) {
			log.error("createGame:: User {} was not found !", userId, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

		Game newGame = Game.builder().user(user).build();

		newGame = gameService.saveGame(newGame);

		return ResponseEntity.ok(newGame);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> revealCell(@RequestParam("xCoordinate") int xCoordinate,
	                                         @RequestParam("yCoordinate") int yCoordinate) {
		log.info("revealCell:: Entering Reveal Cell endpoint with coordinates: [x,y] -> [{}{}]...", xCoordinate,
				yCoordinate);

		return ResponseEntity.ok("Checked ok!");

	}
}
