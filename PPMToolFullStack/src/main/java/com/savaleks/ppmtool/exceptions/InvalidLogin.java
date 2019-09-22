package com.savaleks.ppmtool.exceptions;

public class InvalidLogin {
    private String username;
    private String password;

    public InvalidLogin() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
