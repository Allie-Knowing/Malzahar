package com.foreveryone.knowing.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_USER_TOKEN(401, "Invalid user token."),
    INTERNAL_SERVER_ERROR(500, "Internal server error."),
    PROVIDER_DOES_NOT_MATCH(409, "Provider does not match."),
    UNSUPPORTED_PROVIDER(400, "Unsupported provider."),
    ACCESS_DENIED(403, "Access denied."),
    NOT_FOUND(404, "Not found."),
    INVALID_REFRESH_TOKEN(401, "Invalid refresh token."),
    REQUIRE_PARAMETER_MISSING(400, "parameter missing.");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
