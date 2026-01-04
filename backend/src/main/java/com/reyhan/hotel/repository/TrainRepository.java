package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * رابط دسترسی به داده‌های قطار
 */
public interface TrainRepository extends JpaRepository<Train, Long> {
    /**
     * جستجوی قطار بر اساس مبدأ، مقصد و تاریخ
     */
    @Query("SELECT t FROM Train t WHERE t.origin = :origin AND t.destination = :destination AND t.departureDate = :date AND t.availableSeats > 0")
    List<Train> searchTrains(@Param("origin") String origin,
                             @Param("destination") String destination,
                             @Param("date") LocalDate date);

    /**
     * جستجوی قطار بر اساس کلاس
     */
    List<Train> findByTrainClassAndDepartureDateAndOriginAndDestination(String trainClass, LocalDate date, String origin, String destination);
}

