package com.foreveryone.knowing.dto.response;

import com.foreveryone.knowing.entity.Inquiry;
import com.foreveryone.knowing.entity.Report;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AdminGetResponse {

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class InquiryResponse {
        private final Integer userId;
        private final String title;
        private final String description;

        public static InquiryResponse from(Inquiry inquiry) {
            return InquiryResponse.builder()
                    .userId(inquiry.getUser().getId())
                    .title(inquiry.getTitle())
                    .description(inquiry.getDescription())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class ReportResponse {
        private final Integer videoId;
        private final Integer userId;
        private final String description;

        public static ReportResponse from(Report report) {
            return ReportResponse.builder()
                    .videoId(report.getVideo().getId())
                    .userId(report.getUser().getId())
                    .description(report.getDescription())
                    .build();
        }
    }
}
