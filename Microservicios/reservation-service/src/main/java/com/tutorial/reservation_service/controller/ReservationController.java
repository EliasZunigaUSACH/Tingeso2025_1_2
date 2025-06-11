package com.tutorial.reservation_service.controller;

import com.tutorial.reservation_service.entity.Reservation;
import com.tutorial.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin("*")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservations = reservationService.getAll();
        if(reservations.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable("id") int id) {
        Reservation reservation = reservationService.getReservationById(id);
        if(reservation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<Reservation> save(@RequestBody Reservation reservation) throws ParseException {
        Reservation reservationNew = reservationService.save(reservation);
        return ResponseEntity.ok(reservationNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok(reservationService.delete(id));
    }
}
