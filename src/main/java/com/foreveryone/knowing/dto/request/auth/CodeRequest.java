package com.foreveryone.knowing.dto.request.auth;

import com.foreveryone.knowing.error.exceptions.auth.OauthBadRequestException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
public class CodeRequest {
    @NotEmpty
    private String code;

    private String redirectUri;

    public String getRedirectUri() {
        String redirectUri = this.redirectUri;

        if(redirectUri == null || redirectUri.trim().isEmpty())
            throw new OauthBadRequestException("요청의 body 에 필수 redirect_uri 가 비었습니다.");

        return redirectUri;
    }

    public String getCode() {
        return this.code;
    }

}
