package com.example.wodozbior.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HydroDataControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET /api/hydrodata/stations")
    void getAllStationsForMap() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hydrodata/stations", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/stations/{id}")
    void getStationById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hydrodata/stations/1", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/stations/list")
    void getBasicStations() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hydrodata/stations/list", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/stations/{id}/chart")
    void getChartData() {
        String url = String.format("/api/hydrodata/stations/1/chart?startDate=2025-06-01&endDate=2025-06-10");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/alerts")
    void getAlerts() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hydrodata/alerts", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/stations/related")
    void getStationsRelated() {
        String url = String.format("/api/hydrodata/stations/related?stationId=1");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("GET /api/hydrodata/rivers")
    void getAllRivers() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hydrodata/rivers", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }
}
