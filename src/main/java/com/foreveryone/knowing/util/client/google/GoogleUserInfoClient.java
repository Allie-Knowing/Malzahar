package com.foreveryone.knowing.util.client.google;

import com.foreveryone.knowing.util.dto.response.google.GoogleUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleUserInfo", url = "https://oauth2.googleapis.com/tokeninfo")
public interface GoogleUserInfoClient {

    @GetMapping
    GoogleUserInfoResponse getUserInfo(@RequestParam String id_token);
}
