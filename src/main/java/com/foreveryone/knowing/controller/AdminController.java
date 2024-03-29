package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.admin.CommentReportRequest;
import com.foreveryone.knowing.dto.request.admin.InquiryRequest;
import com.foreveryone.knowing.dto.request.admin.VideoReportRequest;
import com.foreveryone.knowing.dto.response.admin.AdminGetResponse.InquiryResponse;
import com.foreveryone.knowing.dto.response.admin.AdminGetResponse.ReportResponse;
import com.foreveryone.knowing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/report/video") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitVideoReport(@Valid @RequestBody VideoReportRequest videoReportRequest) {
        System.out.println("영상 신고 요청");
        adminService.submitVideoReport(videoReportRequest);
    }

    @PostMapping("/report/comment") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitCommentReport(@Valid @RequestBody CommentReportRequest commentReportRequest) {
        System.out.println("글답변 신고 요청");
        adminService.submitCommentReport(commentReportRequest);
    }

    @GetMapping("/report")
    public List<ReportResponse> reportList() {
        System.out.println("신고 목록 요청");
        return adminService.reportList();
    }

    @DeleteMapping("/video/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideo(@PathVariable Integer videoId, @RequestParam Integer reportId) {
        System.out.println("영상 or 영상 답변 삭제 요청");
        adminService.deleteVideo(videoId, reportId);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer commentId, @RequestParam Integer reportId) {
        System.out.println("글답변 삭제 요청");
        adminService.deleteComment(commentId, reportId);
    }

    @DeleteMapping("/report/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reportPass(@PathVariable Integer reportId) {
        System.out.println("신고 삭제 요청");
        adminService.reportPass(reportId);
    }

    @DeleteMapping("/inquiry/{inquiryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInquiry(@PathVariable Integer inquiryId) {
        System.out.println("문의 삭제 요청");
        adminService.deleteInquiry(inquiryId);
    }

    @PostMapping("/inquiry") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void inquiry(@Valid @RequestBody InquiryRequest inquiryRequest) {
        System.out.println("문의 제출 요청");
        adminService.inquiry(inquiryRequest);
    }

    @GetMapping("/inquiry")
    public List<InquiryResponse> inquiryList() {
        System.out.println("문의 목록 요청");
        return adminService.inquiryList();
    }
}
