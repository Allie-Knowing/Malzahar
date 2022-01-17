package com.foreveryone.knowing.oauth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacebookAuthRequest {

    private String code;

    private String client_id;

    private String client_secret;

    private String redirect_uri;
}
