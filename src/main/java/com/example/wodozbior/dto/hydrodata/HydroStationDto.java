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
}
