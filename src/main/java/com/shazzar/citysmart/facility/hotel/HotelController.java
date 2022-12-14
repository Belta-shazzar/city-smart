package com.shazzar.citysmart.facility.hotel;

import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.facility.hotel.model.request.TempRateSet;
import com.shazzar.citysmart.facility.hotel.model.response.HotelHomePageResponse;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import com.shazzar.citysmart.facility.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Role_CUSTOMER', 'Role_FACILITY_OWNER')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest, Authentication authentication) {
        return new ResponseEntity<>(hotelService.createHotel(hotelRequest, authentication), HttpStatus.CREATED);
    }

    @PostMapping("/upload-images")
    @PreAuthorize("hasAuthority('Role_FACILITY_OWNER')")
    public ResponseEntity<PublicResponse> uploadHotelImgFiles(@RequestParam("files")List<MultipartFile> files,
                                                              @RequestParam("hotelId") Long hotelId,
                                                              Authentication authentication) {
        PublicResponse response = hotelService.uploadHotelFiles(files, hotelId, authentication);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable("hotelId") Long hotelId) {
        return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.OK);
    }

    @GetMapping("/home-page")
    public ResponseEntity<List<HotelHomePageResponse>> loadHomePage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(defaultValue = "8") Integer pageSize,
                                                                    @RequestParam(defaultValue = "ratings") String sortBy) {
        List<HotelHomePageResponse> responses = hotelService.loadHomePage(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

//    Temporal
    @PatchMapping("/update-rating")
    public ResponseEntity<PublicResponse> updateRating(@RequestBody TempRateSet rateSet) {
        return new ResponseEntity<>(hotelService.updateRating(rateSet), HttpStatus.OK);
    }
}
