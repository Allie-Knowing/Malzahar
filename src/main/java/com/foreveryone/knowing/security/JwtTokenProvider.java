package com.foreveryone.knowing.security;

import com.foreveryone.knowing.error.exceptions.InvalidUserTokenException;
import com.foreveryone.knowing.security.auth.AuthDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authDetailService.loadUserByUsername(this.getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getId(String token) {
        return getClaims(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {
        try {
            return getClaims(jwtToken).getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            throw new InvalidUserTokenException("잘못된 토큰입니다.");
        }
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
