package com.ecommerce.springbootecommerce.constant;

public class TokenConstant {
    private TokenConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final long JWT_ACCESS_TOKEN_EXPIRATION = 30000; // 30secs
    public static final int JWT_COOKIE_ACCESS_TOKEN_EXPIRATION = 3600; // 1h
    public static final long JWT_REFRESH_TOKEN_EXPIRATION = 259200; // 3days

    public static final String JWT_REFRESH_TOKEN = "refresh_token";
}
