package com.foreveryone.knowing.oauth.client.google;

import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "googleAuthClient", url = "${oauth.google.auth_url}")
public interface GoogleAuthClient {

    @PostMapping(produces = "application/json", value = "/token")
    GoogleAuthResponse googleAuth(GoogleAuthRequest authRequest);
}
