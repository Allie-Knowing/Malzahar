package com.foreveryone.knowing.repository.admin;

import com.foreveryone.knowing.entity.admin.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}