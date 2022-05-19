package com.uns.ac.rs.userservice.jwt;

import com.uns.ac.rs.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generate(User u) {
        Claims claims = Jwts.claims();

        claims.put("id", u.getId());
        claims.put("iat", System.currentTimeMillis());
        claims.put("exp", ((long) claims.get("iat")) + expiration);
        claims.put("role", u.getRole());
        claims.put("email", u.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
