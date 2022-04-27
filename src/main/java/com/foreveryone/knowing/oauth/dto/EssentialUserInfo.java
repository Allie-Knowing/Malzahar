package com.foreveryone.knowing.oauth.dto;

import com.foreveryone.knowing.oauth.utils.OauthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EssentialUserInfo {

    private final String email;

    private final String picture;

    private final String name;

    private final OauthProvider provider;

}
