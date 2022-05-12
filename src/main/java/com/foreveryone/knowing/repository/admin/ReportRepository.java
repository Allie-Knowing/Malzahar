package com.foreveryone.knowing.repository.admin;

import com.foreveryone.knowing.entity.admin.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAllByPassed(boolean b);
}