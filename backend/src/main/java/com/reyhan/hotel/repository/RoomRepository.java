package com.reyhan.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reyhan.hotel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	@Query("""
		SELECT r FROM Room r 
		WHERE r.hotel.id = :hotelId AND NOT EXISTS (
			SELECT res FROM Reservation res 
			WHERE res.room = r 
			  AND res.startDate <= :endDate 
			  AND res.endDate >= :startDate
		)
	""")
	List<Room> findAvailableRooms(@Param("hotelId") Long hotelId,
	                              @Param("startDate") LocalDate startDate,
	                              @Param("endDate") LocalDate endDate);
}


