package com.tutorial.report_service.model;

import java.util.List;

public class Rack {
    private int id;
    private String year;
    private String month;
    private int week;
    private List<Reservation> reservations;
}