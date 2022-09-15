package com.shazzar.citysmart.facility.hotel.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.exception.AlreadyExistException;
import com.shazzar.citysmart.exception.ResourceNotFoundException;
import com.shazzar.citysmart.facility.FacilityUtil;
import com.shazzar.citysmart.facility.extension.Images;
import com.shazzar.citysmart.facility.extension.Location;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.facility.hotel.HotelRepo;
import com.shazzar.citysmart.facility.hotel.model.Mapper;
import com.shazzar.citysmart.facility.hotel.model.request.HotelRequest;
import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.facility.hotel.model.request.TempRateSet;
import com.shazzar.citysmart.facility.hotel.model.response.HotelHomePageResponse;
import com.shazzar.citysmart.facility.hotel.model.response.HotelResponse;
import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.role.UserRole;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Hotel", "id", hotelId)));
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        return Mapper.hotelToHotelModel(getById(hotelId));
    }

    @Override
    public List<HotelHomePageResponse> loadHomePage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Hotel> pageResult = hotelRepo.findAll(paging);
        if (pageResult.hasContent()) {
            return Mapper.hotelsToHotelModels(pageResult);
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    @SneakyThrows
    public PublicResponse updateRating(TempRateSet rateSet) {
        Hotel hotel = hotelRepo.findById(rateSet.getHotelId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Hotel", "id", rateSet)));
        hotel.setRatings(rateSet.getRate());
        hotelRepo.save(hotel);
        return new PublicResponse(hotel.getHotelName() + " rate has been updated successfully");
    }

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        checkIfHotelNameExist(hotelRequest.getHotelName());
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

    @SneakyThrows
    public void checkIfHotelNameExist(String hotelName) {
        Optional<Hotel> hotel = hotelRepo.findByHotelName(hotelName);
        if (hotel.isPresent()) {
            throw new AlreadyExistException(String.format("%s with %s %s, already exist",
                    "Hotel", "hotel name", hotelName));
        }
    }

    @Override
    public PublicResponse uploadHotelFiles(List<MultipartFile> files, Long hotelId, Authentication authentication) {
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
        return new PublicResponse("Images uploaded successfully");
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
