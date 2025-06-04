package com.example.wodozbior.service;

import com.example.wodozbior.entity.*;
import com.example.wodozbior.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class HydroApiService {

    @Value("${hydro.api.url}")
    private String hydroApiUrl;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RiverRepository riverRepository;

    @Autowired
    private VoivodeshipRepository voivodeshipRepository;

    @Autowired
    private WaterLevelRepository waterLevelRepository;

    @Autowired
    private OtherMeasurementRepository otherMeasurementRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedDelayString = "${hydro.api.delay:120000}")
    public void scheduleFetch() {
        fetchAndSaveHydroData();
    }

    @Async
    @Transactional
    public void fetchAndSaveHydroData() {
        ResponseEntity<List> response = restTemplate.getForEntity(hydroApiUrl, List.class);
        List<Map<String, Object>> dataList = response.getBody();

        if (dataList == null) return;

        for (Map<String, Object> data : dataList) {
            try {
                String stationId = (String) data.get("id_stacji");
                String stationName = (String) data.get("stacja");
                String riverName = (String) data.get("rzeka");
                String voivodeshipName = (String) data.get("województwo");

                Float waterLevel = parseFloat(data.get("stan_wody"));
                LocalDateTime levelTime = parseDate(data.get("stan_wody_data_pomiaru"));

                Float waterTemp = parseFloat(data.get("temperatura_wody"));
                LocalDateTime waterTempDate = parseDate(data.get("temperatura_wody_data_pomiaru"));

                Integer ice = parseInt(data.get("zjawisko_lodowe"));
                LocalDateTime iceDate = parseDate(data.get("zjawisko_lodowe_data_pomiaru"));

                Integer over = parseInt(data.get("zjawisko_zarastania"));
                LocalDateTime overDate = parseDate(data.get("zjawisko_zarastania_data_pomiaru"));

                River river = riverRepository.findByName(riverName).orElseGet(() -> riverRepository.save(new River(null, riverName)));
                Voivodeship v = voivodeshipRepository.findByName(voivodeshipName).orElseGet(() -> voivodeshipRepository.save(new Voivodeship(null, voivodeshipName)));

                Station station = stationRepository.findById(Integer.parseInt(stationId)).orElseGet(() -> {
                    Station s = new Station();
                    s.setId(Integer.parseInt(stationId));
                    s.setName(stationName);
                    s.setRiver(river);
                    s.setVoivodeship(v);
                    return stationRepository.save(s);
                });

                if (waterLevel != null && levelTime != null) {
                    if (!waterLevelRepository.existsByStationAndTime(station, levelTime)) {
                        WaterLevel wl = new WaterLevel();
                        wl.setStation(station);
                        wl.setLevel(waterLevel);
                        wl.setTime(levelTime);
                        waterLevelRepository.save(wl);
                    }
                }

                if (waterTemp != null || ice != null || over != null) {
                    OtherMeasurement om = new OtherMeasurement();
                    om.setStation(station);
                    om.setWaterTemp(waterTemp);
                    om.setWaterTempDate(waterTempDate);
                    om.setIcePhenomena(ice);
                    om.setIcePhenomenaDate(iceDate);
                    om.setOvergrowth(over);
                    om.setOvergrowthDate(overDate);
                    otherMeasurementRepository.save(om);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Float parseFloat(Object value) {
        try {
            return value == null ? null : Float.parseFloat(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(Object value) {
        try {
            return value == null ? null : Integer.parseInt(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseDate(Object value) {
        try {
            return value == null ? null : LocalDateTime.parse(value.toString(), formatter);
        } catch (Exception e) {
            return null;
        }
    }
}