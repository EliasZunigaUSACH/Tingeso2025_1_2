package com.tutorial.report_service.service;

import com.tutorial.report_service.entity.Report;
import com.tutorial.report_service.model.Rack;
import com.tutorial.report_service.model.Reservation;
import com.tutorial.report_service.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report getById(int id) {
        return reportRepository.findById(id).orElse(null);
    }

    private int calculateTime(int year_start, int year_end, int month_start, int month_end){
        if (year_start == year_end){
            return month_end - month_start;
        } else {
            int yearDifferenceInMonths = (year_end - year_start - 1) * 12;
            int monthsFromStartYear = 12 - month_start;
            return yearDifferenceInMonths + monthsFromStartYear + month_end;
        }
    }

    private void fillMonthEarningsLists(Report report, int time){
        for (int i = 0; i < time; i++){
            report.getMonthEarnings1().add(0L);
            report.getMonthEarnings2().add(0L);
            report.getMonthEarnings3().add(0L);
            report.getTotalMonthEarnings().add(0L);
        }
    }

    private void calculateMonthEarnings(Report report, int time, int yearStart, int monthStart, int yearEnd, int monthEnd) {
        for (int i = 0; i < time; i++) {
            int currentYear = yearStart + (monthStart + i - 1) / 12;
            int currentMonth = (monthStart + i - 1) % 12 + 1;
            String url = "http://rack-service/rack?year=" + currentYear + "&month=" + currentMonth;
            Rack[] racks = restTemplate.getForObject(url, Rack[].class);

            if (racks == null) continue;

            for (Rack rack : racks) {
                if (report.getRacksIDs().contains(rack.getId())) {
                    for (Integer reservationId : rack.getReservationsIDs()) {
                        String reservationUrl = "http://reservation-service/reservation/" + reservationId;
                        Reservation reservation = restTemplate.getForObject(reservationUrl, Reservation.class);

                        if (reservation != null) {
                            switch (reservation.getTrackTime()) {
                                case 1:
                                    report.getMonthEarnings1().set(i,
                                        report.getMonthEarnings1().get(i) + reservation.getTotalPrice());
                                    break;
                                case 2:
                                    report.getMonthEarnings2().set(i,
                                        report.getMonthEarnings2().get(i) + reservation.getTotalPrice());
                                    break;
                                case 3:
                                    report.getMonthEarnings3().set(i,
                                        report.getMonthEarnings3().get(i) + reservation.getTotalPrice());
                                    break;
                            }
                            report.getTotalMonthEarnings().set(i,
                                report.getTotalMonthEarnings().get(i) + reservation.getTotalPrice());
                        }
                    }
                }
            }
        }
    }

    public Report save(Report report) {
        int time = calculateTime(Integer.parseInt(report.getYear_start()), Integer.parseInt(report.getYear_end()), Integer.parseInt(report.getMonth_start()), Integer.parseInt(report.getMonth_end()));
        fillMonthEarningsLists(report, time);
        calculateMonthEarnings(report, time, Integer.parseInt(report.getYear_start()), Integer.parseInt(report.getMonth_start()), Integer.parseInt(report.getYear_end()), Integer.parseInt(report.getMonth_end()));
        return reportRepository.save(report);
    }

    public void delete(Report report) {
        reportRepository.delete(report);
    }
}