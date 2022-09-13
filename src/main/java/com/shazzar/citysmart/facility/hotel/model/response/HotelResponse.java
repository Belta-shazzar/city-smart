package com.shazzar.citysmart.facility.hotel.model.response;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HotelResponse {
    private Long hotelId;
    private String hotelName;
    private String ownerName;


    private String state;
    private String address;
//    private String frontViewUrl;
//    private String backViewUrl;
//    private String sideViewUrl;
//
    private List<String> randomViewUrl;

    private BigDecimal pricePerNight;
//    private long likes;
//    private double ratings;
}
