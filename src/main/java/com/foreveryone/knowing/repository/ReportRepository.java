package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}