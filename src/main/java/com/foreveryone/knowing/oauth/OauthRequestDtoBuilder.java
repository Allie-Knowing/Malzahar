package com.foreveryone.knowing.oauth;

import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.NaverAuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class OauthRequestDtoBuilder {

    @Value(value = "${oauth.google.client_id}")
    private String GOOGLE_CLIENT_ID;
    @Value(value = "${oauth.google.client_secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value(value = "${oauth.google.redirect_uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value(value = "${oauth.naver.client_id}")
    private String NAVER_CLIENT_ID;
    @Value(value = "${oauth.naver.client_secret}")
    private String NAVER_CLIENT_SECRET;


    public GoogleAuthRequest getGoogle(String code) {
        return GoogleAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(GOOGLE_CLIENT_ID)
                .client_secret(GOOGLE_CLIENT_SECRET)
                .redirect_uri(GOOGLE_REDIRECT_URI)
                .grant_type("authorization_code")
                .build();
    }

    public NaverAuthRequest getNaver(String code) {
        return NaverAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(NAVER_CLIENT_ID)
                .client_secret(NAVER_CLIENT_SECRET)
                .grant_type("authorization_code")
                .build();
    }
}
