package com.shazzar.citysmart.facility.hotel.model.response;
import com.shazzar.citysmart.facility.extension.Location;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class HotelResponse {
    private Long hotelId;
    private String hotelName;
    private String ownerName;


    private String state;
    private String lga;
    private String address;
//    private String frontViewUrl;
//    private String backViewUrl;
//    private String sideViewUrl;
//
//    private Set<String> randomViewUrl;

//    private BigDecimal pricePerNight;
//    private long likes;
//    private double ratings;
}
