package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

@Data
public class StationBasicDto { //podstawowe dane stacji do wyswietlania jako lista
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String waterLevel;

    public StationBasicDto(String id, String name, double latitude, double longitude, String waterLevel) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterLevel = waterLevel;
    }
    public StationBasicDto() {
        new StationBasicDto("", "", 0.0, 0.0, "");
    }

}
