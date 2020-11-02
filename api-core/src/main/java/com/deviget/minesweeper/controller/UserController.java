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
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles user endpoints.-
 */
@RestController
@RequestMapping("/users")
@Log4j2
@Api(value = "User API endpoint for Minesweeper Application", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ApiOperation(value = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created", response = User.class),
            @ApiResponse(code = 409, message = "The user already exists for the username provided"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@ApiParam(value = "The user to crate. Required", required = true, type = "User")
                                           @NotNull @RequestBody User user) throws ExistingUserException {
        log.info("createUser:: Entering Create new user with data {} ...", user.toString());

        try {
            user = userService.saveUser(user);
        } catch (final ExistingUserException e) {
            log.error("createUser:: User {} already exists, choose a new username !", user.getUsername(), e);
            throw e;
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Creates a new game for the provided user
     *
     * @return a {@link ResponseEntity} of {@link Game} with the new created game
     */
    @ApiOperation(value = "Creates a new game for the supplied user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Game created"),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 500, message = "Game settings are not correct"),
    })
    @PostMapping(value = "/{userId}/games", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =
            {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> createGame(@ApiParam(value = "User ID. Required", required = true, type = "int")
                                           @NotNull @PathVariable("userId") final Integer userId,
                                           @ApiParam(value = "Board settings. Required", required = true, type = "BoardSettings")
                                           @NotNull @RequestBody final BoardSettings boardSettings) throws UserNotFoundException, InvalidBoardSettingsException {
        log.info("createGame:: Entering Create new newGame for user {} ...", userId);

        Game newGame = null;
        try {
            newGame = gameService.createGame(userId, boardSettings);
        } catch (final InvalidBoardSettingsException e) {
            log.error("createGame:: There is an error on game settings", e);
            throw e;
        } catch (final UserNotFoundException e) {
            log.error("createGame:: User {} was not found !", userId, e);
            throw e;
        }

        log.info("createGame:: New game created:  {} ...", newGame);

        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }
}
