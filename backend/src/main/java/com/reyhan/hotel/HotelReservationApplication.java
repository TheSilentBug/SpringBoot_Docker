package com.reyhan.hotel; // تعریف پکیج اصلی پروژه

import org.springframework.boot.SpringApplication; // کلاس Spring برای بوت‌استرپ اپلیکیشن
import org.springframework.boot.autoconfigure.SpringBootApplication; // فعال‌سازی auto-configuration

/**
 * کلاس اصلی برنامه Spring Boot برای سامانه رزرواسیون هتل
 * این کلاس نقطه ورود برنامه است و با استفاده از @SpringBootApplication
 * تمام تنظیمات خودکار Spring Boot را فعال می‌کند
 */
@SpringBootApplication // مشخص می‌کند این کلاس نقطه شروع Spring Boot است
public class HotelReservationApplication { // تعریف کلاس اصلی برنامه
	/**
	 * متد اصلی برنامه که اپلیکیشن Spring Boot را راه‌اندازی می‌کند
	 * @param args آرگومان‌های خط فرمان
	 */
	public static void main(String[] args) { // متد main به عنوان ورودی JVM
		SpringApplication.run(HotelReservationApplication.class, args); // اجرای اپلیکیشن Spring بر اساس تنظیمات کلاس
	}
}


