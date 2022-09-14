package com.shazzar.citysmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CitySmartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitySmartApplication.class, args);
    }

}
