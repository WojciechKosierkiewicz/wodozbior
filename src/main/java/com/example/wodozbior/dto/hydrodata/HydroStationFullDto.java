package com.example.wodozbior.dto.hydrodata;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HydroStationFullDto {

    private String stationId;
    private String stationName;
    private String river;
    private String voivodeship;
    private Float lat;
    private Float lon;

    private List<MeasurementDto> measurements = new ArrayList<>();

    public static class MeasurementDto {
        private LocalDateTime waterLevelDate;
        private Integer waterLevel;

        private LocalDateTime temperatureDate;
        private Integer temperature;

        private String icePhenomenon;
        private LocalDateTime icePhenomenonDate;

        private String overgrownPhenomenon;
        private LocalDateTime overgrownPhenomenonDate;

        private Integer flow;
        private LocalDateTime flowDate;

        // gettery i settery
        public LocalDateTime getWaterLevelDate() { return waterLevelDate; }
        public void setWaterLevelDate(LocalDateTime waterLevelDate) { this.waterLevelDate = waterLevelDate; }

        public Integer getWaterLevel() { return waterLevel; }
        public void setWaterLevel(Integer waterLevel) { this.waterLevel = waterLevel; }

        public Integer getTemperature() { return temperature; }
        public void setTemperature(Integer temperature) { this.temperature = temperature; }

        public LocalDateTime getTemperatureDate() { return temperatureDate; }
        public void setTemperatureDate(LocalDateTime temperatureDate) { this.temperatureDate = temperatureDate; }

        public String getIcePhenomenon() { return icePhenomenon; }
        public void setIcePhenomenon(String icePhenomenon) { this.icePhenomenon = icePhenomenon; }

        public LocalDateTime getIcePhenomenonDate() { return icePhenomenonDate; }
        public void setIcePhenomenonDate(LocalDateTime icePhenomenonDate) { this.icePhenomenonDate = icePhenomenonDate; }

        public String getOvergrownPhenomenon() { return overgrownPhenomenon; }
        public void setOvergrownPhenomenon(String overgrownPhenomenon) { this.overgrownPhenomenon = overgrownPhenomenon; }

        public LocalDateTime getOvergrownPhenomenonDate() { return overgrownPhenomenonDate; }
        public void setOvergrownPhenomenonDate(LocalDateTime overgrownPhenomenonDate) { this.overgrownPhenomenonDate = overgrownPhenomenonDate; }

        public Integer getFlow() { return flow; }
        public void setFlow(Integer flow) { this.flow = flow; }

        public LocalDateTime getFlowDate() { return flowDate; }
        public void setFlowDate(LocalDateTime flowDate) { this.flowDate = flowDate; }
    }

    // gettery i settery
    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getRiver() { return river; }
    public void setRiver(String river) { this.river = river; }

    public String getVoivodeship() { return voivodeship; }
    public void setVoivodeship(String voivodeship) { this.voivodeship = voivodeship; }

    public Float getLat() { return lat; }
    public void setLat(Float lat) { this.lat = lat; }

    public Float getLon() { return lon; }
    public void setLon(Float lon) { this.lon = lon; }

    public List<MeasurementDto> getMeasurements() { return measurements; }
    public void setMeasurements(List<MeasurementDto> measurements) { this.measurements = measurements; }
}
