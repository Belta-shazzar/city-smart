package com.shazzar.citysmart.facility.hotel.service;

import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.HotelRepo;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepo hotelRepo;
    private final UserServiceImpl userService;

    public HotelServiceImpl(HotelRepo hotelRepo, UserServiceImpl userService) {
        this.hotelRepo = hotelRepo;
        this.userService = userService;
    }

    @Override
    public Hotel createHotel(HotelRequest request) {
//        Paused to create "createUser" Functionality
        return null;
    }
}
