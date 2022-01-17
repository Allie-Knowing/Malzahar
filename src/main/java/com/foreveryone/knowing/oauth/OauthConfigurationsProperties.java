package com.foreveryone.knowing.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(value = "oauth")
public class OauthConfigurationsProperties {

    private OauthProperty google;
    private OauthProperty naver;
    private OauthProperty facebook;
    private OauthProperty kakao;

    @Getter
    @Setter
    public static class OauthProperty {
        private String client_id;
        private String client_secret;
        private String redirect_uri;
    }

}
