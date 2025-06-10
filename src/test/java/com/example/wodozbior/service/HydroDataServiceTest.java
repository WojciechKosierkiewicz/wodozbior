package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.AlertDto;
import com.example.wodozbior.dto.hydrodata.ChartDataDto;
import com.example.wodozbior.dto.hydrodata.StationBasicDto;
import com.example.wodozbior.dto.hydrodata.StationDetailsDto;
import com.example.wodozbior.dto.hydrodata.StationMapDto;
import com.example.wodozbior.dto.hydrodata.RiverStationsDto;
import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HydroDataServiceTest {

    private StationRepository stationRepository;
    private RiverRepository riverRepository;
    private WaterLevelRepository waterLevelRepository;
    private OtherMeasurementRepository otherMeasurementRepository;

    private HydroDataService hydroDataService;

    @BeforeEach
    void setUp() {
        stationRepository = mock(StationRepository.class);
        riverRepository = mock(RiverRepository.class);
        waterLevelRepository = mock(WaterLevelRepository.class);
        otherMeasurementRepository = mock(OtherMeasurementRepository.class);

        hydroDataService = new HydroDataService(
                stationRepository,
                riverRepository,
                waterLevelRepository,
                otherMeasurementRepository
        );
    }

    @Test
    void getAllStationsForMap_shouldReturnStations() {
        Station station = new Station();
        station.setId(1);
        station.setName("Station A");
        station.setLatitude(50.0f);
        station.setLongitude(19.0f);
        River river = new River(1, "River X");
        station.setRiver(river);

        WaterLevel wl = new WaterLevel();
        wl.setLevel(100f);
        wl.setTime(LocalDateTime.now());

        when(stationRepository.findAll()).thenReturn(List.of(station));
        when(waterLevelRepository.findTopByStationIdOrderByTimeDesc(1)).thenReturn(Optional.of(wl));
        when(waterLevelRepository.findAverageLevelForStationSince(anyInt(), any())).thenReturn(100f);

        List<StationMapDto> result = hydroDataService.getAllStationsForMap();
        assertFalse(result.isEmpty());
        assertEquals("Station A", result.get(0).getStationName());
    }

    @Test
    void getStationById_shouldReturnDetails() {
        Station station = new Station();
        station.setId(1);
        station.setName("Station A");
        station.setLatitude(50.0f);
        station.setLongitude(19.0f);
        River river = new River(1, "River X");
        Voivodeship voivodeship = new Voivodeship(1, "Voivodeship Y");
        station.setRiver(river);
        station.setVoivodeship(voivodeship);

        WaterLevel wl = new WaterLevel();
        wl.setLevel(150f);
        wl.setTime(LocalDateTime.now());
        wl.setStation(station);

        OtherMeasurement om = new OtherMeasurement();
        om.setStation(station);
        om.setWaterTemp(20f);
        om.setWaterTempDate(LocalDateTime.now());
        om.setIcePhenomena(0);
        om.setIcePhenomenaDate(LocalDateTime.now());
        om.setOvergrowth(1);
        om.setOvergrowthDate(LocalDateTime.now());

        when(stationRepository.findById(1)).thenReturn(Optional.of(station));
        when(waterLevelRepository.findTopByStationIdOrderByTimeDesc(1)).thenReturn(Optional.of(wl));
        when(otherMeasurementRepository.findLatestByStationId(1)).thenReturn(Optional.of(om));

        StationDetailsDto result = hydroDataService.getStationById("1");

        assertNotNull(result);
        assertEquals("Station A", result.getName());
        assertEquals("River X", result.getRiver());
    }

    @Test
    void getAllStationsBasicInfo_shouldReturnBasicInfo() {
        Station station = new Station();
        station.setId(1);
        station.setName("Station A");
        station.setLatitude(50.0f);
        station.setLongitude(19.0f);

        WaterLevel wl = new WaterLevel();
        wl.setLevel(123f);
        wl.setTime(LocalDateTime.now());

        when(stationRepository.findAll()).thenReturn(List.of(station));
        when(waterLevelRepository.findTopByStationIdOrderByTimeDesc(1)).thenReturn(Optional.of(wl));

        List<StationBasicDto> result = hydroDataService.getAllStationsBasicInfo();

        assertFalse(result.isEmpty());
        assertEquals("Station A", result.get(0).getName());
    }

    @Test
    void getChartDataForStation_shouldReturnChartData() {
        WaterLevel wl = new WaterLevel();
        wl.setLevel(100f);
        wl.setFlowRate(200f);
        wl.setTime(LocalDateTime.now());
        wl.setFlowDate(LocalDateTime.now());

        OtherMeasurement om = new OtherMeasurement();
        om.setWaterTemp(15f);
        om.setWaterTempDate(LocalDateTime.now());

        when(waterLevelRepository.findByStationIdAndTimeBetween(anyInt(), any(), any()))
                .thenReturn(List.of(wl));
        when(waterLevelRepository.findByStationIdAndFlowDateBetween(anyInt(), any(), any()))
                .thenReturn(List.of(wl));
        when(otherMeasurementRepository.findByStationIdAndWaterTempDateBetween(anyInt(), any(), any()))
                .thenReturn(List.of(om));

        ChartDataDto result = hydroDataService.getChartDataForStation("1", "2024-01-01", "2024-12-31");

        assertNotNull(result);
        assertFalse(result.getWaterLevel().isEmpty());
        assertFalse(result.getWaterFlow().isEmpty());
        assertFalse(result.getWaterTemperature().isEmpty());
    }

    @Test
    void getCurrentAlerts_shouldReturnAlerts() {
        Station station = new Station();
        station.setId(1);
        station.setName("Station A");
        River river = new River(1, "River X");
        station.setRiver(river);

        WaterLevel wl = new WaterLevel();
        wl.setStation(station);
        wl.setLevel(350f);
        wl.setTime(LocalDateTime.now());

        when(waterLevelRepository.findAll()).thenReturn(List.of(wl));

        List<AlertDto> result = hydroDataService.getCurrentAlerts();

        assertFalse(result.isEmpty());
        assertEquals("Station A", result.get(0).getStationName());
        assertEquals("Wysoki poziom wody", result.get(0).getAlertType());
    }

    @Test
    void getAllRivers_shouldReturnRiversWithStations() {
        River river = new River(1, "River X");

        Station station = new Station();
        station.setId(1);
        station.setName("Station A");

        when(riverRepository.findAll()).thenReturn(List.of(river));
        when(stationRepository.findByRiverId(1)).thenReturn(List.of(station));

        List<RiverStationsDto> result = hydroDataService.getAllRivers();

        assertFalse(result.isEmpty());
        assertEquals("River X", result.get(0).getName());
        assertFalse(result.get(0).getStations().isEmpty());
    }
}
