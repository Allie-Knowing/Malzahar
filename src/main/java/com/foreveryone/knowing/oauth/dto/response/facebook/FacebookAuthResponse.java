package com.foreveryone.knowing.oauth.dto.response.facebook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FacebookAuthResponse {

    private String accessToken;

    private String tokenType;

}

