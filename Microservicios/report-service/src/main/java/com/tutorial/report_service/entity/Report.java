package com.tutorial.report_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String year_start;
    private String year_end;
    private String month_start;
    private String month_end;

    @ElementCollection
    private List<Integer> racksIDs;

    private List<Long> monthEarnings1;
    private List<Long> monthEarnings2;
    private List<Long> monthEarnings3;
    private List<Long> totalMonthEarnings;
}
