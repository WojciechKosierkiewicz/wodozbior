package com.example.wodozbior.service;


import com.example.wodozbior.dto.hydrodata.*;
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
       return stationMapDtoList;
    }


    public StationDetailsDto getStationById(String id) {
        return null;
    }

    public List<StationBasicDto> getAllStationsBasicInfo() {
        List<StationBasicDto> stationBasicDtoList = new ArrayList<>();
        return stationBasicDtoList;
    }

    public ChartDataDto getChartDataForStation(String id, String startDate, String endDate) {
        ChartDataDto chartDataDto = new ChartDataDto();
        return chartDataDto;
    }

    public List<AlertDto> getCurrentAlerts() {
        List<AlertDto> alertDtoList = new ArrayList<>();
        return alertDtoList;
    }
}
