package com.example.wodozbior.service;

import com.example.wodozbior.dto.hydrodata.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HydroServiceFacade {

    private final HydroApiMergeService mergeService;
    private final HydroDatabaseSaveService saveService;
    private final HydroDataService dataService;


    @Value("${hydro.api.delay}")
    private long delay;

    public HydroServiceFacade(HydroApiMergeService mergeService,
                              HydroDatabaseSaveService saveService,
                              HydroDataService dataService) {
        this.mergeService = mergeService;
        this.saveService = saveService;
        this.dataService = dataService;
    }

    @Scheduled(fixedDelayString = "${hydro.api.delay}")
    public void fetchAndSaveData() {
        try {
            List<HydroStationFullDto> data = mergeService.fetchAndMerge();
            saveService.saveAllAsync(data);
            //  System.out.println("✅ Dane hydrologiczne zostały pobrane i zapisane.");
        } catch (Exception e) {
            System.err.println("❌ Błąd podczas pobierania lub zapisywania danych: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<StationMapDto> getAllStationsForMap() {
        return dataService.getAllStationsForMap();
    }

//public List<Pair<String, List<StationBasicDto>>> getAllRivers() {
//    return dataService.getAllRivers();
//}

    public StationDetailsDto getStationById(String id) {
        return dataService.getStationById(id);
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        return dataService.getAllStationsBasicInfo();
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        return dataService.getChartDataForStation(id, startDate, endDate);
    }

    public List<AlertDto> getCurrentAlerts() {
        return dataService.getCurrentAlerts();
    }

    public List<StationBasicDto> getStationsOnSameRiver(String stationId, Integer riverId, String riverName) {
        return dataService.getStationsOnSameRiver(stationId, riverId, riverName);
    }

    public List<RiverStationsDto> getAllRivers() {
        return dataService.getAllRivers();
    }
}