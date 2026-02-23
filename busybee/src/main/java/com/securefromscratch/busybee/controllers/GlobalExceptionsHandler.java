package com.securefromscratch.busybee.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.securefromscratch.busybee.auth.InvalidResponsibilityException;
import com.securefromscratch.busybee.storage.TaskNotFoundException;
import com.securefromscratch.busybee.storage.InsufficientDiskSpaceException;
import com.securefromscratch.busybee.storage.InvalidFileUploadException;
import com.securefromscratch.busybee.storage.CommentNotFoundException;
import org.owasp.safetypes.exception.TypeValidationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgument(IllegalArgumentException ex) {
        LOGGER.warn("IllegalArgumentException caught: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidResponsibilityException.class)
    public ResponseEntity<String> invalidResponsibility(InvalidResponsibilityException ex) {
        LOGGER.warn("InvalidResponsibilityException caught: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Void> taskNotFound(TaskNotFoundException ex) {
        LOGGER.warn("TaskNotFoundException caught: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(InsufficientDiskSpaceException.class)
    public ResponseEntity<String> insufficientDiskSpace(InsufficientDiskSpaceException ex) {
        LOGGER.error("InsufficientDiskSpaceException caught: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidFileUploadException.class)
    public ResponseEntity<String> invalidFileUpload(InvalidFileUploadException ex) {
        LOGGER.error("InvalidFileUploadException caught: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> ioException(IOException ex) {
        LOGGER.error("IOException caught: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: File operation failed");
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<String> messageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        LOGGER.warn("HttpMessageNotReadableException caught: {}", ex.getMessage());
        String message = ex.getMessage();
        if (message != null && message.contains("Password")) {
            return ResponseEntity.badRequest().body("Password must be between 8 and 100 characters");
        }
        if (message != null && message.contains("Name")) {
            return ResponseEntity.badRequest().body("Username must be between 1 and 20 characters");
        }
        return ResponseEntity.badRequest().body("Invalid input format");
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Void> commentNotFound(CommentNotFoundException ex) {
        LOGGER.warn("CommentNotFoundException caught: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(TypeValidationException.class)
    public ResponseEntity<String> typeValidation(TypeValidationException ex) {
        LOGGER.warn("TypeValidationException caught: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFound(UsernameNotFoundException ex) {
        LOGGER.warn("UsernameNotFoundException caught: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
}