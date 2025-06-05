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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }
}
