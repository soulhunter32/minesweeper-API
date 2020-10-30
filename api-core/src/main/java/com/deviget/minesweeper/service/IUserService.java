package com.deviget.minesweeper.service;

import com.deviget.minesweeper.exception.ExistingUserException;
import com.deviget.minesweeper.exception.UserNotFoundException;
import com.deviget.minesweeper.model.dto.User;

public interface IUserService {
	User findById(int userId) throws UserNotFoundException;

	User saveUser(User user) throws ExistingUserException;
}
