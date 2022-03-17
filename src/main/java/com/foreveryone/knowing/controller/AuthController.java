package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.error.exceptions.UnsupportedProviderException;
import com.foreveryone.knowing.service.AuthService;
import com.foreveryone.knowing.oauth.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@RequestParam String code, @RequestParam OauthProvider provider) {
        switch (provider) {
            case GOOGLE:
                return authService.googleLogin(code);
            case NAVER:
                return authService.naverLogin(code);
            case FACEBOOK:
                return authService.facebookLogin(code);
            case KAKAO:
                return authService.kakaoLogin(code);
            default:
                throw new UnsupportedProviderException("[ " + provider + " ] 는 지원하지 않는 provider 입니다~!");
        }

    }
}
