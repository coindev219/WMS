package com.warehouse.model;

public class OAuthRequest {
    private String username;
    private String password;

    public OAuthRequest() {
    }


    public OAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    @Override
    public String toString() {
        return "OAuthRequest [password=" + password + ", username=" + username + "]";
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
