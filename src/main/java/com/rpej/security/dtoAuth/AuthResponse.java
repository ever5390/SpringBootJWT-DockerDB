package com.rpej.security.dtoAuth;

public class AuthResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public AuthResponse(Builder builder) {
        this.token = builder.token;
    }

    public static class Builder {
        String token;

        public AuthResponse.Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }
}
