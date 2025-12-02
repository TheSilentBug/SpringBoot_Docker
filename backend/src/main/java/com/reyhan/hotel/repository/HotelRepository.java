package com.reyhan.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reyhan.hotel.entity.Hotel;

/**
 * رابط دسترسی به داده‌های هتل
 * این اینترفیس تمام عملیات CRUD (ایجاد، خواندن، به‌روزرسانی، حذف) را
 * برای موجودیت Hotel فراهم می‌کند
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}


