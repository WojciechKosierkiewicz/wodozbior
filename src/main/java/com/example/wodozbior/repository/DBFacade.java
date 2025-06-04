package com.example.wodozbior.repository;


import com.example.wodozbior.dto.hydrodata.HydroStationDto;
import com.example.wodozbior.dto.hydrodata.StationDetailsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DBFacade {
    static DBFacade instance;



     public ArrayList<HydroStationDto> getStations(){
        //TODO
         //ZWRACA LISTE WSZYSTKICH STACJI TAK JAK W API
         //AKTUALNY STAN
         //WYPEŁNIJ INFO Z DTO

        ArrayList<HydroStationDto> stations = new ArrayList<>();
        return stations;
     }

     public StationDetailsDto getStationStationById(int id){
         StationDetailsDto station = new StationDetailsDto();
         return station;
     }
}
