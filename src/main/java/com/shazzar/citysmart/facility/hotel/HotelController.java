package com.shazzar.citysmart.facility.hotel;

import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.facility.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("smart-city/hotel")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/create-hotel")
    private ResponseEntity<Hotel> createHotel(@RequestBody HotelRequest request) {
        return new ResponseEntity<>(hotelService.createHotel(request), HttpStatus.CREATED);
    }
}
