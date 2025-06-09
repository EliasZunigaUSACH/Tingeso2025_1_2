package com.tutorial.reservation_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int clientId;
    private String clientName;
    private String year;
    private String month;
    private String day;
    private int peopleQuantity;
    private int trackTime;
    private int totalTime;
    private LocalTime start_time;
    private LocalTime end_time;
    private Long basePrice;
    private int discountQuantity;
    private int discountFidelity;
    private int discountBirthday;
    private int discountWeekend;
    private int discountSpecialDay;
    private Long iva;
    private Long totalPrice;
}
