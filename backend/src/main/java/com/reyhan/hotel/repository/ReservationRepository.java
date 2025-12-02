package com.reyhan.hotel.repository; // پکیج مخازن داده

import java.time.LocalDate; // نوع تاریخ برای بازه رزرو

import org.springframework.data.jpa.repository.JpaRepository; // عملیات CRUD عمومی
import org.springframework.data.jpa.repository.Query; // تعریف کوئری سفارشی
import org.springframework.data.repository.query.Param; // نام‌گذاری پارامترهای کوئری

import com.reyhan.hotel.entity.Reservation; // موجودیت رزرو

/**
 * رابط دسترسی به داده‌های رزرو
 * این اینترفیس عملیات CRUD و بررسی تداخل رزروها را فراهم می‌کند
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> { // مخزن رزرو با کلید Long
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
	""") // کوئری برای بررسی تداخل زمانی
	long countOverlapping(@Param("roomId") Long roomId, // شناسه اتاق
	                      @Param("startDate") LocalDate startDate, // شروع بازه
	                      @Param("endDate") LocalDate endDate); // پایان بازه
}


 