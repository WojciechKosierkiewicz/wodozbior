package com.example.wodozbior.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "water_levels",
        uniqueConstraints = @UniqueConstraint(name = "unique_station_time", columnNames = {"station_id", "time"}))
public class WaterLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(nullable = false)
    private Float level;

    private LocalDateTime time;
    private Float flowRate;
    private LocalDateTime flowDate;

    public WaterLevel() {
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

    public Float getLevel() {
        return level;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Float getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(Float flowRate) {
        this.flowRate = flowRate;
    }

    public LocalDateTime getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(LocalDateTime flowDate) {
        this.flowDate = flowDate;
    }
}