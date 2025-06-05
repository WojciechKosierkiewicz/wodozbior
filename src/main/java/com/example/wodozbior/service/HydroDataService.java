package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.*;
import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HydroDataService {

    private final StationRepository stationRepository;
    private final RiverRepository riverRepository;
    private final WaterLevelRepository waterLevelRepository;
    private final OtherMeasurementRepository otherMeasurementRepository;

    public HydroDataService(StationRepository stationRepository,
                            RiverRepository riverRepository,
                            WaterLevelRepository waterLevelRepository,
                            OtherMeasurementRepository otherMeasurementRepository) {
        this.stationRepository = stationRepository;
        this.riverRepository = riverRepository;
        this.waterLevelRepository = waterLevelRepository;
        this.otherMeasurementRepository = otherMeasurementRepository;
    }

    public List<StationMapDto> getAllStationsForMap() {
        return new ArrayList<>();
    }

    public List<Pair<String, List<StationBasicDto>>> getAllRivers() {
        return new ArrayList<>();
    }

    public StationDetailsDto getStationById(String id) {
        return new StationDetailsDto();
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        return new ArrayList<>();
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        return new ChartDataDto();
    }

    public List<AlertDto> getCurrentAlerts() {
        return new ArrayList<>();
    }
}
