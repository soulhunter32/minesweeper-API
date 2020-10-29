package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.service.IBoardService;
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
 * This controller handles user endpoints.-
 */
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IBoardService boardService;

	/**
	 * Creates a new user for the provided information
	 *
	 * @return a {@link ResponseEntity} of {@link User} with the new created game
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@NotNull @RequestBody User user) {
		log.info("createUser:: Entering Create new user with data {} ...", user.toString());

		try {
			user = userService.saveUser(user);
		} catch (ExistingUserException e) {
			log.error("createUser:: User {} already exists, choose a new username !", user.getUsername(), e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	/**
	 * Creates a new game for the provided user
	 *
	 * @return a {@link ResponseEntity} of {@link Game} with the new created game
	 */
	@PostMapping(value = "/{userId}/game", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
			{MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Game> createGame(@NotNull @PathVariable("userId") Integer userId,
	                                       @NotNull @RequestBody BoardSettings boardSettings) {
		log.info("createGame:: Entering Create new newGame for user {} ...", userId);
		User user;

		try {
			user = userService.findById(userId);
		} catch (UserNotFoundException e) {
			log.error("createGame:: User {} was not found !", userId, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

		Game newGame;
		try {
			newGame = gameService.createGame(user, boardSettings);
		} catch (InvalidBoardSettingsException e) {
			log.error("createGame:: There is an error on game settings", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}

		log.info("createGame:: New game created:  {} ...", newGame);

		return new ResponseEntity<Game>(newGame, HttpStatus.CREATED);
	}
}