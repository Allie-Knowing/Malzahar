package com.foreveryone.knowing.dto.request.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FollowingRequest {
    @NotNull
    private Integer userId;
}
