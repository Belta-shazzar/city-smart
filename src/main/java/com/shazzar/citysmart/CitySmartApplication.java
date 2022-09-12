package com.shazzar.citysmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitySmartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitySmartApplication.class, args);
    }

    /*
    TODO:
    - create jwt features
    - email verification
    - user sign in / sign up
    - create hotel
    - get hotels for home page (pagination and sort)
    - get hotels for hotels page
    */

}
