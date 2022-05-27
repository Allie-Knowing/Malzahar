package com.foreveryone.knowing.dto.request.mypage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FollowingRequest {
    @NotNull
    @JsonProperty(value = "userId")
    private Integer userId;
}
