package com.example.wodozbior.repository;

import com.example.wodozbior.entity.OtherMeasurement;
import com.example.wodozbior.entity.WaterLevel;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaterLevelRepository extends JpaRepository<WaterLevel, Integer> {


    // Najnowszy poziom dla stacji
    Optional<WaterLevel> findTopByStationIdOrderByTimeDesc(Integer stationId);

    // Wszystkie poziomy w czasie rosnącym dla stacji
    List<WaterLevel> findAllByStationIdOrderByTimeAsc(Integer stationId);

    // Czy istnieje rekord dla stacji i czasu
    boolean existsByStationAndTime(Station station, LocalDateTime time);

    // Poziomy z zakresu czasu (po time)
    List<WaterLevel> findByStationIdAndTimeBetween(Integer stationId, LocalDateTime start, LocalDateTime end);

    // Poziomy z zakresu czasu (po flowDate) — jeśli masz flowDate w modelu
    List<WaterLevel> findByStationIdAndFlowDateBetween(Integer stationId, LocalDateTime start, LocalDateTime end);

    // ŚREDNIA poziomu z ostatnich X dni — używane do procentowego odchylenia
    @Query("SELECT AVG(w.level) FROM WaterLevel w WHERE w.station.id = :stationId AND w.time >= :startDate")
    Float findAverageLevelForStationSince(@Param("stationId") Integer stationId,
                                          @Param("startDate") LocalDateTime startDate);
}