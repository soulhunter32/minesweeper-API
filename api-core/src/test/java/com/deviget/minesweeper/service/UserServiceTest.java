package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.impl.UserService;
import com.deviget.minesweeper.util.ModelMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final String username = "testUser";

    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository userRepository;

    @Mock
    private ModelMapperUtils modelMapperUtils;

    @Test
    public void testFindById_withExistingId_shouldReturnUser() throws Exception {

        final int id = 10;
        final com.deviget.minesweeper.model.entity.User userEntity = mock(com.deviget.minesweeper.model.entity.User.class);
        final User savedUser = mock(User.class);
        when(savedUser.getId()).thenReturn(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(modelMapperUtils.map(userEntity, User.class)).thenReturn(savedUser);

        final User userFound = userService.findById(id);

        verify(userRepository).findById(id);
        assertNotNull(userFound);
        assertEquals(id, userFound.getId());
    }

    @Test(expected = ExistingUserException.class)
    public void testSaveUser_withExistingUsername_shouldThrowExistingUserException() throws ExistingUserException {

        final User userToSave = mock(User.class);
        final com.deviget.minesweeper.model.entity.User userEntity = mock(com.deviget.minesweeper.model.entity.User.class);
        when(userToSave.getUsername()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        userService.saveUser(userToSave);
    }

    @Test
    public void testSaveUser_withNonExistingUsername_shouldSaveUser() throws ExistingUserException {

        final int id = 5;

        final User userToSave = mock(User.class);

        final User savedUser = mock(User.class);
        when(savedUser.getUsername()).thenReturn(username);
        when(savedUser.getId()).thenReturn(id);

        final com.deviget.minesweeper.model.entity.User userEntity = mock(com.deviget.minesweeper.model.entity.User.class);
        when(userRepository.save(any())).thenReturn(userEntity);

        when(modelMapperUtils.map(any(User.class), any(Class.class))).thenReturn(userEntity);
        when(modelMapperUtils.map(any(com.deviget.minesweeper.model.entity.User.class), any(Class.class))).thenReturn(savedUser);

        final User returnedUser = userService.saveUser(userToSave);

        assertNotNull(returnedUser);
        assertEquals(id, returnedUser.getId());
        assertEquals(username, returnedUser.getUsername());
    }
}
