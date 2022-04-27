package com.foreveryone.knowing.repository.admin.inquiry;

import com.foreveryone.knowing.entity.admin.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
    List<Inquiry> findAllByOrderByInquiryCategoryCategory();
}