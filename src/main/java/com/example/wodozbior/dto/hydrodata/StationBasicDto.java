package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

@Data
public class StationBasicDto { //podstawowe dane stacji do wyswietlania jako lista
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String waterLevel;
}
