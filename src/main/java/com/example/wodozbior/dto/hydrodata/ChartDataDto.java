package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChartDataDto {

     private List<MeasurementPointDto> waterLevel ;
    private List<MeasurementPointDto> waterTemperature ;
    private List<MeasurementPointDto> waterFlow  ;


    public ChartDataDto(List<MeasurementPointDto> waterLevel, List<MeasurementPointDto> waterTemperature, List<MeasurementPointDto> waterFlow) {
        this.waterLevel = waterLevel;
        this.waterTemperature = waterTemperature;
        this.waterFlow = waterFlow;
    }

    public ChartDataDto() {
        this.waterLevel = new ArrayList<>();
        this.waterTemperature = new ArrayList<>();
        this.waterFlow = new ArrayList<>();
    }


    public List<MeasurementPointDto> getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(List<MeasurementPointDto> waterLevel) {
        this.waterLevel = waterLevel;
    }

    public List<MeasurementPointDto> getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(List<MeasurementPointDto> waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public List<MeasurementPointDto> getWaterFlow() {
        return waterFlow;
    }

    public void setWaterFlow(List<MeasurementPointDto> waterFlow) {
        this.waterFlow = waterFlow;
    }
}
