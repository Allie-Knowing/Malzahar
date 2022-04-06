package com.foreveryone.knowing.oauth.client.apple;

import com.foreveryone.knowing.oauth.dto.response.ApplePublicKeyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "appleAuthClient", url = "${oauth.apple.auth_url}")
public interface AppleAuthClient {

    @GetMapping(value = "/auth/keys")
    ApplePublicKeyResponse appleKeys();
}
