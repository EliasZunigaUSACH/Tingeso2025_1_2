package com.tutorial.rack_service.controller;

import com.tutorial.rack_service.entity.Rack;
import com.tutorial.rack_service.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/racks")
@CrossOrigin("*")
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
        Rack rack = rackService.getRackById(id);
        if(rack == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rack);
    }

    @GetMapping("/year/{year}/month/{month}")
    public ResponseEntity<List<Rack>> getByYearAndMonth(@PathVariable("year") String year, @PathVariable("month") String month) {
        List<Rack> racks = rackService.getRacksByYearAndMonth(year, month);
        if(racks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(racks);
    }

    @PostMapping
    public ResponseEntity<Rack> save(@RequestBody Rack rack) {
        Rack rackNew = rackService.save(rack);
        return ResponseEntity.ok(rackNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rack> update(@RequestBody Rack rack, @RequestParam("id") int id) {
        Rack rackNew = rackService.update(rack, id);
        return ResponseEntity.ok(rackNew);
    }
}
