package com.shazzar.citysmart.facility.hotel.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelRequest {
    private String hotelName;
    private String state;
    private String lga;
    private String address;
}