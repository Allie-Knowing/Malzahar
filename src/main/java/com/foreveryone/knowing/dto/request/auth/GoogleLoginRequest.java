package com.foreveryone.knowing.dto.request.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class GoogleLoginRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;

    private String picture;
}
