package com.shazzar.citysmart.facility.hotel.service;

import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.facility.extension.Location;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.HotelRepo;
import com.shazzar.citysmart.facility.hotel.model.Mapper;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import com.shazzar.citysmart.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepo hotelRepo;
    private final AppUserService userService;

    public HotelServiceImpl(HotelRepo hotelRepo, AppUserService userService) {
        this.hotelRepo = hotelRepo;
        this.userService = userService;
    }

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Hotel hotel = new Hotel(hotelRequest.getHotelName(), user);
        Location location = new Location();
        location.setAddress(hotelRequest.getAddress());
        location.setLga(hotelRequest.getLga());
        location.setState(hotelRequest.getState());
        hotel.setLocation(location);
        hotelRepo.save(hotel);
        return Mapper.hotelToHotelModel(hotel);
    }
}



/*
        HotelResponse response = new HotelResponse();
        response.setHotelId(hotel.getHotelId());
        response.setHotelName(hotel.getHotelName());
        response.setOwnerName(user.getFirstName() + " " + user.getLastName());
        response.setPricePerNight(hotel.getPricePerNight());*/
