package com.securefromscratch.busybee.controllers;

import com.securefromscratch.busybee.auth.UsernamePasswordDetailsService;
import com.securefromscratch.busybee.safety.Name;
import com.securefromscratch.busybee.safety.Password;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UsernamePasswordDetailsService usersService;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UsernamePasswordDetailsService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    public record NewUserCreationFields(@NotNull Name username, @NotNull Password password) {}
    public record ErrorCreatingUser(String error) {}
    public record UserCreationSuccess(String redirectTo) {}

    @PostMapping("/register")
    public ResponseEntity registerNewUser(
            @Valid @RequestBody NewUserCreationFields request
    ) {
        try {
            LOGGER.info("Registration attempt for username: {}", request.username);
            
            // Check if user already exists
            if (usersService.userExists(request.username.toString())) {
                LOGGER.warn("Registration failed: Username already exists - {}", request.username);
                return ResponseEntity.status(409).body(new ErrorCreatingUser("Username already exists"));
            }
            
            // Create user with "TRIAL" role
            usersService.createUser(request.username.toString(), request.password.toString(), new String[]{"TRIAL"});
            LOGGER.info("New user registered successfully: {}", request.username);
            
            return ResponseEntity.ok(new UserCreationSuccess("main.html"));
        }
        catch (IOException ex) {
            LOGGER.error("IOException during user registration for username: {} - {}", request.username, ex.getMessage());
            return ResponseEntity.status(500).body(new ErrorCreatingUser("Server error: " + ex.getMessage()));
        }
        catch (Throwable ex) {
            LOGGER.error("Error during user registration for username: {} - {}", request.username, ex.getMessage());
            return ResponseEntity.status(500).body(new ErrorCreatingUser("Unexpected error: " + ex.getMessage()));
        }
    }
}
