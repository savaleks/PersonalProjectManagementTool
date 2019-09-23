package com.savaleks.ppmtool.exceptions;

public class UsernameResponseException {

    private String username;

    public UsernameResponseException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
