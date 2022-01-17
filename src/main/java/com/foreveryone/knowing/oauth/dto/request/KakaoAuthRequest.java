package com.foreveryone.knowing.oauth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoAuthRequest {

    private String grant_type;

    private String client_id;

    private String redirect_uri;

    private String code;

    private String client_secret;
}
