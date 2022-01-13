package com.foreveryone.knowing.util.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverAuthRequest {

    private String code;

    private String client_id;

    private String client_secret;

    private String grant_type;

}
