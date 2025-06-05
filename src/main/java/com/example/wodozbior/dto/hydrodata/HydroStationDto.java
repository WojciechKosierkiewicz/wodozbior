package com.example.wodozbior.dto.hydrodata;

import lombok.Data;

@Data
public class HydroStationDto {
    private String idStacji;
    private String stacja;
    private String rzeka;
    private String wojewodztwo;

    private String stanWody;
    private String stanWodyDataPomiaru;

    private String temperaturaWody;
    private String temperaturaWodyDataPomiaru;

    private String zjawiskoLodowe;
    private String zjawiskoLodoweDataPomiaru;

    private String zjawiskoZarastania;
    private String zjawiskoZarastaniaDataPomiaru;


    public HydroStationDto() {
        // Default constructor
    }

    public String getIdStacji() {
        return idStacji;
    }

    public void setIdStacji(String idStacji) {
        this.idStacji = idStacji;
    }

    public String getStacja() {
        return stacja;
    }

    public void setStacja(String stacja) {
        this.stacja = stacja;
    }

    public String getRzeka() {
        return rzeka;
    }

    public void setRzeka(String rzeka) {
        this.rzeka = rzeka;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getStanWody() {
        return stanWody;
    }

    public void setStanWody(String stanWody) {
        this.stanWody = stanWody;
    }

    public String getStanWodyDataPomiaru() {
        return stanWodyDataPomiaru;
    }

    public void setStanWodyDataPomiaru(String stanWodyDataPomiaru) {
        this.stanWodyDataPomiaru = stanWodyDataPomiaru;
    }

    public String getTemperaturaWody() {
        return temperaturaWody;
    }

    public void setTemperaturaWody(String temperaturaWody) {
        this.temperaturaWody = temperaturaWody;
    }

    public String getTemperaturaWodyDataPomiaru() {
        return temperaturaWodyDataPomiaru;
    }

    public void setTemperaturaWodyDataPomiaru(String temperaturaWodyDataPomiaru) {
        this.temperaturaWodyDataPomiaru = temperaturaWodyDataPomiaru;
    }

    public String getZjawiskoLodowe() {
        return zjawiskoLodowe;
    }

    public void setZjawiskoLodowe(String zjawiskoLodowe) {
        this.zjawiskoLodowe = zjawiskoLodowe;
    }

    public String getZjawiskoLodoweDataPomiaru() {
        return zjawiskoLodoweDataPomiaru;
    }

    public void setZjawiskoLodoweDataPomiaru(String zjawiskoLodoweDataPomiaru) {
        this.zjawiskoLodoweDataPomiaru = zjawiskoLodoweDataPomiaru;
    }

    public String getZjawiskoZarastania() {
        return zjawiskoZarastania;
    }

    public void setZjawiskoZarastania(String zjawiskoZarastania) {
        this.zjawiskoZarastania = zjawiskoZarastania;
    }

    public String getZjawiskoZarastaniaDataPomiaru() {
        return zjawiskoZarastaniaDataPomiaru;
    }

    public void setZjawiskoZarastaniaDataPomiaru(String zjawiskoZarastaniaDataPomiaru) {
        this.zjawiskoZarastaniaDataPomiaru = zjawiskoZarastaniaDataPomiaru;
    }
}
