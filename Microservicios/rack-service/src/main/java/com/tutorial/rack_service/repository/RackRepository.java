package com.tutorial.rack_service.repository;

import com.tutorial.rack_service.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RackRepository extends JpaRepository<Rack, Integer> {

    List<Rack> findRacksByYearAndMonth(String year, String month);
}
