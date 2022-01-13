package com.foreveryone.knowing.util.dto.response.naver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverUserInfoResponse {

    private Response response;

    @Getter
    public static class Response {
        private String email;

        private String profileImage;

        private String name;
    }
}
