package com.foreveryone.knowing.oauth.client.kakao;

import com.foreveryone.knowing.oauth.dto.request.KakaoAuthRequest;
import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "kakaoAuthClient", url = "https://kauth.kakao.com")
public interface KakaoAuthClient {

    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    KakaoAuthResponse kakaoAuth(MultiValueMap<String, String> request);
}
