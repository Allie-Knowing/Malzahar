package com.foreveryone.knowing.dto.response;

import com.foreveryone.knowing.entity.Category;
import com.foreveryone.knowing.entity.Inquiry;
import com.foreveryone.knowing.entity.Report;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

public class AdminGetResponse {

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class InquiryResponse {
        private final Integer userId;
        private final String title;
        private final String description;
        private final Category category;
        private final Timestamp createdAt;

        public static InquiryResponse from(Inquiry inquiry) {
            return InquiryResponse.builder()
                    .userId(inquiry.getUser().getId())
                    .title(inquiry.getTitle())
                    .description(inquiry.getDescription())
                    .category(inquiry.getInquiryCategory().getCategory())
                    .createdAt(inquiry.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class ReportResponse {
        private final Integer videoId;
        private final Integer userId;
        private final Integer commentId;
        private final String description;
        private final Timestamp createdAt;

        public static ReportResponse from(Report report) {
            return ReportResponse.builder()
                    .videoId(report.getVideo().getId())
                    .userId(report.getUser().getId())
                    .description(report.getDescription())
                    .commentId(report.getComment() == null ? null : report.getComment().getId())
                    .createdAt(report.getCreatedAt())
                    .build();
        }
    }
}