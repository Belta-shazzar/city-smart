package com.shazzar.citysmart.facility.hotel.service;

import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.facility.hotel.model.request.TempRateSet;
import com.shazzar.citysmart.facility.hotel.model.response.HotelHomePageResponse;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelRequest hotelRequest, Authentication authentication);

    PublicResponse uploadHotelFiles(List<MultipartFile> files, Long hotelId, Authentication authentication);

    HotelResponse getHotelById(Long hotelId);

    List<HotelHomePageResponse> loadHomePage(Integer pageNo, Integer pageSize, String sortBy);

    PublicResponse updateRating(TempRateSet rateSet);
}
