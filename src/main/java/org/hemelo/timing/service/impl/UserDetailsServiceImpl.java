package org.hemelo.timing.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        // For simplicity, using a hardcoded user. In a real application, fetch user details from a database.
        if ("user".equals(username)) {
            return new User("user", "$2a$10$Dow1Q9Q9Q9Q9Q9Q9Q9Q9QO", new ArrayList<>()); // password: password
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
