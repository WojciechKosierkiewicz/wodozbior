package com.example.wodozbior.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class HydroApiFetchService {

    @Value("${hydro.api.url}")
    private final String hydroApiUrl;

    @Value("${hydro2.api.url}")
    private final String hydro2ApiUrl;
    private final WebClient webClient;

    public HydroApiFetchService(@Value("${hydro.api.url}") String hydroApiUrl,
                                @Value("${hydro2.api.url}") String hydro2ApiUrl) {
        this.hydroApiUrl = hydroApiUrl;
        this.hydro2ApiUrl = hydro2ApiUrl;
        this.webClient = WebClient.builder().build();
    }

    public List<String> fetchDataFromHydroApi() {
        String hydroData = fetchData(hydroApiUrl);
        String hydroData2 = fetchData(hydro2ApiUrl);
        return List.of(hydroData, hydroData2);
    }

    private String fetchData(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    System.err.println("Błąd podczas pobierania danych z " + url + ": " + e.getMessage());
                    return Mono.just("[]");
                })
                .block();
    }

}
