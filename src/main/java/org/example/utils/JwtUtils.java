package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class JwtUtils {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final String ISSUER = "AuctionIT";
    private static final long EXPIRATION_TIME_MS = 900_000;


    public static String generateToken(String userId, String firstName, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", firstName);
        claims.put("email", email);
        return createToken(userId, claims);
    }

    private static String createToken(String subject, Map<String, Object> customClaims) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + EXPIRATION_TIME_MS
                ))
                .addClaims(customClaims)      // Custom claims map
                .signWith(SECRET_KEY)
                .compact();
    }
    public static boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    public static String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static String refreshToken(String token) {
        Claims claims = parseToken(token);
        return createToken(claims.getSubject(), claims);
    }

}
