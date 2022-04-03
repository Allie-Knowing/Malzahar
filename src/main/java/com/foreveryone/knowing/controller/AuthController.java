package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.CodeRequest;
import com.foreveryone.knowing.dto.request.IdTokenRequest;
import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.error.exceptions.UnsupportedProviderException;
import com.foreveryone.knowing.service.AuthService;
import com.foreveryone.knowing.oauth.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@Valid @RequestBody IdTokenRequest idTokenRequest) {
        return authService.googleLogin(idTokenRequest.getIdToken());
    }


    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@Valid @RequestBody CodeRequest codeRequest, @RequestParam OauthProvider provider) {
        switch (provider) {
            case NAVER:
                return authService.naverLogin(codeRequest);
            case FACEBOOK:
                return authService.facebookLogin(codeRequest);
            case KAKAO:
                return authService.kakaoLogin(codeRequest);
            default:
                throw new UnsupportedProviderException("[ " + provider + " ] 는 지원하지 않는 provider 입니다~!");
        }

    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse tokenRefresh(@RequestHeader("Refresh-Token") String refreshToken) {
        System.out.println("refreshToken = " + refreshToken);
        return authService.tokenRefresh(refreshToken);
    }


}
