package com.example.wodozbior.repository;

import com.example.wodozbior.entity.WaterLevel;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterLevelRepository extends JpaRepository<WaterLevel, Integer> {
    boolean existsByStationAndTime(Station station, java.time.LocalDateTime time);
}
