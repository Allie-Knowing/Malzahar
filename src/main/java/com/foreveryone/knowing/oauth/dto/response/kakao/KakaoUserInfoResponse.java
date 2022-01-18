package com.foreveryone.knowing.oauth.dto.response.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoResponse {

    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {

        private String email;

        private Profile profile;

        private Boolean hasEmail;

        @Getter
        public static class Profile {

            private String nickname;

            private String thumbnailImageUrl;

        }
    }

}
