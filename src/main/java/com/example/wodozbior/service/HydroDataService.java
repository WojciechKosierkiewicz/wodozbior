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
        return stationRepository.findAll().stream().map(station -> {
            Double latestLevel = waterLevelRepository.findLatestByStationId(station.getId())
                    .map(WaterLevel::getLevel).orElse(null);
            return new StationMapDto(
                    null, // możesz dodać czas ostatniego pomiaru
                    station.getId().toString(),
                    station.getRiver().getName(),
                    station.getLatitude(),
                    station.getLongitude(),
                    latestLevel != null ? latestLevel.intValue() : null,
                    "#FF0000" // kolory możesz uwarunkować później
            );
        }).collect(Collectors.toList());
    }

    public List<Pair<String, List<StationBasicDto>>> getAllRivers() {
        return riverRepository.findAll().stream()
                .map(river -> Pair.of(river.getName(),
                        stationRepository.findAllByRiver(river).stream().map(station -> {
                            String waterLevel = waterLevelRepository.findLatestByStationId(station.getId())
                                    .map(wl -> String.valueOf(wl.getLevel().intValue())).orElse("N/A");
                            return new StationBasicDto(
                                    station.getId().toString(),
                                    station.getName(),
                                    station.getLatitude(),
                                    station.getLongitude(),
                                    waterLevel
                            );
                        }).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    public StationDetailsDto getStationById(String id) {
        Station station = stationRepository.findById(Integer.parseInt(id)).orElseThrow();
        StationDetailsDto dto = new StationDetailsDto();
        dto.setId(id);
        dto.setName(station.getName());
        dto.setRiver(station.getRiver().getName());
        dto.setRegion(station.getVoivodeship().getName());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());

        waterLevelRepository.findLatestByStationId(station.getId()).ifPresent(wl -> {
            dto.setWaterLevel(wl.getLevel().intValue());
            dto.setWaterLevelDate(wl.getTime().toString());
            dto.setWaterFlow(wl.getFlowRate());
            dto.setWaterFlowDate(wl.getFlowDate() != null ? wl.getFlowDate().toString() : null);
        });

        otherMeasurementRepository.findLatestByStationId(station.getId()).ifPresent(om -> {
            dto.setWaterTemperature(om.getWaterTemp());
            dto.setWaterTemperatureDate(om.getWaterTempDate() != null ? om.getWaterTempDate().toString() : null);
            dto.setIcePhenomenon(String.valueOf(om.getIcePhenomena()));
            dto.setIcePhenomenonDate(om.getIcePhenomenaDate() != null ? om.getIcePhenomenaDate().toString() : null);
            dto.setOvergrowthPhenomenon(String.valueOf(om.getOvergrowth()));
            dto.setOvergrowthPhenomenonDate(om.getOvergrowthDate() != null ? om.getOvergrowthDate().toString() : null);
        });

        return dto;
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        return stationRepository.findAll().stream().map(station -> {
            String level = waterLevelRepository.findLatestByStationId(station.getId())
                    .map(wl -> String.valueOf(wl.getLevel().intValue())).orElse("N/A");
            return new StationBasicDto(
                    station.getId().toString(),
                    station.getName(),
                    station.getLatitude(),
                    station.getLongitude(),
                    level
            );
        }).collect(Collectors.toList());
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        int stationId = Integer.parseInt(id);
        ChartDataDto chart = new ChartDataDto();

        waterLevelRepository.findAllByStationIdOrderByTimeAsc(stationId).forEach(wl -> {
            chart.getWaterLevel().add(new MeasurementPointDto(wl.getTime().toString(), wl.getLevel().intValue()));
            if (wl.getFlowRate() != null)
                chart.getWaterFlow().add(new MeasurementPointDto(wl.getTime().toString(), wl.getFlowRate().intValue()));
        });

        otherMeasurementRepository.findAllByStationIdOrderByWaterTempDateAsc(stationId).forEach(om -> {
            if (om.getWaterTemp() != null)
                chart.getWaterTemperature().add(new MeasurementPointDto(om.getWaterTempDate().toString(), om.getWaterTemp().intValue()));
        });

        return chart;
    }

    public List<AlertDto> getCurrentAlerts() {
        return List.of(); // placeholder do rozbudowy
    }
}
