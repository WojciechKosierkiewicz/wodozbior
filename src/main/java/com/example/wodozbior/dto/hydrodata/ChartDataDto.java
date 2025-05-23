package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataDto {

    private List<MeasurementPointDto> waterLevel;
    private List<MeasurementPointDto> waterTemperature;
    private List<MeasurementPointDto> waterFlow;
}
