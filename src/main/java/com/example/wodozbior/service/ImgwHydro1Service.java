package com.example.wodozbior.service;

import com.example.wodozbior.datastructures.Stacja;
import com.example.wodozbior.dto.hydrodata.HydroStationDto;
import com.example.wodozbior.repository.DBFacade;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

@Service
public class ImgwHydro1Service {
    private final DBFacade dbFacade;

    public ImgwHydro1Service(DBFacade dbFacade) {
        this.dbFacade = dbFacade;
    }

    public ArrayList<HydroStationDto> getStacjas() {
        ArrayList<HydroStationDto> stacjas = dbFacade.getStations();
        return stacjas;
    }
}