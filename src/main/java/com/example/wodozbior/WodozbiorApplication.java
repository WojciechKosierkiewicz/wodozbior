package com.example.wodozbior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class WodozbiorApplication {
    public static void main(String[] args) {
        SpringApplication.run(WodozbiorApplication.class, args);
    }
    @GetMapping("/Test")
    public String Test(){
        return "Hello World";
    }
}
