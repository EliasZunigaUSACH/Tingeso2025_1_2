package com.tutorial.receipt_service.controller;

import com.tutorial.receipt_service.entity.Receipt;
import com.tutorial.receipt_service.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class ReceiptController {

    @Autowired
    ReceiptService receiptService;

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getById(@PathVariable("id") int id) {
        Receipt receipt = receiptService.getReceiptById(id);
        if(receipt == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(receipt);
    }

    @PostMapping()
    public ResponseEntity<Receipt> save(@RequestBody Receipt receipt) {
        Receipt receiptNew = receiptService.save(receipt);
        return ResponseEntity.ok(receiptNew);
    }

}
