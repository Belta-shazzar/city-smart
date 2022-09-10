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
    - add createHotel functionality
    - add config for Cloudinary
    - add getAllHotelForHomePage functionality (Pagination and sorting)
    - add getAllHotel functionality (Pagination and sorting)
    - add createUser functionality
    */

}
