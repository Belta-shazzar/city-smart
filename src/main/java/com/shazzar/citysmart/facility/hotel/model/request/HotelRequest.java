package com.shazzar.citysmart.facility.hotel.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelRequest {
    private Long hotelId;
    private Long ownerId;
    private String hotelName;
    private String state;
    private String LGA;
    private String address;
    private BigDecimal pricePerNight;
}