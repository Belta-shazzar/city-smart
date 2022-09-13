package com.shazzar.citysmart.facility.hotel.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.exception.ResourceNotFoundException;
import com.shazzar.citysmart.facility.FacilityUtil;
import com.shazzar.citysmart.facility.extension.Images;
import com.shazzar.citysmart.facility.extension.Location;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.HotelRepo;
import com.shazzar.citysmart.facility.hotel.model.Mapper;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.facility.hotel.model.response.HotelActionResponse;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.role.UserRole;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepo hotelRepo;
    private final AppUserService userService;
    private final Cloudinary cloudinaryConfig;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";

    public HotelServiceImpl(HotelRepo hotelRepo, AppUserService userService, Cloudinary cloudinaryConfig) {
        this.hotelRepo = hotelRepo;
        this.userService = userService;
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @SneakyThrows
    public Hotel getById(Long hotelId) {
        return hotelRepo.findById(hotelId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Position", "id", hotelId)));
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        return Mapper.hotelToHotelModel(getById(hotelId));
    }

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Hotel hotel = new Hotel(hotelRequest.getHotelName(), user);
        hotel.setPricePerNight(hotelRequest.getPricePerNight());

        Location location = new Location();
        location.setAddress(hotelRequest.getAddress());
        location.setState(hotelRequest.getState());
        hotel.setLocation(location);
        hotel.setImages(new Images());
        hotelRepo.save(hotel);
        if (user.getRole().equals(UserRole.CUSTOMER)) {
            user.setRole(UserRole.FACILITY_OWNER);
        }
        return Mapper.hotelToHotelModel(hotel);
    }

    @Override
    public HotelActionResponse uploadHotelFiles(List<MultipartFile> files, Long hotelId, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Hotel hotel = getById(hotelId);
        Images images = hotel.getImages();

        if (hotel.getOwner().equals(user)) {
            for (MultipartFile file : files) {
                String imgUrl = uploadHotelPictures(file);
                images.getRandomViewUrl().add(imgUrl);
            }
        }
        hotel.setImages(images);
        hotelRepo.save(hotel);
        return new HotelActionResponse("Images uploaded successfully");
    }

    private String uploadHotelPictures(MultipartFile file) {
        try {
            File UploadFile = FacilityUtil.convertMultipartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(UploadFile, ObjectUtils.emptyMap());

            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



/*
        HotelResponse response = new HotelResponse();
        response.setHotelId(hotel.getHotelId());
        response.setHotelName(hotel.getHotelName());
        response.setOwnerName(user.getFirstName() + " " + user.getLastName());
        response.setPricePerNight(hotel.getPricePerNight());*/
