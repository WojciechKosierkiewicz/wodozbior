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

    public AlertDto(String stationId, String stationName, String river, int level, String alertDate, String alertType) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.river = river;
        this.level = level;
        this.alertDate = alertDate;
        this.alertType = alertType;

    }
    public AlertDto() {
        // Default constructor
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}