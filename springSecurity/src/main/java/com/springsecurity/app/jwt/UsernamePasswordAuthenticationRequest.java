package com.springsecurity.app.jwt;

public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;

    public UsernamePasswordAuthenticationRequest() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
}