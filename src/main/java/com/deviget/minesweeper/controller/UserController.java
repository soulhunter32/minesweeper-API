package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.service.IUserService;
import com.sun.istack.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	/**
	 * Creates a new user for the provided information
	 *
	 * @return a {@link ResponseEntity} of {@link User} with the new created game
	 */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@NotNull @RequestBody User user) {
		log.info("createUser:: Entering Create new user with data {} ...", user.toString());

		try {
			user = userService.saveUser(user);
		} catch (ExistingUserException e) {
			log.error("createGame:: User {} already exists, choose a new username !", user.getUsername(), e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

		return ResponseEntity.ok(user);
	}
}
