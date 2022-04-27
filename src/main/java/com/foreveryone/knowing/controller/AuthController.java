package com.foreveryone.knowing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foreveryone.knowing.dto.request.auth.CodeRequest;
import com.foreveryone.knowing.dto.request.auth.GoogleLoginRequest;
import com.foreveryone.knowing.dto.request.auth.IdTokenRequest;
import com.foreveryone.knowing.dto.response.auth.TokenResponse;
import com.foreveryone.knowing.error.exceptions.UnsupportedProviderException;
import com.foreveryone.knowing.service.AuthService;
import com.foreveryone.knowing.oauth.utils.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse googleLogin(@Valid @RequestBody GoogleLoginRequest googleLoginRequest) {
        System.out.println("GOOGLE 로그인 요청");
        return authService.googleLogin(googleLoginRequest);
    }

    @PostMapping("/apple")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse appleLogin(@Valid @RequestBody IdTokenRequest idTokenRequest) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("APPLE 로그인 요청");
        return authService.appleLogin(idTokenRequest);
    }


    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@Valid @RequestBody CodeRequest codeRequest, @RequestParam OauthProvider provider) {
        System.out.println(provider + " 로그인 요청");
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
        System.out.println("토큰 리프레시 요청");
        return authService.tokenRefresh(refreshToken);
    }


}
