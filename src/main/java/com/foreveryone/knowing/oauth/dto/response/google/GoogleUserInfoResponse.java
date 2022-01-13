package com.foreveryone.knowing.oauth.dto.response.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfoResponse {

    private String email;

    private String name;

    private String picture;

}
