package com.foreveryone.knowing.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private final Integer status;
    private final String message;
    private final String description;

    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .description(description)
                .build();
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"status\":" + status +
                ",\n\t\"message\":\"" + message + '\"' +
                ",\n\t\"description\":" + description + '\"' +
                "\n}";
    }
}
