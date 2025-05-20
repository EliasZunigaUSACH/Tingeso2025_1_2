package com.tutorial.rack_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private int id;
    private int clientId;
    private String clientName;
    private String year;
    private String month;
    private String day;
    private int peopleQuantity;
    private int trackTime;
    private int totalTime;
    private LocalTime start;
    private LocalTime end;
    private Long basePrice;
    private int discountQuantity;
    private int discountFidelity;
    private int discountBirthday;
    private int discountWeekend;
    private int discountSpecialDay;
    private Long iva;
    private Long totalPrice;
}
