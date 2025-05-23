package com.example.wodozbior.dto.hydrodata;


import lombok.Data;

@Data
public class AlertDto {
    private String stationId;
    private String stationName;
    private String river;
    private int level;
    private String alertDate;
    private String alertType;


}