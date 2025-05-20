package com.tutorial.rack_service.controller;

import com.tutorial.rack_service.entity.Rack;
import com.tutorial.rack_service.model.Reservation;
import com.tutorial.rack_service.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class RackController {

    @Autowired
    RackService rackService;

    @GetMapping
    public ResponseEntity<List<Rack>> getAll() {
        List<Rack> racks = rackService.getAll();
        if(racks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(racks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rack> getById(@PathVariable("id") int id) {
        Rack rack = rackService.getStudentById(id);
        if(rack == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rack);
    }

    @PostMapping()
    public ResponseEntity<Rack> save(@RequestBody Rack rack) {
        Rack rackNew = rackService.save(rack);
        return ResponseEntity.ok(rackNew);
    }

    @PutMapping
    public ResponseEntity<Rack> update(@RequestBody Rack rack) {
        Rack rackNew = rackService.update(rack);
        return ResponseEntity.ok(rackNew);
    }
}
