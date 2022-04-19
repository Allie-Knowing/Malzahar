package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.CommentReportRequest;
import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.VideoReportRequest;
import com.foreveryone.knowing.entity.*;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.foreveryone.knowing.dto.response.AdminGetResponse.*;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReportRepository reportRepository;
    private final InquiryRepository inquiryRepository;
    private final VideoRepository videoRepository;
    private final InquiryCategoryRepository inquiryCategoryRepository;
    private final CommentRepository commentRepository;

    public void submitVideoReport(VideoReportRequest videoReportRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        reportRepository.save(Report.builder()
                .description(videoReportRequest.getDescription())
                .user(getCurrentUser())
                .video(videoRepository.findById(videoReportRequest.getVideoId()).orElseThrow(
                                () -> new NotFoundException("video Id Not Found")
                ))
                .createdAt(now)
                .build());
    }

    public void submitCommentReport(CommentReportRequest commentReportRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        reportRepository.save(Report.builder()
                .description(commentReportRequest.getDescription())
                .user(getCurrentUser())
                .video(videoRepository.findById(commentReportRequest.getVideoId()).orElseThrow(
                        () -> new NotFoundException("video Id Not Found")
                ))
                .comment(commentRepository.findById(commentReportRequest.getCommentId()).orElseThrow(
                        () -> new NotFoundException("comment Id Not Found")
                ))
                .createdAt(now)
                .build());
    }

    public List<ReportResponse> reportList() {
        return reportRepository.findAll().stream()
                .map(ReportResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteVideo(Integer videoId) {
        videoRepository.deleteById(videoId);
    }

    public void inquiry(InquiryRequest inquiryRequest) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        InquiryCategory inquiryCategory = inquiryCategoryRepository.save(InquiryCategory.builder()
                .category(Category.valueOf(inquiryRequest.getCategory()))
                .build());

        inquiryRepository.save(Inquiry.builder()
                .title(inquiryRequest.getTitle())
                .description(inquiryRequest.getDescription())
                .user(getCurrentUser())
                .inquiryCategory(inquiryCategory)
                .createdAt(now)
                .build());

    }

    public List<InquiryResponse> inquiryList() {
        return inquiryRepository.findAllByOrderByInquiryCategoryCategory().stream()
                .map(InquiryResponse::from)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        return (User)getContext().getAuthentication().getPrincipal();
    }
}
