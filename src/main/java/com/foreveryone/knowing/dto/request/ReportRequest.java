package com.foreveryone.knowing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    @NotBlank
    private Integer videoId;
    @NotBlank
    private String description;
}
