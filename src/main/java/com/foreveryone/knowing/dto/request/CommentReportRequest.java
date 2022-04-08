package com.foreveryone.knowing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReportRequest {
    @NotNull
    private Integer videoId;
    @NotNull
    private Integer commentId;
    @NotBlank
    private String description;
}
