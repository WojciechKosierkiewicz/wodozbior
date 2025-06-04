package com.example.wodozbior.controller;

import com.example.wodozbior.dto.hydrodata.*;
import com.example.wodozbior.service.HydroServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hydrodata")
public class HydroDataController {

    @Autowired
    private HydroServiceFacade hydroServiceFacade;

    /**
     * Zwraca listę wszystkich stacji hydrologicznych z lokalizacją, poziomem wody, nazwa rzeki, datą pomiaru i kolorem.
     * Używane do wyświetlenia stacji na mapie.
     */
    @GetMapping("/stations")
    public ResponseEntity<List<StationMapDto>> getAllStationsForMap() {
        List<StationMapDto> stations = hydroServiceFacade.getAllStationsForMap();
        return ResponseEntity.ok(stations);
    }

    /**
     * Zwraca szczegółowe dane konkretnej stacji po ID.
     * Używane do wyświetlenia informacji o stacji po kliknięciu w nią na mapie lub liście.
     */
    @GetMapping("/stations/{id}")
    public ResponseEntity<StationDetailsDto> getStationById(@PathVariable String id) {
        StationDetailsDto dto = hydroServiceFacade.getStationById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Zwraca listę podstawowych informacji o wszystkich stacjach: ID, nazwa, lokalizacja, poziom wody.
     * Używane np. do listy stacji, wyszukiwania lub rozwijanej listy w interfejsie.
     */
    @GetMapping("/stations/list")
    public ResponseEntity<List<StationBasicDto>> getBasicStations() {
        List<StationBasicDto> stations = hydroServiceFacade.getAllStationsBasicInfo();
        return ResponseEntity.ok(stations);
    }

    /**
     * Zwraca dane wykresowe (poziom wody, temperatura, przepływ) dla wybranej stacji i zakresu dat.
     * Używane do rysowania wykresów czasowych w interfejsie użytkownika.
     */
    @GetMapping("/stations/{id}/chart")
    public ResponseEntity<ChartDataDto> getChartData(
            @PathVariable String id,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        ChartDataDto chartData = hydroServiceFacade.getChartDataForStation(id, startDate, endDate);
        if (chartData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chartData);
    }

    /**
     * Zwraca listę aktywnych alertów hydrologicznych dla wszystkich stacji.
     * Używane do podświetlania stacji na mapie lub pokazywania ostrzeżeń w interfejsie.
     */
    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDto>> getAlerts() {
        List<AlertDto> alerts = hydroServiceFacade.getCurrentAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/rivers")
    public ResponseEntity<List<Pair<String,List<StationBasicDto>>>> getRivers() {
        List<Pair<String,List<StationBasicDto>>> rivers = hydroServiceFacade.getAllRivers();
        return ResponseEntity.ok(rivers);
    }
}
