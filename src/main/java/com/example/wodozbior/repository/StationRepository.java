package com.example.wodozbior.repository;

import com.example.wodozbior.entity.River;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findByName(String stationName);

    Collection<Object> findAllByRiver(River river);
}
