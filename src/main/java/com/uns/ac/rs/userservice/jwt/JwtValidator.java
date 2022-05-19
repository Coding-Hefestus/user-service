package com.uns.ac.rs.userservice.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public boolean isValid(String token) {

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            if (tokenExpired(body)) return false;
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public Integer extractUserId(String jwt){
        Integer userId = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
            userId = (int) body.get("id");

        } catch (Exception e) {
            System.out.println(e);
        }

        return userId;
    }

    private boolean tokenExpired(Claims claims) {

        return (System.currentTimeMillis() - ((long) claims.get("iat"))) >= expiration;
    }
}
