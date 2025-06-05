package com.example.wodozbior.service;


import com.example.wodozbior.dto.hydrodata.HydroStationFullDto;
import com.example.wodozbior.dto.hydrodata.HydroStationFullDto.MeasurementDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class HydroApiMergeService {

    private final HydroApiFetchService fetchService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HydroApiMergeService(HydroApiFetchService fetchService) {
        this.fetchService = fetchService;
    }

    public List<HydroStationFullDto> fetchAndMerge() {
        List<String> rawJsonList = fetchService.fetchDataFromHydroApi();
        String hydroJson = rawJsonList.get(0);
        String hydro2Json = rawJsonList.get(1);

        Map<String, HydroStationFullDto> stations = new HashMap<>();

        try {
            JsonNode hydroNodes = objectMapper.readTree(hydroJson);
            JsonNode hydro2Nodes = objectMapper.readTree(hydro2Json);

            for (JsonNode node : hydroNodes) {
                String id = node.path("id_stacji").asText();

                HydroStationFullDto dto = new HydroStationFullDto();
                dto.setStationId(id);
                dto.setStationName(node.path("stacja").asText(null));
                dto.setRiver(node.path("rzeka").asText(null));
                dto.setVoivodeship(node.path("województwo").asText(null));

                MeasurementDto m = new MeasurementDto();
                m.setWaterLevel(readInt(node, "stan_wody"));
                m.setWaterLevelDate(readDateTime(node, "stan_wody_data_pomiaru"));
                m.setTemperature(readInt(node, "temperatura_wody"));
                m.setTemperatureDate(readDateTime(node, "temperatura_wody_data_pomiaru"));
                m.setIcePhenomenon(node.path("zjawisko_lodowe").asText(null));
                m.setIcePhenomenonDate(readDateTime(node, "zjawisko_lodowe_data_pomiaru"));
                m.setOvergrownPhenomenon(node.path("zjawisko_zarastania").asText(null));
                m.setOvergrownPhenomenonDate(readDateTime(node, "zjawisko_zarastania_data_pomiaru"));

                dto.getMeasurements().add(m);
                stations.put(id, dto);
            }

            for (JsonNode node : hydro2Nodes) {
                String id = node.path("kod_stacji").asText();
                HydroStationFullDto dto = stations.get(id);
                if (dto == null) continue;

                dto.setStationName(dto.getStationName() != null ? dto.getStationName() : node.path("nazwa_stacji").asText(null));
                dto.setLat(readFloat(node, "lat"));
                dto.setLon(readFloat(node, "lon"));

                MeasurementDto m = new MeasurementDto();
                m.setWaterLevel(readInt(node, "stan"));
                m.setWaterLevelDate(readDateTime(node, "stan_data"));
                m.setFlow(readInt(node, "przelyw"));
                m.setFlowDate(readDateTime(node, "przeplyw_data"));

                dto.getMeasurements().add(m);
            }

        } catch (Exception e) {
            throw new RuntimeException("Błąd parsowania JSON", e);
        }

        return new ArrayList<>(stations.values());
    }

    private LocalDateTime readDateTime(JsonNode node, String key) {
        String text = node.path(key).asText(null);
        if (text == null || text.isEmpty()) return null;
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Integer readInt(JsonNode node, String key) {
        return node.hasNonNull(key) && !node.path(key).asText().isEmpty()
                ? node.path(key).asInt() : null;
    }

    private Float readFloat(JsonNode node, String key) {
        return node.hasNonNull(key) && !node.path(key).asText().isEmpty()
                ? Float.parseFloat(node.path(key).asText()) : null;
    }
}
