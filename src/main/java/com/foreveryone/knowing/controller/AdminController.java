package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.CommentReportRequest;
import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.VideoReportRequest;
import com.foreveryone.knowing.dto.response.AdminGetResponse.InquiryResponse;
import com.foreveryone.knowing.dto.response.AdminGetResponse.ReportResponse;
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
        adminService.submitVideoReport(videoReportRequest);
    }

    @PostMapping("/report/comment") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitCommentReport(@Valid @RequestBody CommentReportRequest commentReportRequest) {
        adminService.submitCommentReport(commentReportRequest);
    }

    @GetMapping("/report")
    public List<ReportResponse> reportList() {
        return adminService.reportList();
    }

    @DeleteMapping("/video/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideo(@PathVariable Integer videoId) {
        adminService.deleteVideo(videoId);
    }

    @PostMapping("/inquiry") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void inquiry(@Valid @RequestBody InquiryRequest inquiryRequest) {
        adminService.inquiry(inquiryRequest);
    }

    @GetMapping("/inquiry")
    public List<InquiryResponse> inquiryList() {
        return adminService.inquiryList();
    }
}
