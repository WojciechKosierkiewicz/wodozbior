package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.*;
import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    List<Station> stations = stationRepository.findAll();
    List<StationMapDto> result = new ArrayList<>();

    // Od razu liczmy startDate (10 dni wstecz)
   LocalDateTime startDate = LocalDateTime.now().minusDays(10);


    for (Station station : stations) {
        Float lat = station.getLatitude();
        Float lon = station.getLongitude();

       // if (lat == null || lon == null) {
         //   System.out.println("[DEBUG] Brak współrzędnych dla stacji: " + station.getName() + " (ID: " + station.getId() + ")");
      //  }

        Optional<WaterLevel> optionalLevel = waterLevelRepository.findTopByStationIdOrderByTimeDesc(station.getId());

        if (optionalLevel.isPresent()) {
            WaterLevel wl = optionalLevel.get();

            // Srednia z bazy z ostatnich 10 dni
            Float avgLevel = waterLevelRepository.findAverageLevelForStationSince(station.getId(), startDate);

            // Sprawdzam czy jest sens liczenia (żeby nie dzielić przez 0)
            if (avgLevel == null || avgLevel == 0) {
                avgLevel = 1f;
            }

            Float currentLevel = wl.getLevel();
            Float deviationPercent = 0f;

            if (currentLevel != null) {
                // Liczymy różnicę procentową
                deviationPercent = Math.abs(currentLevel - avgLevel) / avgLevel * 100;
            }

            // Wypełniamy DTO
            StationMapDto dto = new StationMapDto();
            dto.setId(station.getId().toString());
            dto.setRiver(station.getRiver().getName());
            dto.setLatitude(lat != null ? lat : 0.0);
            dto.setLongitude(lon != null ? lon : 0.0);
            dto.setStationName(station.getName());
            dto.setWaterLevel(currentLevel != null ? Math.round(currentLevel) : 0);
            dto.setMeasurementDate(wl.getTime() != null ? wl.getTime().toString() : null);
            dto.setColor(getColorForDeviation(deviationPercent)); // USTAWIAMY KOLOR wg odchylenia %

            result.add(dto);
        }
    }

    return result;
}

private String getColorForDeviation(Float deviationPercent) {
    if (deviationPercent <= 20.0) {
        return "#00FF00"; // zielony
    } else if (deviationPercent <= 40.0) {
        return "#FFFF00"; // żółty
    } else {
        return "#FF0000"; // czerwony
    }
}



    /**
     * Zwraca szczegółowe dane konkretnej stacji po ID.
     * Używane do wyświetlenia informacji o stacji po kliknięciu w nią na mapie lub liście.
     *
     * FIXED: Dodano try-catch dla handlera NonUniqueResultException
     */
    public StationDetailsDto getStationById(String id) {
        Optional<Station> optionalStation = stationRepository.findById(Integer.parseInt(id));
        if (optionalStation.isEmpty()) {
            throw new RuntimeException("Nie znaleziono stacji o ID: " + id);
        }

        Station station = optionalStation.get();

        StationDetailsDto dto = new StationDetailsDto();
        dto.setId(station.getId().toString());
        dto.setName(station.getName());
        dto.setRiver(station.getRiver().getName());
        dto.setRegion(station.getVoivodeship().getName());

        dto.setLatitude(station.getLatitude() != null ? station.getLatitude() : 0.0);
        dto.setLongitude(station.getLongitude() != null ? station.getLongitude() : 0.0);

        // Water level data
        waterLevelRepository.findTopByStationIdOrderByTimeDesc(station.getId()).ifPresent(wl -> {
            dto.setWaterLevel(wl.getLevel() != null ? Math.round(wl.getLevel()) : null);
            dto.setWaterLevelDate(wl.getTime() != null ? wl.getTime().toString() : null);
        });

        // Other measurements data - FIXED: Handle NonUniqueResultException
        try {
            Optional<OtherMeasurement> measurementOpt = otherMeasurementRepository.findLatestByStationId(station.getId());
            if (measurementOpt.isPresent()) {
                OtherMeasurement measurement = measurementOpt.get();

                Float temp = measurement.getWaterTemp();
                dto.setWaterTemperature(temp != null ? temp.doubleValue() : null);

                dto.setWaterTemperatureDate(measurement.getWaterTempDate() != null
                        ? measurement.getWaterTempDate().toString()
                        : null);

                dto.setIcePhenomenon(measurement.getIcePhenomena() != null
                        ? String.valueOf(measurement.getIcePhenomena())
                        : null);
                dto.setIcePhenomenonDate(measurement.getIcePhenomenaDate() != null
                        ? measurement.getIcePhenomenaDate().toString()
                        : null);

                dto.setOvergrowthPhenomenon(measurement.getOvergrowth() != null
                        ? String.valueOf(measurement.getOvergrowth())
                        : null);
                dto.setOvergrowthPhenomenonDate(measurement.getOvergrowthDate() != null
                        ? measurement.getOvergrowthDate().toString()
                        : null);
            }
        } catch (org.springframework.dao.IncorrectResultSizeDataAccessException ex) {
            // Log the issue and handle gracefully
//            System.err.println("[ERROR] Multiple measurements found for station ID: " + station.getId() +
//                             ". Using fallback approach to get the latest measurement.");

            // Fallback: Get all measurements and manually select the latest one
            List<OtherMeasurement> measurements = otherMeasurementRepository.findAllByStationIdOrderByDateDesc(station.getId());
            if (!measurements.isEmpty()) {
                OtherMeasurement measurement = measurements.get(0); // Get the first (latest) one

                Float temp = measurement.getWaterTemp();
                dto.setWaterTemperature(temp != null ? temp.doubleValue() : null);

                dto.setWaterTemperatureDate(measurement.getWaterTempDate() != null
                        ? measurement.getWaterTempDate().toString()
                        : null);

                dto.setIcePhenomenon(measurement.getIcePhenomena() != null
                        ? String.valueOf(measurement.getIcePhenomena())
                        : null);
                dto.setIcePhenomenonDate(measurement.getIcePhenomenaDate() != null
                        ? measurement.getIcePhenomenaDate().toString()
                        : null);

                dto.setOvergrowthPhenomenon(measurement.getOvergrowth() != null
                        ? String.valueOf(measurement.getOvergrowth())
                        : null);
                dto.setOvergrowthPhenomenonDate(measurement.getOvergrowthDate() != null
                        ? measurement.getOvergrowthDate().toString()
                        : null);
            }
        }

        return dto;
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        List<Station> stations = stationRepository.findAll();
        List<StationBasicDto> result = new ArrayList<>();

        for (Station station : stations) {
            StationBasicDto dto = new StationBasicDto();
            dto.setId(station.getId().toString());
            dto.setName(station.getName());
            dto.setLatitude(station.getLatitude() != null ? station.getLatitude() : 0.0);
            dto.setLongitude(station.getLongitude() != null ? station.getLongitude() : 0.0);

            Optional<WaterLevel> levelOpt = waterLevelRepository.findTopByStationIdOrderByTimeDesc(station.getId());
            if (levelOpt.isPresent() && levelOpt.get().getLevel() != null) {
                dto.setWaterLevel(String.valueOf(Math.round(levelOpt.get().getLevel())));
            } else {
                dto.setWaterLevel("brak");
            }

            result.add(dto);
        }

        return result;
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        int stationId = Integer.parseInt(id);
        LocalDateTime from = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime to = LocalDate.parse(endDate).atTime(23, 59, 59);

        List<MeasurementPointDto> levelPoints = waterLevelRepository
                .findByStationIdAndTimeBetween(stationId, from, to)
                .stream()
                .filter(wl -> wl.getLevel() != null)
                .map(wl -> new MeasurementPointDto(
                        wl.getTime().toString(),
                        wl.getLevel()
                ))
                .toList();

        List<MeasurementPointDto> flowPoints = waterLevelRepository
                .findByStationIdAndFlowDateBetween(stationId, from, to)
                .stream()
                .filter(wl -> wl.getFlowRate() != null)
                .map(wl -> new MeasurementPointDto(
                        wl.getFlowDate().toString(),
                        wl.getFlowRate()
                ))
                .toList();

        List<MeasurementPointDto> tempPoints = otherMeasurementRepository
                .findByStationIdAndWaterTempDateBetween(stationId, from, to)
                .stream()
                .filter(m -> m.getWaterTemp() != null)
                .map(m -> new MeasurementPointDto(
                        m.getWaterTempDate().toString(),
                        m.getWaterTemp()
                ))
                .toList();

        ChartDataDto dto = new ChartDataDto();
        dto.setWaterLevel(levelPoints);
        dto.setWaterFlow(flowPoints);
        dto.setWaterTemperature(tempPoints);
        return dto;
    }

    public List<AlertDto> getCurrentAlerts() {
        List<WaterLevel> highLevels = waterLevelRepository.findAll().stream()
                .filter(wl -> wl.getLevel() != null && wl.getLevel() > 300) // przykładowy próg
                .collect(Collectors.toList());

        return highLevels.stream().map(wl -> {
            Station s = wl.getStation();
            AlertDto dto = new AlertDto();
            dto.setStationId(s.getId().toString());
            dto.setStationName(s.getName());
            dto.setRiver(s.getRiver().getName());
            dto.setLevel(Math.round(wl.getLevel()));
            dto.setAlertDate(wl.getTime().toString());
            dto.setAlertType("Wysoki poziom wody");
            return dto;
        }).collect(Collectors.toList());
    }

    public List<StationBasicDto> getStationsOnSameRiver(String stationId, Integer riverId, String riverName) {
        List<Station> stations;

        if (stationId != null) {
            int id = Integer.parseInt(stationId);
            Integer riverIdFromStation = stationRepository.findRiverIdByStationId(id);
            if (riverIdFromStation == null) return List.of();
            stations = stationRepository.findByRiverId(riverIdFromStation);
        } else if (riverId != null) {
            stations = stationRepository.findByRiverId(riverId);
        } else if (riverName != null) {
            stations = stationRepository.findByRiverNameIgnoreCase(riverName);
        } else {
            return List.of(); // brak parametrów
        }

        return stations.stream().map(this::toBasicDto).toList();
    }

    private StationBasicDto toBasicDto(Station station) {
        StationBasicDto dto = new StationBasicDto();
        dto.setId(station.getId().toString());
        dto.setName(station.getName());
        dto.setLatitude(station.getLatitude() != null ? station.getLatitude() : 0.0);
        dto.setLongitude(station.getLongitude() != null ? station.getLongitude() : 0.0);
        dto.setWaterLevel(null); // opcjonalnie: dodaj ostatni poziom z waterLevelRepository
        return dto;
    }

    public List<RiverStationsDto> getAllRivers() {
        List<River> rivers = riverRepository.findAll();
        List<RiverStationsDto> result = new ArrayList<>();

        for (River river : rivers) {
            List<Station> stations = stationRepository.findByRiverId(river.getId());
            List<StationBasicDto> stationDtos = stations.stream()
                    .map(this::toBasicDto)
                    .collect(Collectors.toList());

            RiverStationsDto dto = new RiverStationsDto(river.getId(), river.getName(), stationDtos);
            result.add(dto);
        }

        return result;
    }
}