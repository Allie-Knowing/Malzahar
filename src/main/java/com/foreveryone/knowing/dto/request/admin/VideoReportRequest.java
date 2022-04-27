package com.foreveryone.knowing.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoReportRequest {
    @NotNull
    private Integer videoId;
    @NotBlank
    private String description;
}
