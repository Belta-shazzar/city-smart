package com.shazzar.citysmart.facility.hotel.service;

import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import org.springframework.security.core.Authentication;

public interface HotelService {
    HotelResponse createHotel(HotelRequest hotelRequest, Authentication authentication);
}
