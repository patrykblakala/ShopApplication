package com.patryk.shop.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String BEARER = "Bearer ";
    public static final String COMMA = ",";
    public static final String TOKEN = "token";
    public static final String AUTHORITIES = "authorities";
    public static final String ROLE_USER = "ROLE_USER";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24;
}
