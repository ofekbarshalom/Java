package com.securefromscratch.busybee.safety;

import com.securefromscratch.busybee.auth.UsersStorage;
import com.securefromscratch.busybee.auth.UserAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResponsibilityOfTest {
    private UsersStorage mockUsersStorage;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        mockUsersStorage = mock(UsersStorage.class);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void validSingleUserIsAccepted() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf responsibility = new ResponsibilityOf(List.of("Alice"), mockUsersStorage);
        assertEquals(List.of("Alice"), responsibility.getResponsibilities());
    }

    @Test
    public void validMultipleUsersAreAccepted() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );
        when(mockUsersStorage.findByUsername("Bob")).thenReturn(
            Optional.of(new UserAccount("Bob", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );
        when(mockUsersStorage.findByUsername("Charlie")).thenReturn(
            Optional.of(new UserAccount("Charlie", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf responsibility = new ResponsibilityOf(List.of("Alice", "Bob", "Charlie"), mockUsersStorage);
        assertEquals(List.of("Alice", "Bob", "Charlie"), responsibility.getResponsibilities());
    }

    @Test
    public void usernameWithSpacesIsAccepted() {
        when(mockUsersStorage.findByUsername("Alice Smith")).thenReturn(
            Optional.of(new UserAccount("Alice Smith", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf responsibility = new ResponsibilityOf(List.of("Alice Smith"), mockUsersStorage);
        assertEquals(List.of("Alice Smith"), responsibility.getResponsibilities());
    }

    @Test
    public void usernameWithDigitsIsAccepted() {
        when(mockUsersStorage.findByUsername("User123")).thenReturn(
            Optional.of(new UserAccount("User123", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf responsibility = new ResponsibilityOf(List.of("User123"), mockUsersStorage);
        assertEquals(List.of("User123"), responsibility.getResponsibilities());
    }

    @Test
    public void nullListIsAccepted() {
        ResponsibilityOf responsibility = new ResponsibilityOf(null, mockUsersStorage);
        assertTrue(responsibility.getResponsibilities().isEmpty());
    }

    @Test
    public void emptyListIsAccepted() {
        ResponsibilityOf responsibility = new ResponsibilityOf(List.of(), mockUsersStorage);
        assertTrue(responsibility.getResponsibilities().isEmpty());
    }

    @Test
    public void nullUsernameThrows() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        List<String> list = new ArrayList<>();
        list.add(null);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(list, actualStorage);
        }, "Username cannot be null or empty");
    }

    @Test
    public void emptyUsernameThrows() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of(""), actualStorage);
        }, "Username cannot be null or empty");
    }

    @Test
    public void blankUsernameThrows() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("   "), actualStorage);
        }, "Username cannot be null or empty");
    }

    @Test
    public void usernameStartingWithDigitIsRejected() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("1Alice"), actualStorage);
        }, "Invalid username format: must start with letter");
    }

    @Test
    public void usernameStartingWithSpaceIsRejected() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of(" Alice"), actualStorage);
        }, "Invalid username format: must start with letter");
    }

    @Test
    public void usernameWithSpecialCharactersIsRejected() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("Alice@Smith"), actualStorage);
        }, "Invalid username format: special characters not allowed");
    }

    @Test
    public void usernameWithHyphensIsRejected() {
        UsersStorage actualStorage = mock(UsersStorage.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("Alice-Smith"), actualStorage);
        }, "Invalid username format: hyphens not allowed");
    }

    @Test
    public void nonExistentUserThrows() {
        when(mockUsersStorage.findByUsername("NonExistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("NonExistent"), mockUsersStorage);
        }, "User does not exist");
    }

    @Test
    public void oneValidUserAndOneInvalidUserThrows() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );
        when(mockUsersStorage.findByUsername("NonExistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            new ResponsibilityOf(List.of("Alice", "NonExistent"), mockUsersStorage);
        }, "Non-existent user should cause exception");
    }

    @Test
    public void toStringReturnsCommaSeparatedUsernames() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );
        when(mockUsersStorage.findByUsername("Bob")).thenReturn(
            Optional.of(new UserAccount("Bob", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf responsibility = new ResponsibilityOf(List.of("Alice", "Bob"), mockUsersStorage);
        assertEquals("Alice, Bob", responsibility.toString());
    }

    @Test
    public void equalsComparesLists() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf r1 = new ResponsibilityOf(List.of("Alice"), mockUsersStorage);
        ResponsibilityOf r2 = new ResponsibilityOf(List.of("Alice"), mockUsersStorage);
        assertEquals(r1, r2);
    }

    @Test
    public void hashCodeIsConsistent() {
        when(mockUsersStorage.findByUsername("Alice")).thenReturn(
            Optional.of(new UserAccount("Alice", passwordEncoder.encode("pass"), new String[]{"USER"}))
        );

        ResponsibilityOf r1 = new ResponsibilityOf(List.of("Alice"), mockUsersStorage);
        ResponsibilityOf r2 = new ResponsibilityOf(List.of("Alice"), mockUsersStorage);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
