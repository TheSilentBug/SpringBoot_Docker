package com.reyhan.hotel.repository; // پکیج مخازن داده

import org.springframework.data.jpa.repository.JpaRepository; // اینترفیس عمومی CRUD

import com.reyhan.hotel.entity.Hotel; // موجودیت هدف

/**
 * رابط دسترسی به داده‌های هتل
 * این اینترفیس تمام عملیات CRUD (ایجاد، خواندن، به‌روزرسانی، حذف) را
 * برای موجودیت Hotel فراهم می‌کند
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> { // مخزن هتل با کلید Long
}


