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
}
