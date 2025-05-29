package com.tutorial.receipt_service.service;

import com.tutorial.receipt_service.entity.Receipt;
import com.tutorial.receipt_service.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;

    public Receipt getById(int id) {
        return receiptRepository.findById(id).orElse(null);
    }

    public Receipt save(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

}
