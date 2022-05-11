package com.foreveryone.knowing.dto.response.mypage;

import com.foreveryone.knowing.entity.auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponse {
    private Integer id;

    private String profile;

    private String name;

    public static FollowResponse of(User user) {
        return FollowResponse.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .name(user.getName())
                .build();
    }
}
