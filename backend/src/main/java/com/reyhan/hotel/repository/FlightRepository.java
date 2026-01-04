package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * رابط دسترسی به داده‌های پرواز
 */
public interface FlightRepository extends JpaRepository<Flight, Long> {
    /**
     * جستجوی پرواز بر اساس مبدأ، مقصد و تاریخ
     */
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND f.departureDate = :date AND f.availableSeats > 0")
    List<Flight> searchFlights(@Param("origin") String origin,
                               @Param("destination") String destination,
                               @Param("date") LocalDate date);

    /**
     * جستجوی پرواز رفت و برگشت
     */
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND f.departureDate = :departDate AND f.returnDate = :returnDate AND f.availableSeats > 0")
    List<Flight> searchRoundTripFlights(@Param("origin") String origin,
                                        @Param("destination") String destination,
                                        @Param("departDate") LocalDate departDate,
                                        @Param("returnDate") LocalDate returnDate);
}

