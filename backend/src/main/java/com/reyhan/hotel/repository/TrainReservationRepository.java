package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.TrainReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * رابط دسترسی به داده‌های رزرو قطار
 */
public interface TrainReservationRepository extends JpaRepository<TrainReservation, Long> {
}

