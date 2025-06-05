package com.example.wodozbior.repository;

import com.example.wodozbior.dto.hydrodata.StationBasicDto;
import com.example.wodozbior.entity.River;
import com.example.wodozbior.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findByName(String stationName);

    Collection<Object> findAllByRiver(River river);


    List<Station> findByRiverId(Integer riverId);

    @Query("SELECT s FROM Station s WHERE LOWER(s.river.name) = LOWER(:riverName)")
    List<Station> findByRiverNameIgnoreCase(String riverName);

    @Query("SELECT s.river.id FROM Station s WHERE s.id = :stationId")
    Integer findRiverIdByStationId(Integer stationId);

}
