package com.tutorial.receipt_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private int id;
    private String name;
    private String birthday;
    private String email;
    private int fidelityLevel;
}