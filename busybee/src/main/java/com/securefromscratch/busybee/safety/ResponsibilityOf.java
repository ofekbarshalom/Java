package com.securefromscratch.busybee.safety;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import com.securefromscratch.busybee.auth.UsersStorage;
import com.securefromscratch.busybee.auth.InvalidResponsibilityException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Schema(type = "Array", description = "List of usernames (each must exist and follow username format)")
public class ResponsibilityOf {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9 ]*$");
    
    private final List<String> value;

    @ConstructorBinding
    public ResponsibilityOf(List<String> usernames, UsersStorage usersStorage) {
        if (usernames == null) {
            this.value = List.of();
            return;
        }

        // Validate each username
        for (String username : usernames) {
            if (username == null || username.isBlank()) {
                throw new InvalidResponsibilityException("Username cannot be null or empty");
            }

            // Validate format
            if (!USERNAME_PATTERN.matcher(username).matches()) {
                throw new InvalidResponsibilityException(
                    String.format("Invalid username format '%s'. Must start with a letter and contain only letters, digits, and spaces.", username)
                );
            }

            // Check if user exists
            if (!usersStorage.findByUsername(username).isPresent()) {
                throw new InvalidResponsibilityException(
                    String.format("User '%s' does not exist", username)
                );
            }
        }

        this.value = usernames;
    }

    public List<String> getResponsibilities() {
        return value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value.stream().collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsibilityOf that = (ResponsibilityOf) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
