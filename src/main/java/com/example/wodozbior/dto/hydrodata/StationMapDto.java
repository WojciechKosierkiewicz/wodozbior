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

    public  StationMapDto() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
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

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
