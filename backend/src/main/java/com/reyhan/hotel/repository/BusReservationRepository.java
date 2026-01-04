package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.BusReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * رابط دسترسی به داده‌های رزرو اتوبوس
 */
public interface BusReservationRepository extends JpaRepository<BusReservation, Long> {
}

