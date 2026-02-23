package com.securefromscratch.busybee.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.UUID;

@Component
public class UsersPrePopulate {
    private static final SecureRandom s_random = new SecureRandom();

    @Bean
    CommandLineRunner populateInitialUsers(UsersStorage usersStorage, PasswordEncoder passwordEncoder) {
        return args -> {
            populateUser("Yariv", new String[] {"ADMIN"}, usersStorage, passwordEncoder);
            populateUser("Or",new String[] {"CREATOR"}, usersStorage, passwordEncoder);
            populateUser("Eyal",new String[] {"TRIAL"}, usersStorage, passwordEncoder);
        };
    }

    private void populateUser(String username, String[] roles, UsersStorage usersStorage, PasswordEncoder passwordEncoder) {
        // Check if user already exists
        if (usersStorage.findByUsername(username).isPresent()) {
            System.out.println("User already exists: " + username);
            return;
        }

        String plainPassword = generatePwd();
        try {
            UserAccount newAccount = usersStorage.createUser(username, plainPassword, roles);
            System.out.println("********** User created: **********");
            System.out.println(newAccount.getUsername());
            System.out.println("********** Plain Password: **********");
            System.out.println(plainPassword);
        } catch (IOException e) {
            System.err.println("Error creating user " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static final String PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";

    private String generatePwd() {
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            sb.append(PASSWORD_CHARS.charAt(s_random.nextInt(PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }
}
