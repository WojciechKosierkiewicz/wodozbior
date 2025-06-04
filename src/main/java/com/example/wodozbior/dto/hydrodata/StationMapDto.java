package com.example.wodozbior.dto.hydrodata;


import lombok.Data;

@Data
public class StationMapDto { // dane do wyswietlenia na mapie
    private String id;
    private String river;
    private double latitude;
    private double longitude;
    private int waterLevel;
    private String measurementDate;
    private String color; // RGB color code

    public StationMapDto(String measurementDate, String id, String river, double latitude, double longitude, int waterLevel, String color) {
        this.measurementDate = measurementDate;
        this.id = id;
        this.river = river;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterLevel = waterLevel;
        this.color = color;
    }
}
