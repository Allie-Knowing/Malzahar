package com.foreveryone.knowing.oauth.dto.response.facebook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FacebookUserInfoResponse {

    private String name;

    private String email;

    private Picture picture;

    @Getter
    public static class Picture {
        private Data data;

        @Getter
        public static class Data {
            private String url;
        }
    }

}
