package com.foreveryone.knowing.security;

import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.error.exceptions.InvalidUserTokenException;
import com.foreveryone.knowing.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfigurationProperties jwtProperties;
    private final UserRepository userRepository;

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

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (checkToken(token)) {
            return token.substring(7);
        }
        return null;
    }

    public boolean isAccessToken(String token){
        return checkTokenType(token);
    }

    public Integer getId(String token) {
        try {
            String id = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
            return Integer.parseInt(id);
        } catch (Exception e) {
            throw new InvalidUserTokenException("잘못된 토큰입니다");
        }
    }

    private boolean checkTokenType(String token) {
        try {
            String type = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getHeader().get("typ").toString();
            return type.equals("access");
        } catch (Exception e) {
            throw new InvalidUserTokenException("잘못된 토큰입니다");
        }
    }

    public Authentication getAuthentication(String token) {
        Integer id = getId(token);
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new UsernamePasswordAuthenticationToken(value, "", getAuthorities()))
                .orElseGet(() -> new UsernamePasswordAuthenticationToken(null, "", null));
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    private Boolean checkToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }
}
