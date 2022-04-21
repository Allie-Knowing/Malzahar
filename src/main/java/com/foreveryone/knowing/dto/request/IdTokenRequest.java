package com.foreveryone.knowing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdTokenRequest {

    @NotNull
    private String idToken;
    private String name;
}
