package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * رابط دسترسی به داده‌های رزرو پرواز
 */
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
}

