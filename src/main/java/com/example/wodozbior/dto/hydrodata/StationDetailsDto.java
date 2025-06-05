package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

@Data
public class StationDetailsDto {
     private String id;
    private String name;
    private String river;
    private String region;
    private double latitude;
    private double longitude;
    private Integer waterLevel;
    private String waterLevelDate;
    private Double waterTemperature;
    private String waterTemperatureDate;
    private String icePhenomenon;
    private String icePhenomenonDate;
    private String overgrowthPhenomenon;
    private String overgrowthPhenomenonDate;


    public StationDetailsDto() {
        // Default constructor
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

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Integer getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Integer waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getWaterLevelDate() {
        return waterLevelDate;
    }

    public void setWaterLevelDate(String waterLevelDate) {
        this.waterLevelDate = waterLevelDate;
    }

    public Double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getWaterTemperatureDate() {
        return waterTemperatureDate;
    }

    public void setWaterTemperatureDate(String waterTemperatureDate) {
        this.waterTemperatureDate = waterTemperatureDate;
    }

    public String getIcePhenomenon() {
        return icePhenomenon;
    }

    public void setIcePhenomenon(String icePhenomenon) {
        this.icePhenomenon = icePhenomenon;
    }

    public String getIcePhenomenonDate() {
        return icePhenomenonDate;
    }

    public void setIcePhenomenonDate(String icePhenomenonDate) {
        this.icePhenomenonDate = icePhenomenonDate;
    }

    public String getOvergrowthPhenomenon() {
        return overgrowthPhenomenon;
    }

    public void setOvergrowthPhenomenon(String overgrowthPhenomenon) {
        this.overgrowthPhenomenon = overgrowthPhenomenon;
    }

    public String getOvergrowthPhenomenonDate() {
        return overgrowthPhenomenonDate;
    }

    public void setOvergrowthPhenomenonDate(String overgrowthPhenomenonDate) {
        this.overgrowthPhenomenonDate = overgrowthPhenomenonDate;
    }
}
