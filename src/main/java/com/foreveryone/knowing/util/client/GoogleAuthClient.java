package com.foreveryone.knowing.util.client;

import com.foreveryone.knowing.util.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.util.dto.response.GoogleAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "googleAuthClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {

    @PostMapping(produces = "application/json", value = "/token")
    GoogleAuthResponse googleAuth(GoogleAuthRequest authRequest);
}
