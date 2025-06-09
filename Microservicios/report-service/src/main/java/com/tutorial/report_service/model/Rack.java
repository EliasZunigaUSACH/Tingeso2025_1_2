package com.tutorial.report_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rack {
    private int id;
    private String year;
    private String month;
    private int week;
    private List<Integer> reservationsIDs;
}