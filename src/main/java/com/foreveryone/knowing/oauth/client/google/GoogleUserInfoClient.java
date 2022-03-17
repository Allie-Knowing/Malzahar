package com.foreveryone.knowing.oauth.client.google;

import com.foreveryone.knowing.oauth.dto.response.google.GoogleUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleUserInfo", url = "${oauth.google.userinfo_url}")
public interface GoogleUserInfoClient {

    @GetMapping(value = "/tokenInfo")
    GoogleUserInfoResponse getUserInfo(@RequestParam String id_token);
}
