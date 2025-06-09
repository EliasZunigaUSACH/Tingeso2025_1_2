package com.tutorial.rack_service.repository;

import com.tutorial.rack_service.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RackRepository extends JpaRepository<Rack, Integer> {

    @Query(value = "SELECT * FROM racks WHERE racks.year = :year AND racks.month = :month", nativeQuery = true)
    List<Rack> findRacksByYearAndMonth(String year, String month);
}
