package com.example.wodozbior.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter(mapper);
        List<MediaType> types = new ArrayList<>(jackson.getSupportedMediaTypes());
        types.add(MediaType.TEXT_HTML);                        // IMGW returns text/html but with JSON body
        jackson.setSupportedMediaTypes(types);
        return new RestTemplate(List.of(jackson));
    }
}
