package com.securefromscratch.busybee.auth;

import java.io.Serializable;

public class UserAccount implements Serializable {
    private String username;
    private String hashedPassword;
    private String[] roles;
    private boolean enabled = true;

    public UserAccount(String username, String hashedPassword, String[] roles) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String[] getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
