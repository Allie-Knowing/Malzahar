package com.foreveryone.knowing.oauth.client.naver;

import com.foreveryone.knowing.oauth.dto.request.NaverAuthRequest;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "naverAuthClient", url = "${oauth.naver.auth_url}")
public interface NaverAuthClient {

    @GetMapping(produces = "application/json", value = "/token")
    NaverAuthResponse naverAuth(@SpringQueryMap NaverAuthRequest authRequest);
}
