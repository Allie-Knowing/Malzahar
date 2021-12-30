package com.foreveryone.knowing.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthDetailService authDetailService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.exp}")
    private Long accessExp;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private static final String PREFIX = "Bearer ";

    public String generateAccessToken(Integer id) {
        return generateToken(id, "access", accessExp);
    }

    public String generateRefreshToken(Integer id) {
        return generateToken(id, "refresh", refreshExp);
    }

    private String generateToken(Integer id, String type, Long exp) {
        return Jwts.builder()
                .setHeaderParam("typ", type)
                .setIssuedAt(new Date())
                .setSubject(id.toString())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
