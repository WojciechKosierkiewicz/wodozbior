package com.example.wodozbior.repository;

import com.example.wodozbior.entity.OtherMeasurement;
import com.example.wodozbior.entity.WaterLevel;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaterLevelRepository extends JpaRepository<WaterLevel, Integer> {

    @Query("SELECT wl FROM WaterLevel wl WHERE wl.station.id = :stationId ORDER BY wl.time DESC LIMIT 1")
    Optional<WaterLevel> findLatestByStationId(Integer stationId);

    List<WaterLevel> findAllByStationIdOrderByTimeAsc(Integer stationId);

    boolean existsByStationAndTime(Station station, LocalDateTime time);

    List<WaterLevel> findByStationIdAndTimeBetween(Integer stationId, LocalDateTime start, LocalDateTime end);

    List<WaterLevel> findByStationIdAndFlowDateBetween(Integer stationId, LocalDateTime start, LocalDateTime end);



}
