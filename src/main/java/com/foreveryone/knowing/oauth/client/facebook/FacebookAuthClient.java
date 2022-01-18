package com.foreveryone.knowing.oauth.client.facebook;

import com.foreveryone.knowing.oauth.dto.request.FacebookAuthRequest;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "facebookAuthClient", url = "https://graph.facebook.com")
public interface FacebookAuthClient {

    @GetMapping(value = "/v12.0/oauth/access_token")
    FacebookAuthResponse facebookAuth(@SpringQueryMap FacebookAuthRequest authRequest);
}
