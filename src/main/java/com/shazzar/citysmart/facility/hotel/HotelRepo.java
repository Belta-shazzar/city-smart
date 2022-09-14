package com.shazzar.citysmart.facility.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {
    @Query("select h from Hotel h where h.hotelName = ?1")
    Optional<Hotel> findByHotelName(String hotelName);

}