package com.example.wodozbior.dto.hydrodata;

import java.util.List;

public class RiverStationsDto {
    private int id;
    private String name;
    private List<StationBasicDto> stations;

    public RiverStationsDto(int id, String name, List<StationBasicDto> stations) {
        this.id = id;
        this.name = name;
        this.stations = stations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StationBasicDto> getStations() {
        return stations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStations(List<StationBasicDto> stations) {
        this.stations = stations;
    }
}