package org.crud.todo.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.crud.todo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public  boolean isTokenValid(String token) {
        return true;
    }
    public boolean validateJwtToken(String authToken) throws JwtException {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}" +  e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}" +  e.getMessage());

        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}" +  e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}" +  e.getMessage());
        }

        return false;
    }
}
