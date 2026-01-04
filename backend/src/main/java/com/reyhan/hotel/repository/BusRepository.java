package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * رابط دسترسی به داده‌های اتوبوس
 */
public interface BusRepository extends JpaRepository<Bus, Long> {
    /**
     * جستجوی اتوبوس بر اساس مبدأ، مقصد و تاریخ
     */
    @Query("SELECT b FROM Bus b WHERE b.origin = :origin AND b.destination = :destination AND b.departureDate = :date AND b.availableSeats > 0")
    List<Bus> searchBuses(@Param("origin") String origin,
                          @Param("destination") String destination,
                          @Param("date") LocalDate date);

    /**
     * جستجوی اتوبوس بر اساس نوع
     */
    List<Bus> findByBusTypeAndDepartureDateAndOriginAndDestination(String busType, LocalDate date, String origin, String destination);
}

