package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.service.AuthService;
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
    public TokenResponse google(@RequestParam String code) {
        return authService.googleUserInfo(code);
    }
}
