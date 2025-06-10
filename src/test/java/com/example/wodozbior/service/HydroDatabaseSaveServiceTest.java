package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.HydroStationFullDto;
import com.example.wodozbior.dto.hydrodata.HydroStationFullDto.MeasurementDto;
import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class HydroDatabaseSaveServiceTest {

    private VoivodeshipRepository voivodeshipRepo;
    private RiverRepository riverRepo;
    private StationRepository stationRepo;
    private WaterLevelRepository waterLevelRepo;
    private OtherMeasurementRepository otherMeasurementRepo;

    private HydroDatabaseSaveService saveService;

    @BeforeEach
    void setUp() {
        voivodeshipRepo = mock(VoivodeshipRepository.class);
        riverRepo = mock(RiverRepository.class);
        stationRepo = mock(StationRepository.class);
        waterLevelRepo = mock(WaterLevelRepository.class);
        otherMeasurementRepo = mock(OtherMeasurementRepository.class);

        saveService = new HydroDatabaseSaveService(
                voivodeshipRepo,
                riverRepo,
                stationRepo,
                waterLevelRepo,
                otherMeasurementRepo
        );
    }

    @Test
    void saveAllAsync_shouldSaveDataCorrectly() {
        // Przygotowanie DTO
        HydroStationFullDto dto = new HydroStationFullDto();
        dto.setStationId("123");
        dto.setStationName("Station A");
        dto.setRiver("River X");
        dto.setVoivodeship("Voivodeship X");
        dto.setLat(50.123f);
        dto.setLon(19.987f);

        MeasurementDto m = new MeasurementDto();
        m.setWaterLevel(150);
        m.setWaterLevelDate(LocalDateTime.now());
        m.setFlow(300);
        m.setFlowDate(LocalDateTime.now());
        m.setTemperature(20);
        m.setTemperatureDate(LocalDateTime.now());
        m.setIcePhenomenon("0");
        m.setIcePhenomenonDate(LocalDateTime.now());
        m.setOvergrownPhenomenon("1");
        m.setOvergrownPhenomenonDate(LocalDateTime.now());

        dto.getMeasurements().add(m);

        // Mockowanie repozytoriów
        when(riverRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(riverRepo.save(any(River.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(voivodeshipRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(voivodeshipRepo.save(any(Voivodeship.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(stationRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(stationRepo.save(any(Station.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(waterLevelRepo.existsByStationAndTime(any(), any())).thenReturn(false);
        when(otherMeasurementRepo.existsByStationAndWaterTempDateAndIcePhenomenaDateAndOvergrowthDate(any(), any(), any(), any())).thenReturn(false);

        // Wywołanie metody
        CompletableFuture<Void> future = saveService.saveAllAsync(List.of(dto));

        // Czekamy na wynik
        future.join();

        // Assercje — sprawdzamy że metody repo były wywołane
        verify(riverRepo, times(1)).save(any(River.class));
        verify(voivodeshipRepo, times(1)).save(any(Voivodeship.class));
        verify(stationRepo, times(1)).save(any(Station.class));
        verify(waterLevelRepo, times(1)).save(any(WaterLevel.class));
        verify(otherMeasurementRepo, times(1)).save(any(OtherMeasurement.class));

        // Prosta asercja że future nie jest nullem (techniczna)
        assertNotNull(future);
    }
}
