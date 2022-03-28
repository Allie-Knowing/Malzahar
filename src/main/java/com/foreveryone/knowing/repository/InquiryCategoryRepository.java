package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCategoryRepository extends JpaRepository<InquiryCategory, Integer> {
}