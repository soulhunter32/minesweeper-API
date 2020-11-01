package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.User;
import com.deviget.minesweeper.repository.IUserRepository;
import com.deviget.minesweeper.service.IUserService;
import com.deviget.minesweeper.util.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service in charge of all user related functionalities.-
 */
@Log4j2
@Service
public class UserService implements IUserService {

    @Autowired
    private ModelMapperUtils modelMapperUtils;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User findById(final int userId) throws UserNotFoundException {
        return modelMapperUtils.map((userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId))),
                User.class);
    }

    /**
     * Saves the current User.-
     *
     * @param user the user data to save
     * @return the saved user
     */
    @Override
    public User saveUser(final User user) throws ExistingUserException {

        if (Objects.isNull(userRepository.findByUsername(user.getUsername()))) {
            return modelMapperUtils.map(userRepository.save(modelMapperUtils.map(user,
                    com.deviget.minesweeper.model.entity.User.class)), User.class);
        } else {
            throw new ExistingUserException(user.getUsername());
        }
    }
}
