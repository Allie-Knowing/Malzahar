package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.error.exceptions.UnsupportedProviderException;
import com.foreveryone.knowing.service.AuthService;
import com.foreveryone.knowing.util.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse login(@RequestParam String code, @RequestParam OauthProvider provider) {
        switch (provider) {
            case GOOGLE:
                return authService.googleLogin(code);
            case NAVER:
                return authService.naverLogin(code);
            default:
                throw new UnsupportedProviderException("지원하지 않는 provider 입니다~!");
        }

    }
}
