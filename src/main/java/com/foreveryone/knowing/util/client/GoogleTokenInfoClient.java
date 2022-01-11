package com.foreveryone.knowing.util.client;

import com.foreveryone.knowing.util.dto.response.GoogleTokenInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleTokenInfo", url = "https://oauth2.googleapis.com/tokeninfo")
public interface GoogleTokenInfoClient {

    @GetMapping
    GoogleTokenInfoResponse getTokenInfo(@RequestParam String id_token);
}
