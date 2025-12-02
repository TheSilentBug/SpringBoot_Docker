package com.reyhan.hotel.repository; // پکیج مخازن داده

import java.time.LocalDate; // نوع تاریخ برای فیلتر
import java.util.List; // لیست نتایج پرس‌وجو

import org.springframework.data.jpa.repository.JpaRepository; // عملیات CRUD عمومی
import org.springframework.data.jpa.repository.Query; // تعریف کوئری سفارشی
import org.springframework.data.repository.query.Param; // نشانه‌گذاری پارامترها

import com.reyhan.hotel.entity.Room; // موجودیت اتاق

/**
 * رابط دسترسی به داده‌های اتاق
 * این اینترفیس عملیات CRUD و جستجوی اتاق‌های موجود را فراهم می‌کند
 */
public interface RoomRepository extends JpaRepository<Room, Long> { // مخزن اتاق با کلید Long
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
	""") // کوئری JPA برای پیدا کردن اتاق‌های بدون رزرو در بازه
	List<Room> findAvailableRooms(@Param("hotelId") Long hotelId, // شناسه هتل
	                              @Param("startDate") LocalDate startDate, // شروع بازه
	                              @Param("endDate") LocalDate endDate); // پایان بازه
}


