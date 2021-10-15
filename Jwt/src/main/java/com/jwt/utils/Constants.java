package com.jwt.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class Constants {

    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final long EXPIRATION_TIME = 10 * 86400 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_KEY = "Authorization";

}
