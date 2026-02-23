package com.securefromscratch.busybee.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class UsersStorage {
    private final Map<String, UserAccount> m_users = loadUsers();
    private final PasswordEncoder m_passwordEncoder;
    private static final String USERS_FILE = "users.dat";

    public UsersStorage(PasswordEncoder passwordEncoder) throws IOException, ClassNotFoundException {
        this.m_passwordEncoder = passwordEncoder;
    }

    public Optional<UserAccount> findByUsername(String username) {
        return Optional.ofNullable(m_users.get(username));
    }

    public UserAccount createUser(String username, String password, String[] roles) throws IOException {
        String hashedPassword = m_passwordEncoder.encode(password);
        UserAccount newAccount = new UserAccount(username, hashedPassword, roles);
        m_users.put(username, newAccount);
        try {
            saveUsers();
        } catch (IOException e) {
            m_users.remove(username); // undo
            throw e;
        }
        return newAccount;
    }

    private Map<String, UserAccount> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (Map<String, UserAccount>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, which is okay on first run.
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            // Log the exception and return an empty map
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void saveUsers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(m_users);
        }
    }
}
