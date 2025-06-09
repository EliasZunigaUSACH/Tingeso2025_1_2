package com.tutorial.rack_service.service;

import com.tutorial.rack_service.entity.Rack;
import com.tutorial.rack_service.model.Reservation;
import com.tutorial.rack_service.repository.RackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RackService {

    @Autowired
    RackRepository rackRepository;

    public List<Rack> getAll() {
        return rackRepository.findAll();
    }

    public List<Rack> getRacksByYearAndMonth(String year, String month) {
        return rackRepository.findRacksByYearAndMonth(year, month);
    }

    public Rack getRackById(int id) {
        return rackRepository.findById(id).orElse(null);
    }

    public Rack save(Rack rack) {
        return rackRepository.save(rack);
    }

    public Rack update(Rack rack, int reservationId) {
        rack.getReservations().add(reservationId);
        return rackRepository.save(rack);
    }
}
