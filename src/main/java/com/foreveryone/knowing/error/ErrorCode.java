package com.foreveryone.knowing.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_USER_TOKEN(401, "Invalid User token."),
    INTERNAL_SERVER_ERROR(500, "Internal Server error.");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
