package com.securefromscratch.busybee.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@Service
public class UsernamePasswordDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordDetailsService.class);
    private final UsersStorage m_usersStorage;

    public UsernamePasswordDetailsService(UsersStorage usersStorage) {
        this.m_usersStorage = usersStorage;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = m_usersStorage.findByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.warn("Login failed: User not found - {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        LOGGER.info("User logged in: {}", username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getHashedPassword())
                .roles(user.getRoles())
                .build();
    }

    public boolean userExists(String username) {
        return m_usersStorage.findByUsername(username).isPresent();
    }

    public void createUser(String username, String password, String[] roles) throws IOException {
        m_usersStorage.createUser(username, password, roles);
    }
}
