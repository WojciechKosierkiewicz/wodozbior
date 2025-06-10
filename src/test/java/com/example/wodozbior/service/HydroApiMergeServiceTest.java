package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.HydroStationFullDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HydroApiMergeServiceTest {

    private HydroApiFetchService fetchServiceMock;
    private HydroApiMergeService mergeService;

    @BeforeEach
    void setUp() {
        fetchServiceMock = mock(HydroApiFetchService.class);
        mergeService = new HydroApiMergeService(fetchServiceMock);
    }

    @Test
    void fetchAndMerge_shouldMergeDataCorrectly() {
        // Przygotowanie sztucznego JSONu 1 (hydro api)
        String hydroJson = """
                [
                  {
                    "id_stacji": "123",
                    "stacja": "Station A",
                    "rzeka": "River X",
                    "województwo": "Woj X",
                    "stan_wody": "150",
                    "stan_wody_data_pomiaru": "2024-06-10 12:00:00",
                    "temperatura_wody": "20",
                    "temperatura_wody_data_pomiaru": "2024-06-10 12:30:00",
                    "zjawisko_lodowe": "none",
                    "zjawisko_lodowe_data_pomiaru": "2024-06-10 12:30:00",
                    "zjawisko_zarastania": "none",
                    "zjawisko_zarastania_data_pomiaru": "2024-06-10 12:30:00"
                  }
                ]
                """;

        // Przygotowanie sztucznego JSONu 2 (hydro2 api)
        String hydro2Json = """
                [
                  {
                    "kod_stacji": "123",
                    "nazwa_stacji": "Station A",
                    "lat": "50.12345",
                    "lon": "19.98765",
                    "stan": "160",
                    "stan_data": "2024-06-10 13:00:00",
                    "przelyw": "300",
                    "przeplyw_data": "2024-06-10 13:00:00"
                  }
                ]
                """;

        // Mockowanie odpowiedzi z fetchService
        when(fetchServiceMock.fetchDataFromHydroApi()).thenReturn(List.of(hydroJson, hydro2Json));

        // Wywołanie metody
        List<HydroStationFullDto> result = mergeService.fetchAndMerge();

        // Assercje
        assertEquals(1, result.size());
        HydroStationFullDto station = result.get(0);

        assertEquals("123", station.getStationId());
        assertEquals("Station A", station.getStationName());
        assertEquals("River X", station.getRiver());
        assertEquals("Woj X", station.getVoivodeship());
        assertEquals(50.12345f, station.getLat());
        assertEquals(19.98765f, station.getLon());

        // Sprawdzamy czy ma 2 pomiary
        assertEquals(2, station.getMeasurements().size());
    }
}
