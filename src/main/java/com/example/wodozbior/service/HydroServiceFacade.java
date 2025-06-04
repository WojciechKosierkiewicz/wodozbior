package com.example.wodozbior.service;


import com.example.wodozbior.dto.hydrodata.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HydroServiceFacade { // TODO implementacja są tylo placeholdery zeby nie wyjebało kompilacji
    //  fasda powinna korzystac z serwisów ImgwHydro1Service i ImgwHydro2Service
    //  w serwisach mozna sobie rozdzielić logikę na mniejsze kawałki np do pasowania jsona i z tego zroibc interfejs
    // w reposiotry metody do komunikacji z baza danych hibernate/jpa/orm
    //  w controllerze powinny być tylko endpointy i wywołania metod z fasady
    //  w serwisach powinny być metody do obslugi logiki biznesowej
    //  w repozytoriach powinny być metody do obslugi bazy danych
    //  w dto powinny być klasy do przesyłania danych między warstwami
    //  w utils cos gdzie nie pasuje nic
    //  w configu jest  konfiguracja aplikacji i jesli cos blokuje enpointy to permitAll bedzie smigało
    //  w enity klasy 1 do 1  jak w bazie
    // appp properties mozna zmienic tworzenie bazy z create-drop na update i inne zminne jak url do api
    //  i wtedy nie bedzie kasowało bazy przy kazdym uruchomieniu
    //  TODO zmienne do env zanim ktoś wjebie na gita hasla application.properties zawsze do .gitignore



    public List<StationMapDto> getAllStationsForMap() {
        List<StationMapDto> stationMapDtoList = new ArrayList<>();
        StationMapDto s1 = new StationMapDto("2023-10-01T12:00:00Z", "1", "Wisła", 52.2297, 21.0122, 300, "#FF0000");
        StationMapDto s2 = new StationMapDto("2023-10-01T12:00:00Z", "2", "Odra", 51.1079, 17.0385, 250, "#00FF00");
        StationMapDto s3 = new StationMapDto("2023-10-01T12:00:00Z", "3", "Warta", 52.4064, 16.9252, 200, "#0000FF");
        stationMapDtoList.add(s1);
        stationMapDtoList.add(s2);
        stationMapDtoList.add(s3);
       return stationMapDtoList;
    }

    public List<Pair<String,List<StationBasicDto>>> getAllRivers(){

        List<Pair<String, List<StationBasicDto>>> rivers = new ArrayList<>();
        List<StationBasicDto> wislaStations = new ArrayList<>();
        wislaStations.add(new StationBasicDto("1", "Wisła", 52.2297, 21.0122, "300"));
        wislaStations.add(new StationBasicDto("2", "Wisła Płock", 52.5500, 19.7000, "280"));

        List<StationBasicDto> odraStations = new ArrayList<>();
        odraStations.add(new StationBasicDto("3", "Odra Wrocław", 51.1079, 17.0385, "250"));

        rivers.add(Pair.of("Wisła", wislaStations));
        rivers.add(Pair.of("Odra", odraStations));

        return rivers;
    }


    public StationDetailsDto getStationById(String id) {
        StationDetailsDto stationDetailsDto = new StationDetailsDto();
        stationDetailsDto.setId(id);
        stationDetailsDto.setName("wwa");
        stationDetailsDto.setRiver("Wisła");
        stationDetailsDto.setRegion("Mazowieckie");
        stationDetailsDto.setLatitude(52.2297);
        stationDetailsDto.setLongitude(21.0122);
        stationDetailsDto.setWaterLevel(300);
        stationDetailsDto.setWaterLevelDate("2023-10-01T12:00:00Z");
        stationDetailsDto.setWaterTemperature(15.5);
        stationDetailsDto.setWaterTemperatureDate("2023-10-01T12:00:00Z");
        stationDetailsDto.setIcePhenomenon("Brak");
        stationDetailsDto.setIcePhenomenonDate("2023-10-01T12:00:00Z");
        stationDetailsDto.setOvergrowthPhenomenon("Brak");
        stationDetailsDto.setOvergrowthPhenomenonDate("2023-10-01T12:00:00Z");

        return  stationDetailsDto;
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        List<StationBasicDto> stationBasicDtoList = new ArrayList<>();
        StationBasicDto s1 = new StationBasicDto();
        s1.setId("1");
        s1.setName("Wisła");
        s1.setLatitude(52.2297);
        s1.setLongitude(21.0122);
        s1.setWaterLevel("300");

        StationBasicDto s2 = new StationBasicDto();
        s2.setId("2");
        s2.setName("Odra");
        s2.setLatitude(51.1079);
        s2.setLongitude(17.0385);
        s2.setWaterLevel("250");
        StationBasicDto s3 = new StationBasicDto();
        s3.setId("3");
        s3.setName("Warta");
        s3.setLatitude(52.4064);
        s3.setLongitude(16.9252);
        s3.setWaterLevel("200");
        stationBasicDtoList.add(s1);
        stationBasicDtoList.add(s2);
        stationBasicDtoList.add(s3);
        return stationBasicDtoList;
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        ChartDataDto chartDataDto = new ChartDataDto();
        for (int i = 0; i < 10; i++) {
            MeasurementPointDto point = new MeasurementPointDto();
            point.setDate("2023-10-0" + (i+1) + "T12:00:00Z");
            point.setValue(200 + (int)(Math.random() * 100));
            chartDataDto.getWaterLevel().add(point);
        }
        for (int i = 0; i < 10; i++) {
            MeasurementPointDto point = new MeasurementPointDto();
            point.setDate("2023-10-0" + (i+1) + "T12:00:00Z");
            point.setValue(200 + (int)(Math.random() * 100));
            chartDataDto.getWaterTemperature().add(point);
        }
        for (int i = 0; i < 10; i++) {
            MeasurementPointDto point = new MeasurementPointDto();
            point.setDate("2023-10-0" + (i+1) + "T12:00:00Z");
            point.setValue(200 + (int)(Math.random() * 100));
            chartDataDto.getWaterFlow().add(point);
        }

        return chartDataDto;
    }

    public List<AlertDto> getCurrentAlerts() {
        List<AlertDto> alertDtoList = new ArrayList<>();
        return alertDtoList;
    }
}
