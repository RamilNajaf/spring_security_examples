package com.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {




    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("JwtApp")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .signWith(Constants.key)
                .compact();


    }

   public boolean  tokenValidate(String token ){
       if (getUsernameFromToken(token) != null && isExpired(token)) {
           return true;
       }
       return false;

    }
    public  String getUsernameFromToken(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();

    }
    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(Constants.key).parseClaimsJws(token).getBody();
    }

}
