package com.foreveryone.knowing.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfigurationProperties jwtProperties;

    public String generateAccessToken(Integer id) {
        return generateToken(id, "access", jwtProperties.getExp().getAccess());
    }

    public String generateRefreshToken(Integer id) {
        return generateToken(id, "refresh", jwtProperties.getExp().getRefresh());
    }

    private String generateToken(Integer id, String type, Long exp) {
        return Jwts.builder()
                .setHeaderParam("typ", type)
                .setIssuedAt(new Date())
                .setSubject(id.toString())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }
}
