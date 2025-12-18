package com.example.demo.Service;

import com.example.demo.Models.UserDTO;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Tables.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {
    private String keyString;
    private UserRepo userRepo;

    public JWTService(UserRepo userRepo){
        try {
            this.userRepo = userRepo;
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

    public String getRoleFromToken(String token){
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("role",String.class);
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.keyString);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Checking validity of token
    public boolean validateTokenClaims(String token) {
        try{
            final Claims claims = extractAllClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
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
        try {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        } catch (Exception e) {
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String refreshAccessToken(UserDTO userData) {
        User userEntityData = userRepo.findByEmail(userData.getEmail());
        if(!isTokenExpired(userEntityData.getRefreshToken())){
            return createAccessToken(userData.getEmail(), userData.getRole().name());
        }
        else{
            return null;
        }
    }
}
