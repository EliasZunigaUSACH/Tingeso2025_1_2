package com.tutorial.receipt_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalTime time;
    private String clientName;
    private int clientId;
    private String clientEmail;
    private int reservationId;
    private boolean emailSended = false;

    @Lob
    private byte[] pdf;
}
