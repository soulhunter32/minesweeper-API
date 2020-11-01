package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.exception.InvalidBoardSettingsException;
import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.BoardSettings;
import com.deviget.minesweeper.model.dto.Game;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.service.impl.BoardService;
import com.deviget.minesweeper.service.impl.GameService;
import com.deviget.minesweeper.service.impl.UserService;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    protected MockMvc apiMock;

    @MockBean
    protected GameService gameServiceMock;

    @MockBean
    protected UserService userServiceMock;

    @MockBean
    protected BoardService boardServiceMock;

    @Test
    public void testCreateUser_withExistingUsername_shouldThrowExistingUserException() throws Exception {

        final String username = "user01";

        final User inputUser = User.builder().username(username).build();
        when(userServiceMock.saveUser(inputUser)).thenThrow(new ExistingUserException(username));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(inputUser))).andReturn();

        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getCause() instanceof ExistingUserException);
        assertEquals("The user " + username + " already exists", result.getResolvedException().getCause().getMessage());
    }

    @Test
    public void testCreateUser_withNewUsername_shouldSave() throws Exception {

        final String username = "user01";
        final int userId = 1;
        final User inputUser = User.builder().username(username).build();
        final User savedUser = User.builder().username(username).id(userId).build();
        when(userServiceMock.saveUser(inputUser)).thenReturn(savedUser);

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(inputUser))).andReturn();

        final User responseUser = mapFromJson(result.getResponse().getContentAsString(), User.class);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertNotNull(responseUser);
        assertEquals(username, responseUser.getUsername());
    }

    @Test
    public void testCreateGame_withInvalidSettings_shouldThrowInvalidBoardSettingsException() throws Exception {

        final int userId = 1;
        final BoardSettings boardSettings = BoardSettings.builder().build();
        when(gameServiceMock.createGame(userId, boardSettings)).thenThrow(new InvalidBoardSettingsException(Strings.EMPTY));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.post("/users/" + userId + "/game")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(boardSettings))).andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getCause() instanceof InvalidBoardSettingsException);
        assertEquals(Strings.EMPTY, result.getResolvedException().getCause().getMessage());
    }

    @Test
    public void testCreateGame_withInvalidUserId_shouldThrowUserNotFoundException() throws Exception {

        final int userId = 1;
        final BoardSettings boardSettings = BoardSettings.builder().build();
        when(gameServiceMock.createGame(userId, boardSettings)).thenThrow(new UserNotFoundException(userId));

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.post("/users/" + userId + "/game")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(boardSettings))).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().getCause() instanceof UserNotFoundException);
        assertEquals("The user " + userId + " was not found", result.getResolvedException().getCause().getMessage());
    }

    @Test
    public void testCreateGame_withValidUserIdAndSettings_shouldCreateSuccessfullyGame() throws Exception {

        final int userId = 1;
        final BoardSettings boardSettings = BoardSettings.builder().build();
        final Game createdGame = Game.builder().id(userId).build();
        when(gameServiceMock.createGame(userId, boardSettings)).thenReturn(createdGame);

        final MvcResult result = apiMock.perform(MockMvcRequestBuilders.post("/users/" + userId + "/game")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(mapToJson(boardSettings))).andReturn();

        final Game responseGame = mapFromJson(result.getResponse().getContentAsString(), Game.class);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertNotNull(responseGame);
    }
}
