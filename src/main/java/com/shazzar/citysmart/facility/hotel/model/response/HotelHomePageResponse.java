package com.shazzar.citysmart.facility.hotel.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HotelHomePageResponse {
    private Long hotelId;
    private String hotelName;
    private String state;
    private List<String> randomViewUrl;

    private BigDecimal pricePerNight;
    private long likes;
    private double ratings;
}
