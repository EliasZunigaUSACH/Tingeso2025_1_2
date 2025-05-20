package com.tutorial.receipt_service.model;

import java.time.LocalTime;

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
