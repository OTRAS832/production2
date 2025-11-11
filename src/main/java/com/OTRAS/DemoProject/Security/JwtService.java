package com.OTRAS.DemoProject.Security;
 
import java.security.Key;

import java.util.Date;

import java.util.Map;

import java.util.function.Function;
 
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;
 
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
 
@Service

public class JwtService {
 
	private static final String SECRET_KEY = "kE2b9zP7hQxYv5fR1tUo8mC6sN4aJ3wL0gVdB2kF9xR1tP5eQ7yT8uZ9cH3nM6rA";
 
	 

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
 
 
    public String generateToken(String username, Map<String, Object> extraClaims) {

        return Jwts.builder()

                .setClaims(extraClaims)

                .setSubject(username)

                .setIssuedAt(new Date(System.currentTimeMillis()))

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day

                .signWith(getSigningKey(), SignatureAlgorithm.HS256)

                .compact();

    }
 
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }
 
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }
 
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }
 
    public boolean isTokenValid(String token, String username) {

        return (username.equals(extractUsername(token)) && !isTokenExpired(token));

    }
 
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }
 
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

}

 