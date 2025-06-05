package com.example.wodozbior.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient imgwWebClient() {

        HttpClient http = HttpClient.create()
                                    .compress(true);        // obsługa gzip/deflate

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(http))
                .defaultHeader("User-Agent",
                        "Mozilla/5.0 (X11; Linux) AppleWebKit/537.36 "
                      + "(KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .defaultHeader("Accept", "*/*")
                .defaultHeader("Accept-Encoding", "gzip, deflate, br")
                .exchangeStrategies(ExchangeStrategies.builder()      // pozwól na >2 MB
                        .codecs(c -> c.defaultCodecs()
                                       .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
}
