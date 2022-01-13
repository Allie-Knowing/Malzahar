package com.foreveryone.knowing.util.client.naver;

import com.foreveryone.knowing.util.dto.response.naver.NaverUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "naverUserInfoClient", url = "https://openapi.naver.com")
public interface NaverUserInfoClient {

    @GetMapping(value = "/v1/nid/me")
    NaverUserInfoResponse naverUserInfo(@RequestHeader(value = "Authorization") String authorization);
}
