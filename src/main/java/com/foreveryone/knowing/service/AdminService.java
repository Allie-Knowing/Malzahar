package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.admin.CommentReportRequest;
import com.foreveryone.knowing.dto.request.admin.InquiryRequest;
import com.foreveryone.knowing.dto.request.admin.VideoReportRequest;
import com.foreveryone.knowing.entity.actionpoint.ActionPoint;
import com.foreveryone.knowing.entity.admin.answer.Video;
import com.foreveryone.knowing.entity.admin.inquiry.InquiryCategoryEnum;
import com.foreveryone.knowing.entity.admin.inquiry.Inquiry;
import com.foreveryone.knowing.entity.admin.inquiry.InquiryCategory;
import com.foreveryone.knowing.entity.admin.Report;
import com.foreveryone.knowing.entity.auth.User;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.repository.actionpoint.ActionPointCategoryRepository;
import com.foreveryone.knowing.repository.actionpoint.ActionPointRepository;
import com.foreveryone.knowing.repository.admin.*;
import com.foreveryone.knowing.repository.admin.answer.CommentRepository;
import com.foreveryone.knowing.repository.admin.answer.VideoRepository;
import com.foreveryone.knowing.repository.admin.inquiry.InquiryCategoryRepository;
import com.foreveryone.knowing.repository.admin.inquiry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.foreveryone.knowing.dto.response.admin.AdminGetResponse.*;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReportRepository reportRepository;
    private final InquiryRepository inquiryRepository;
    private final VideoRepository videoRepository;
    private final InquiryCategoryRepository inquiryCategoryRepository;
    private final CommentRepository commentRepository;
    private final ActionPointCategoryRepository actionPointCategoryRepository;
    private final ActionPointRepository actionPointRepository;

    public void submitVideoReport(VideoReportRequest videoReportRequest) {

        reportRepository.save(
                Report.builder()
                        .description(videoReportRequest.getDescription())
                        .user(getCurrentUser())
                        .video(videoRepository.findById(videoReportRequest.getVideoId()).orElseThrow(
                                        () -> new NotFoundException("video Id Not Found")
                        ))
                        .createdAt(now())
                        .build());

        System.out.println("비디오 신고 제출");
    }

    public void submitCommentReport(CommentReportRequest commentReportRequest) {

        reportRepository.save(
                Report.builder()
                        .description(commentReportRequest.getDescription())
                        .user(getCurrentUser())
                        .video(videoRepository.findById(commentReportRequest.getVideoId()).orElseThrow(
                                () -> new NotFoundException("video Id Not Found")
                        ))
                        .comment(commentRepository.findById(commentReportRequest.getCommentId()).orElseThrow(
                                () -> new NotFoundException("comment Id Not Found")
                        ))
                        .createdAt(now())
                        .build());

        System.out.println("글답변 신고");
    }

    public List<ReportResponse> reportList() {
        System.out.println("신고 리스트 보기");
        return reportRepository.findAll().stream()
                .map(ReportResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVideo(Integer videoId, Integer reportId) {
        System.out.println("비디오 삭제");
        Integer reportApproveCategoryId = 8;
        Integer reportedCategoryId = 10;

        Video video = videoRepository.findById(videoId).orElseThrow(() -> new NotFoundException("video ID Not found."));
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException("report ID Not found."));

        saveActionPoint(reportApproveCategoryId, report.getUser());
        saveActionPoint(reportedCategoryId, video.getUser());

        videoRepository.deleteById(videoId);
    }

    public void inquiry(InquiryRequest inquiryRequest) {

        InquiryCategory inquiryCategory = inquiryCategoryRepository.save(
                InquiryCategory.builder()
                .inquiryCategoryEnum(InquiryCategoryEnum.valueOf(inquiryRequest.getCategory()))
                .build()
        );

        inquiryRepository.save(Inquiry.builder()
                .title(inquiryRequest.getTitle())
                .description(inquiryRequest.getDescription())
                .user(getCurrentUser())
                .inquiryCategory(inquiryCategory)
                .createdAt(now())
                .build());

        System.out.println("문의");
    }

    public List<InquiryResponse> inquiryList() {
        System.out.println("문의 리스트");
        return inquiryRepository.findAllByOrderByInquiryCategoryInquiryCategoryEnum().stream()
                .map(InquiryResponse::from)
                .collect(Collectors.toList());
    }

    private void saveActionPoint(Integer actionCategoryId, User user) {
        actionPointRepository.save(ActionPoint.builder()
                .actionPointCategory(
                        actionPointCategoryRepository.findById(actionCategoryId)
                                .orElseThrow(() ->
                                        new NotFoundException("action point category Id Not Found. Id : "
                                                + actionCategoryId)))
                .createdAt(now())
                .user(user)
                .build());
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    private User getCurrentUser() {
        return (User)getContext().getAuthentication().getPrincipal();
    }
}
