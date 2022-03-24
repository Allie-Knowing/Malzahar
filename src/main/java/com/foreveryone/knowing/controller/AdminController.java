package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.ReportRequest;
import com.foreveryone.knowing.dto.response.AdminGetResponse.InquiryResponse;
import com.foreveryone.knowing.dto.response.AdminGetResponse.ReportResponse;
import com.foreveryone.knowing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/report")
    public void submitReport(@Valid @RequestBody ReportRequest reportRequest) {
        adminService.submitReport(reportRequest);
    }

    @GetMapping("/report")
    public List<ReportResponse> reportList() {
        return adminService.reportList();
    }

    @DeleteMapping("/{videoId}")
    public void deleteVideo(@PathVariable Integer videoId) {
        adminService.deleteVideo(videoId);
    }

    @PostMapping("/inquiry")
    public void inquiry(@Valid @RequestBody InquiryRequest inquiryRequest) {
        adminService.inquiry(inquiryRequest);
    }

    @GetMapping("/inquiry")
    public List<InquiryResponse> inquiryList() {
        return adminService.inquiryList();
    }
}
