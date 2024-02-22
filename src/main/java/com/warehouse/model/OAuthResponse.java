package com.warehouse.model;


public class OAuthResponse {
    private String token;

    public OAuthResponse() {
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }

    public OAuthResponse(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OAuthResponse [token=" + token + "]";
    }

    
    
}
