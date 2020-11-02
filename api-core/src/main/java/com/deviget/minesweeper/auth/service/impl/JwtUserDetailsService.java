package com.deviget.minesweeper.auth.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.secret-pass}")
    private String passBcrypted;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (secret.equals(username)) {
            return new User(secret, passBcrypted, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
