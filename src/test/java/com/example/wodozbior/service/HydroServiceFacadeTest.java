package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HydroServiceFacadeTest {

    private HydroApiMergeService mergeService;
    private HydroDatabaseSaveService saveService;
    private HydroDataService dataService;

    private HydroServiceFacade hydroServiceFacade;

    @BeforeEach
    void setUp() {
        mergeService = mock(HydroApiMergeService.class);
        saveService = mock(HydroDatabaseSaveService.class);
        dataService = mock(HydroDataService.class);

        hydroServiceFacade = new HydroServiceFacade(mergeService, saveService, dataService);
    }

    @Test
    void fetchAndSaveData_shouldFetchAndSave() {
        List<HydroStationFullDto> dummyData = List.of(new HydroStationFullDto());
        when(mergeService.fetchAndMerge()).thenReturn(dummyData);

        hydroServiceFacade.fetchAndSaveData();

        verify(mergeService, times(1)).fetchAndMerge();
        verify(saveService, times(1)).saveAllAsync(dummyData);
    }

    @Test
    void getAllStationsForMap_shouldDelegateToDataService() {
        List<StationMapDto> dummyList = List.of(new StationMapDto());
        when(dataService.getAllStationsForMap()).thenReturn(dummyList);

        List<StationMapDto> result = hydroServiceFacade.getAllStationsForMap();

        assertEquals(dummyList, result);
        verify(dataService, times(1)).getAllStationsForMap();
    }

    @Test
    void getStationById_shouldDelegateToDataService() {
        StationDetailsDto dummyDto = new StationDetailsDto();
        when(dataService.getStationById("1")).thenReturn(dummyDto);

        StationDetailsDto result = hydroServiceFacade.getStationById("1");

        assertEquals(dummyDto, result);
        verify(dataService, times(1)).getStationById("1");
    }

    @Test
    void getAllStationsBasicInfo_shouldDelegateToDataService() {
        List<StationBasicDto> dummyList = List.of(new StationBasicDto());
        when(dataService.getAllStationsBasicInfo()).thenReturn(dummyList);

        List<StationBasicDto> result = hydroServiceFacade.getAllStationsBasicInfo();

        assertEquals(dummyList, result);
        verify(dataService, times(1)).getAllStationsBasicInfo();
    }

    @Test
    void getChartDataForStation_shouldDelegateToDataService() {
        ChartDataDto dummyDto = new ChartDataDto();
        when(dataService.getChartDataForStation("1", "2024-01-01", "2024-12-31")).thenReturn(dummyDto);

        ChartDataDto result = hydroServiceFacade.getChartDataForStation("1", "2024-01-01", "2024-12-31");

        assertEquals(dummyDto, result);
        verify(dataService, times(1)).getChartDataForStation("1", "2024-01-01", "2024-12-31");
    }

    @Test
    void getCurrentAlerts_shouldDelegateToDataService() {
        List<AlertDto> dummyList = List.of(new AlertDto());
        when(dataService.getCurrentAlerts()).thenReturn(dummyList);

        List<AlertDto> result = hydroServiceFacade.getCurrentAlerts();

        assertEquals(dummyList, result);
        verify(dataService, times(1)).getCurrentAlerts();
    }

    @Test
    void getStationsOnSameRiver_shouldDelegateToDataService() {
        List<StationBasicDto> dummyList = List.of(new StationBasicDto());
        when(dataService.getStationsOnSameRiver("1", null, null)).thenReturn(dummyList);

        List<StationBasicDto> result = hydroServiceFacade.getStationsOnSameRiver("1", null, null);

        assertEquals(dummyList, result);
        verify(dataService, times(1)).getStationsOnSameRiver("1", null, null);
    }

    @Test
    void getAllRivers_shouldDelegateToDataService() {
        List<RiverStationsDto> dummyList = List.of(new RiverStationsDto());
        when(dataService.getAllRivers()).thenReturn(dummyList);

        List<RiverStationsDto> result = hydroServiceFacade.getAllRivers();

        assertEquals(dummyList, result);
        verify(dataService, times(1)).getAllRivers();
    }
}
