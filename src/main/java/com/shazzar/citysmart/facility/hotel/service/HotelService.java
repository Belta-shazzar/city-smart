package com.shazzar.citysmart.facility.hotel.service;

import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;

public interface HotelService {

    Hotel createHotel(HotelRequest request);
}
