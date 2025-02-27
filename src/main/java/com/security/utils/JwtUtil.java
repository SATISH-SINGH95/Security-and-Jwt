package com.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "AJJDHRE3DDJFB334DFBEKBJKBE86JEBREGU4835798JHKDHUIFJJBKDJB8458DJFJKJDBVK";

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "satish@gmail.com");

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1))
                .claims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] decode = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenNotExpired(String token){
        Claims claims = getClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.after(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        if(username.equals(userDetails.getUsername()) && isTokenNotExpired(token)){
            return true;
        }
        return false;
    }

}
