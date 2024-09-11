package com.inversionesaraujo.api.service;

import java.util.Map;
import java.security.Key;
import java.util.function.Function;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface IJwt {
    String getToken(Map<String, Object> extractClaims, UserDetails userDetails);

    Key getKey();

    boolean isTokenValid(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

    Claims getAllClaims(String token);

    <T> T getClaim(String token, Function<Claims, T> claimsResolver);

    Date getExpiration(String token);

    boolean isTokenExpired(String token);
}
