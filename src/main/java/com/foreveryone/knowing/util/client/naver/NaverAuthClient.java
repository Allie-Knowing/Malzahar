package com.foreveryone.knowing.util.client.naver;

import com.foreveryone.knowing.util.dto.request.NaverAuthRequest;
import com.foreveryone.knowing.util.dto.response.naver.NaverAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "naverAuthClient", url = "https://nid.naver.com/oauth2.0")
public interface NaverAuthClient {

    @GetMapping(produces = "application/json", value = "/token")
    NaverAuthResponse naverAuth(@SpringQueryMap NaverAuthRequest authRequest);
}
