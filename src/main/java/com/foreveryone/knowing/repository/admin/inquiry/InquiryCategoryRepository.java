package com.foreveryone.knowing.repository.admin.inquiry;

import com.foreveryone.knowing.entity.admin.inquiry.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCategoryRepository extends JpaRepository<InquiryCategory, Integer> {
}