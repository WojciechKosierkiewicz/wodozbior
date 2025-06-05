package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

@Data
public class MeasurementPointDto {
    private String date;
    private float value;

    public MeasurementPointDto(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public MeasurementPointDto() {
        // Default constructor
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
