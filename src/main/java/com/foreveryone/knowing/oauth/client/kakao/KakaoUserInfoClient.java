package com.foreveryone.knowing.oauth.client.kakao;

import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "kakaoUserInfoClient", url = "${oauth.kakao.userinfo_url}")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", produces = "application/json")
    KakaoUserInfoResponse kakaoUserInfo(@RequestHeader(value = "Authorization") String authorization);
}
