package com.reyhan.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reyhan.hotel.entity.Room;

/**
 * رابط دسترسی به داده‌های اتاق
 * این اینترفیس عملیات CRUD و جستجوی اتاق‌های موجود را فراهم می‌کند
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
	/**
	 * پیدا کردن تمام اتاق‌های موجود یک هتل در بازه زمانی مشخص
	 * این متد اتاق‌هایی را برمی‌گرداند که در بازه زمانی درخواستی رزرو نشده‌اند
	 * 
	 * @param hotelId شناسه هتل
	 * @param startDate تاریخ شروع بازه زمانی
	 * @param endDate تاریخ پایان بازه زمانی
	 * @return لیست اتاق‌های موجود
	 */
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


