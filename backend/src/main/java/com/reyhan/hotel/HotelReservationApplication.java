package com.reyhan.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * کلاس اصلی برنامه Spring Boot برای سامانه رزرواسیون هتل
 * این کلاس نقطه ورود برنامه است و با استفاده از @SpringBootApplication
 * تمام تنظیمات خودکار Spring Boot را فعال می‌کند
 */
@SpringBootApplication
public class HotelReservationApplication {
	/**
	 * متد اصلی برنامه که اپلیکیشن Spring Boot را راه‌اندازی می‌کند
	 * @param args آرگومان‌های خط فرمان
	 */
	public static void main(String[] args) {
		SpringApplication.run(HotelReservationApplication.class, args);
	}
}


