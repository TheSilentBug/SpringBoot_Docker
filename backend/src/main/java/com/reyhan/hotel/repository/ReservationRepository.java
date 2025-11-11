package com.reyhan.hotel.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reyhan.hotel.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	@Query("""
		SELECT COUNT(res) FROM Reservation res
		WHERE res.room.id = :roomId
		  AND res.startDate <= :endDate
		  AND res.endDate >= :startDate
	""")
	long countOverlapping(@Param("roomId") Long roomId,
	                      @Param("startDate") LocalDate startDate,
	                      @Param("endDate") LocalDate endDate);
}


 