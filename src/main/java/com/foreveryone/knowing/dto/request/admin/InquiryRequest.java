package com.foreveryone.knowing.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryRequest {
    @NotBlank
    private String category;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
