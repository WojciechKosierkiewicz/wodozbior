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

}
