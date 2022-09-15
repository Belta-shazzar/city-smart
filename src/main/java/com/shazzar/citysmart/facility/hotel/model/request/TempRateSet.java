package com.shazzar.citysmart.facility.hotel.model.request;

import lombok.Data;

@Data
public class TempRateSet {
    private Long hotelId;
    private double rate;
}
