package com.tutorial.report_service.service;

import com.tutorial.report_service.entity.Report;
import com.tutorial.report_service.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report getById(int id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public void delete(Report report) {
        reportRepository.delete(report);
    }
}
