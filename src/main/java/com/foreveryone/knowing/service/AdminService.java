package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.ReportRequest;
import com.foreveryone.knowing.entity.Category;
import com.foreveryone.knowing.entity.Inquiry;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.repository.InquiryRepository;
import com.foreveryone.knowing.entity.Report;
import com.foreveryone.knowing.repository.ReportRepository;
import com.foreveryone.knowing.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void submitReport(ReportRequest reportRequest) {
        reportRepository.save(Report.builder()
                .description(reportRequest.getDescription())
                .user(getCurrentUser())
                .video(videoRepository.findById(reportRequest.getVideoId()).orElseThrow(
                                () -> new NotFoundException("video Id Not Found")
                ))
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
        inquiryRepository.save(Inquiry.builder()
                .title(inquiryRequest.getTitle())
                .description(inquiryRequest.getDescription())
                .user(getCurrentUser())
                .category(Category.valueOf(inquiryRequest.getCategory()))
                .build());
    }

    public List<InquiryResponse> inquiryList() {
        return inquiryRepository.findAllByOrderByCategory().stream()
                .map(InquiryResponse::from)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        return (User)getContext().getAuthentication().getPrincipal();
    }
}
