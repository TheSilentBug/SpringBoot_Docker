package com.reyhan.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reyhan.hotel.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}


