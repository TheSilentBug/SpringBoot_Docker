package com.reyhan.hotel.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reyhan.hotel.entity.Reservation;

/**
 * رابط دسترسی به داده‌های رزرو
 * این اینترفیس عملیات CRUD و بررسی تداخل رزروها را فراهم می‌کند
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	/**
	 * شمارش تعداد رزروهای تداخل‌دار برای یک اتاق در بازه زمانی مشخص
	 * این متد برای بررسی موجود بودن اتاق استفاده می‌شود
	 * 
	 * @param roomId شناسه اتاق
	 * @param startDate تاریخ شروع بازه زمانی
	 * @param endDate تاریخ پایان بازه زمانی
	 * @return تعداد رزروهای تداخل‌دار (اگر 0 باشد، اتاق موجود است)
	 */
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


 