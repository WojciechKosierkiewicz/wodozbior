package com.example.wodozbior.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "other_measurements",
        uniqueConstraints = @UniqueConstraint(name = "unique_station_all_dates", columnNames = {
                "station_id", "water_temp_date", "ice_phenomena_date", "overgrowth_date"
        }))
public class OtherMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    private Float waterTemp;
    private LocalDateTime waterTempDate;
    private Integer icePhenomena;
    private LocalDateTime icePhenomenaDate;
    private Integer overgrowth;
    private LocalDateTime overgrowthDate;

    public OtherMeasurement() {
    }

    public OtherMeasurement(Integer id, Station station, Float waterTemp, LocalDateTime waterTempDate,
                            Integer icePhenomena, LocalDateTime icePhenomenaDate, Integer overgrowth, LocalDateTime overgrowthDate) {
        this.id = id;
        this.station = station;
        this.waterTemp = waterTemp;
        this.waterTempDate = waterTempDate;
        this.icePhenomena = icePhenomena;
        this.icePhenomenaDate = icePhenomenaDate;
        this.overgrowth = overgrowth;
        this.overgrowthDate = overgrowthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Float getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Float waterTemp) {
        this.waterTemp = waterTemp;
    }

    public LocalDateTime getWaterTempDate() {
        return waterTempDate;
    }

    public void setWaterTempDate(LocalDateTime waterTempDate) {
        this.waterTempDate = waterTempDate;
    }

    public Integer getIcePhenomena() {
        return icePhenomena;
    }

    public void setIcePhenomena(Integer icePhenomena) {
        this.icePhenomena = icePhenomena;
    }

    public LocalDateTime getIcePhenomenaDate() {
        return icePhenomenaDate;
    }

    public void setIcePhenomenaDate(LocalDateTime icePhenomenaDate) {
        this.icePhenomenaDate = icePhenomenaDate;
    }

    public Integer getOvergrowth() {
        return overgrowth;
    }

    public void setOvergrowth(Integer overgrowth) {
        this.overgrowth = overgrowth;
    }

    public LocalDateTime getOvergrowthDate() {
        return overgrowthDate;
    }

    public void setOvergrowthDate(LocalDateTime overgrowthDate) {
        this.overgrowthDate = overgrowthDate;
    }
}
