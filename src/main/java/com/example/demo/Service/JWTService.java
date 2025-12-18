package com.example.demo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {
    private String keyString;
    public JWTService(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            this.keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String createRefreshToken(String username){
        Map <String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10*24*60*60*1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String createAccessToken(String username, String role){
        Map <String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 30*60*1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String getRoles(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("roles").toString();
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.keyString);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Checking validity of token
    public boolean validateToken(String token, UserDetails userDetails, String email) {
        boolean resultOne = email.equals(userDetails.getUsername());
        boolean resultTwo =  resultOne && !isTokenExpired(token);
        if(!resultTwo){
            //You check the refresh token
            int a  = 1+1;
        }
        return resultTwo;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    //Functions to extract the email from the token
    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
