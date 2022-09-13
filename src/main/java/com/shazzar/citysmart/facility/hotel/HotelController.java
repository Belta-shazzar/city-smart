package com.shazzar.citysmart.facility.hotel;

import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import com.shazzar.citysmart.facility.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/create-hotel")
    @PreAuthorize("hasAnyAuthority('Role_CUSTOMER', 'Role_FACILITY_OWNER')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest, Authentication authentication) {
        return new ResponseEntity<>(hotelService.createHotel(hotelRequest, authentication), HttpStatus.CREATED);
    }
}
