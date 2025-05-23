package com.example.wodozbior.controller;

import com.example.wodozbior.dto.hydrodata.HydroStationDto;
import com.example.wodozbior.service.HydroDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hydrodata")
public class HydroDataController {

    @Autowired
    private HydroDataService hydroDataService;

    // GET /api/hydrodata - wszystkie stacje
    @GetMapping
    public ResponseEntity<List<HydroStationDto>> getAllStations() {
        return ResponseEntity.ok(hydroDataService.getAllStations());
    /// here

    }

    // GET /api/hydrodata/{id} - stacja po ID
    @GetMapping("/{id}")
    public ResponseEntity<HydroStationDto> getStationById(@PathVariable String id) {
        return hydroDataService.getStationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//    // GET /api/hydrodata/nearest - najbliższa stacja
//    @GetMapping("/nearest")
//    public ResponseEntity<HydroStationDto> getNearestStation(@RequestParam double latitude, @RequestParam double longitude) {
//        return hydroDataService.getNearestStation(latitude, longitude)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    // najbliższa stacja po lokalizacji
    // naj






}





