package com.foreveryone.knowing.util.dto.response;

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

}