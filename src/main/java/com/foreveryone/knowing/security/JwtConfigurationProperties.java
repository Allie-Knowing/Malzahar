package com.foreveryone.knowing.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Base64;

@Getter
@ConstructorBinding
@ConfigurationProperties("auth.jwt")
public class JwtConfigurationProperties {

    private final String secret;
    private final Exp exp;

    public JwtConfigurationProperties(String secret, Exp exp) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.exp = exp;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Exp {
        private final Long access;
        private final Long refresh;
    }
}
