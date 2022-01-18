package com.foreveryone.knowing.oauth;

import com.foreveryone.knowing.oauth.dto.request.FacebookAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.KakaoAuthRequest;
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

    public FacebookAuthRequest getFacebook(String code) {
        return FacebookAuthRequest.builder()
                .client_id(oauthConfigurationsProperties.getFacebook().getClient_id())
                .redirect_uri(oauthConfigurationsProperties.getFacebook().getRedirect_uri())
                .client_secret(oauthConfigurationsProperties.getFacebook().getClient_secret())
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .build();
    }

    public KakaoAuthRequest getKakao(String code) {
        return KakaoAuthRequest.builder()
                .grant_type("authorization_code")
                .client_id(oauthConfigurationsProperties.getKakao().getClient_id())
                .redirect_uri(oauthConfigurationsProperties.getKakao().getRedirect_uri())
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_secret(oauthConfigurationsProperties.getKakao().getClient_secret())
                .build();
    }
}
