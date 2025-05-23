package com.example.wodozbior.service;



import com.example.wodozbior.dto.hydrodata.HydroStationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HydroDataService {

    @Value("${imgw.api.url}")
    private String imgwApiUrl; // np. https://danepubliczne.imgw.pl/api/data/hydro/

    private final RestTemplate restTemplate = new RestTemplate();

    public List<HydroStationDto> getAllStations() {
        HydroStationDto[] response = restTemplate.getForObject(imgwApiUrl, HydroStationDto[].class);
        return Arrays.asList(response);
    }

    public Optional<HydroStationDto> getStationById(String id) {
        return getAllStations().stream()
                .filter(s -> s.getIdStacji().equals(id))
                .findFirst();
    }

    public List<HydroStationDto> searchStations(String wojewodztwo, String rzeka, String stacja) {
        return getAllStations().stream()
                .filter(s -> wojewodztwo == null || s.getWojewodztwo().equalsIgnoreCase(wojewodztwo))
                .filter(s -> rzeka == null || s.getRzeka().equalsIgnoreCase(rzeka))
                .filter(s -> stacja == null || s.getStacja().toLowerCase().contains(stacja.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<HydroStationDto> getStationsWithIce() {
        return getAllStations().stream()
                .filter(s -> s.getZjawiskoLodowe() != null && !s.getZjawiskoLodowe().equals("0"))
                .collect(Collectors.toList());
    }

    public List<HydroStationDto> getStationsWithVegetation() {
        return getAllStations().stream()
                .filter(s -> s.getZjawiskoZarastania() != null && !s.getZjawiskoZarastania().equals("0"))
                .collect(Collectors.toList());
    }
}
