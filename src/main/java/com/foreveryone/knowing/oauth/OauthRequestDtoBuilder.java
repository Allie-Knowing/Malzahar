package com.foreveryone.knowing.oauth;

import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.NaverAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OauthRequestDtoBuilder {

    private final OauthConfigurationsProperties oauthConfigurationsProperties;

    public GoogleAuthRequest getGoogle(String code) {
        return GoogleAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(oauthConfigurationsProperties.getGoogle().getClient_id())
                .client_secret(oauthConfigurationsProperties.getGoogle().getClient_secret())
                .redirect_uri(oauthConfigurationsProperties.getGoogle().getRedirect_uri())
                .grant_type("authorization_code")
                .build();
    }

    public NaverAuthRequest getNaver(String code) {
        return NaverAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(oauthConfigurationsProperties.getNaver().getClient_id())
                .client_secret(oauthConfigurationsProperties.getNaver().getClient_secret())
                .grant_type("authorization_code")
                .build();
    }
}
