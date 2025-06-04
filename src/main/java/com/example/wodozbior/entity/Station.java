package com.example.wodozbior.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "river_id")
    private River river;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voivodeship_id")
    private Voivodeship voivodeship;

    private Float latitude;
    private Float longitude;

    public Station() {
    }


    public Station(Integer id, String name, River river, Voivodeship voivodeship, Float latitude, Float longitude) {
        this.id = id;
        this.name = name;
        this.river = river;
        this.voivodeship = voivodeship;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public River getRiver() {
        return river;
    }

    public void setRiver(River river) {
        this.river = river;
    }

    public Voivodeship getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(Voivodeship voivodeship) {
        this.voivodeship = voivodeship;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}