package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.ReportRequest;
import com.foreveryone.knowing.dto.response.AdminGetResponse.InquiryResponse;
import com.foreveryone.knowing.dto.response.AdminGetResponse.ReportResponse;
import com.foreveryone.knowing.error.exceptions.InvalidUserTokenException;
import com.foreveryone.knowing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/report") @PreAuthorize("isAuthenticated()")
    public void submitReport(@Valid @RequestBody ReportRequest reportRequest) {
        adminService.submitReport(reportRequest);
    }

    @GetMapping("/report")
    public List<ReportResponse> reportList() {
        return adminService.reportList();
    }

    @DeleteMapping("/{videoId}")
    public void deleteVideo(@PathVariable Integer videoId, @RequestParam String secret) {
        if (!secret.equals("secret")) throw new InvalidUserTokenException("시크릿쥬쥬");
        adminService.deleteVideo(videoId);
    }

    @PostMapping("/inquiry") @PreAuthorize("isAuthenticated()")
    public void inquiry(@Valid @RequestBody InquiryRequest inquiryRequest) {
        adminService.inquiry(inquiryRequest);
    }

    @GetMapping("/inquiry")
    public List<InquiryResponse> inquiryList() {
        return adminService.inquiryList();
    }
}
