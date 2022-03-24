package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
}