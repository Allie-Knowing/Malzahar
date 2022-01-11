package com.foreveryone.knowing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String access_token;

    private final String refresh_token;
}
