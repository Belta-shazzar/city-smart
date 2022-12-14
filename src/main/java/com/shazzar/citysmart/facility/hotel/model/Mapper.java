package com.shazzar.citysmart.facility.hotel.model;

import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.model.response.HotelHomePageResponse;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static HotelResponse hotelToHotelModel(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.setHotelId(hotel.getHotelId());
        response.setHotelName(hotel.getHotelName());
        String hotelOwnerFullName = hotel.getOwner().getFirstName() + " " +
                hotel.getOwner().getLastName();
        response.setOwnerName(hotelOwnerFullName);
        response.setState(hotel.getLocation().getState());
        response.setAddress(hotel.getLocation().getAddress());
//        response.setFrontViewUrl(hotel.getImages().getFrontViewUrl());
//        response.setBackViewUrl(hotel.getImages().getBackViewUrl());
//        response.setSideViewUrl(hotel.getImages().getSideViewUrl());
        response.setRandomViewUrl(hotel.getImages().getRandomViewUrl());
        response.setPricePerNight(hotel.getPricePerNight());
//        response.setLikes(hotel.getLikes().size());
//        response.setRatings(hotel.getRatings());

        return response;
    }

    private static HotelHomePageResponse HotelModelForHomePage(Hotel hotel) {
        HotelHomePageResponse response = new HotelHomePageResponse();
        response.setHotelId(hotel.getHotelId());
        response.setHotelName(hotel.getHotelName());
        response.setState(hotel.getLocation().getState());
        response.setRandomViewUrl(hotel.getImages().getRandomViewUrl());
        response.setPricePerNight(hotel.getPricePerNight());
        response.setLikes(hotel.getLikes().size());
        response.setRatings(hotel.getRatings());

        return response;
    }

    public static List<HotelHomePageResponse> hotelsToHotelModels(Page<Hotel> pageResult) {
        List<HotelHomePageResponse> responses = new ArrayList<>();

        for (Hotel hotel : pageResult) {
            responses.add(HotelModelForHomePage(hotel));
        }

        return responses;
    }
}
