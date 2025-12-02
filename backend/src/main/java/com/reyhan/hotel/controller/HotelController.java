package com.reyhan.hotel.controller; // پکیج مربوط به کنترلرهای REST

import java.time.LocalDate; // نوع تاریخ برای فیلتر کردن اتاق‌ها
import java.util.List; // برای بازگرداندن مجموعه نتایج

import org.springframework.format.annotation.DateTimeFormat; // تبدیل رشته تاریخ ورودی به LocalDate
import org.springframework.http.ResponseEntity; // ساخت پاسخ HTTP با وضعیت مناسب
import org.springframework.web.bind.annotation.CrossOrigin; // مجوز دسترسی از دامنه‌های دیگر
import org.springframework.web.bind.annotation.GetMapping; // نگاشت درخواست GET
import org.springframework.web.bind.annotation.PathVariable; // خواندن متغیر مسیر از URL
import org.springframework.web.bind.annotation.RequestMapping; // تعریف مسیر پایه کنترلر
import org.springframework.web.bind.annotation.RequestParam; // خواندن پارامتر query string
import org.springframework.web.bind.annotation.RestController; // ترکیب @Controller و @ResponseBody

import com.reyhan.hotel.entity.Hotel; // مدل داده هتل
import com.reyhan.hotel.entity.Room; // مدل داده اتاق
import com.reyhan.hotel.repository.HotelRepository; // مخزن CRUD برای هتل
import com.reyhan.hotel.repository.RoomRepository; // مخزن برای جستجوی اتاق‌ها

/**
 * کنترلر REST API برای مدیریت هتل‌ها و اتاق‌ها
 * این کلاس endpointهای مربوط به لیست هتل‌ها و اتاق‌ها را فراهم می‌کند
 */
@RestController // مشخص می‌کند پاسخ‌ها JSON است
@RequestMapping("/api/hotels") // مسیر پایه برای تمام متدها
@CrossOrigin // فعال‌سازی CORS برای فرانت‌اند
public class HotelController { // تعریف کلاس کنترلر
	/**
	 * مخزن داده‌های هتل برای دسترسی به اطلاعات هتل‌ها
	 */
	private final HotelRepository hotelRepository; // وابستگی به مخزن هتل
	
	/**
	 * مخزن داده‌های اتاق برای جستجوی اتاق‌های موجود
	 */
	private final RoomRepository roomRepository; // وابستگی به مخزن اتاق

	/**
	 * سازنده کنترلر که وابستگی‌ها را تزریق می‌کند
	 * @param hotelRepository مخزن هتل‌ها
	 * @param roomRepository مخزن اتاق‌ها
	 */
	public HotelController(HotelRepository hotelRepository, RoomRepository roomRepository) { // سازنده با تزریق وابستگی
		this.hotelRepository = hotelRepository; // مقداردهی مخزن هتل
		this.roomRepository = roomRepository; // مقداردهی مخزن اتاق
	}

	/**
	 * دریافت لیست تمام هتل‌های موجود در سیستم
	 * @return لیست تمام هتل‌ها
	 */
	@GetMapping // هندلر GET /api/hotels
	public List<Hotel> listHotels() { // متد بازگرداننده هتل‌ها
		return hotelRepository.findAll(); // خواندن تمام رکوردها از دیتابیس
	}

	/**
	 * دریافت لیست اتاق‌های یک هتل
	 * اگر پارامتر available=true و تاریخ‌ها ارسال شوند، فقط اتاق‌های موجود برگردانده می‌شوند
	 * در غیر این صورت تمام اتاق‌های هتل برگردانده می‌شوند
	 * 
	 * @param id شناسه هتل
	 * @param available اگر true باشد، فقط اتاق‌های موجود را برمی‌گرداند
	 * @param from تاریخ شروع بازه زمانی (برای جستجوی اتاق‌های موجود)
	 * @param to تاریخ پایان بازه زمانی (برای جستجوی اتاق‌های موجود)
	 * @return لیست اتاق‌ها یا 404 اگر هتل پیدا نشود
	 */
	@GetMapping("/{id}/rooms") // هندلر GET برای اتاق‌های یک هتل
	public ResponseEntity<List<Room>> listRooms(@PathVariable Long id, // شناسه هتل از URL
	                                            @RequestParam(required = false) Boolean available, // پارامتر اختیاری وضعیت
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, // تاریخ شروع فیلتر
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) { // تاریخ پایان فیلتر
		if (Boolean.TRUE.equals(available) && from != null && to != null) { // بررسی درخواست اتاق‌های آزاد
			return ResponseEntity.ok(roomRepository.findAvailableRooms(id, from, to)); // بازگرداندن لیست اتاق‌های آزاد
		}
		return hotelRepository.findById(id) // جستجوی هتل با شناسه
			.map(h -> ResponseEntity.ok(h.getRooms())) // تبدیل به پاسخ شامل اتاق‌ها
			.orElse(ResponseEntity.notFound().build()); // در صورت نبود هتل، برگرداندن 404
	}
}


