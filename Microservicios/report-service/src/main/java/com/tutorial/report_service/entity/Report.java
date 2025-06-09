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

    @ElementCollection
    private List<Long> monthEarnings1;

    @ElementCollection
    private List<Long> monthEarnings2;

    @ElementCollection
    private List<Long> monthEarnings3;

    @ElementCollection
    private List<Long> totalMonthEarnings;
}
