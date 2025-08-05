package com.backend.TrackMoney.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {

    private static final String SECRET = "myverysecretkeyformyjwtproject1234"; // 32+ chars for HS256
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate token using email (username)
    public String generateToken(String email) {
        return Jwts.builder()
               .setSubject(email)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(key, SignatureAlgorithm.HS256)
               .compact();
    }

    // Validate token (signature & expiration)
    public boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }

    //  Get username (email) from token
    public String getUsernameByToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Get token expiration
    public Date getTokenExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //  Check if token is expired
    public boolean isTokenExpired(String token) {
        return getTokenExpiration(token).before(new Date());
    }

    //  Helper: extract specific claim
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //  Helper: get full claims payload
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
