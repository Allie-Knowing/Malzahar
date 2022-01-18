package com.foreveryone.knowing.oauth.client.facebook;

import com.foreveryone.knowing.oauth.dto.request.FacebookUserInfoRequest;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "facebookUserInfoClient", url = "https://graph.facebook.com")
public interface FacebookUserInfoClient {

    @GetMapping(value = "/me")
    FacebookUserInfoResponse facebookUserInfo(@SpringQueryMap FacebookUserInfoRequest request);
}
