package com.example.wodozbior.repository;

import com.example.wodozbior.entity.OtherMeasurement;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OtherMeasurementRepository extends JpaRepository<OtherMeasurement, Integer> {
    @Query("SELECT om FROM OtherMeasurement om WHERE om.station.id = :stationId " +
            "ORDER BY om.waterTempDate DESC NULLS LAST")
    Optional<OtherMeasurement> findLatestByStationId(Integer stationId);

    List<OtherMeasurement> findAllByStationIdOrderByWaterTempDateAsc(Integer stationId);

    boolean existsByStationAndWaterTempDateAndIcePhenomenaDateAndOvergrowthDate(
            Station station,
            LocalDateTime waterTempDate,
            LocalDateTime icePhenomenaDate,
            LocalDateTime overgrowthDate
    );
}
