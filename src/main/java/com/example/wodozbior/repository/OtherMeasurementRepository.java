package com.example.wodozbior.repository;

import com.example.wodozbior.entity.OtherMeasurement;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OtherMeasurementRepository extends JpaRepository<OtherMeasurement, Integer> {
    boolean existsByStationAndWaterTempDateAndIcePhenomenaDateAndOvergrowthDate(
            Station station,
            LocalDateTime waterTempDate,
            LocalDateTime icePhenomenaDate,
            LocalDateTime overgrowthDate
    );
}
