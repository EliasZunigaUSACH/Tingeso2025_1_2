package com.tutorial.report_service.controller;

import com.tutorial.report_service.entity.Report;
import com.tutorial.report_service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping
    public ResponseEntity<List<Report>> getAll() {
        List<Report> reports = reportService.getAll();
        if(reports.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getById(@PathVariable("id") int id) {
        Report report = reportService.getById(id);
        if(report == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(report);
    }

    @PostMapping
    public ResponseEntity<Report> save(@RequestBody Report report) {
        Report reportNew = reportService.save(report);
        return ResponseEntity.ok(reportNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Report> delete(@RequestBody Report report) {
        reportService.save(report);
        return ResponseEntity.ok().build();
    }

}
