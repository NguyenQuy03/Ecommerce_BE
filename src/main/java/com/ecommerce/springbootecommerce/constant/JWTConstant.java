package com.ecommerce.springbootecommerce.constant;

public class JWTConstant {
    private JWTConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    public static final long JWT_ACCESS_TOKEN_EXPIRATION = 360000; // 6mins
    public static final int JWT_COOKIE_ACCESS_TOKEN_EXPIRATION = 3600; // 1h
    public static final long JWT_REFRESH_TOKEN_EXPIRATION = 259200; // 3days

}
