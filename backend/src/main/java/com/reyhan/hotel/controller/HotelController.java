package com.reyhan.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reyhan.hotel.entity.Hotel;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.HotelRepository;
import com.reyhan.hotel.repository.RoomRepository;

/**
 * کنترلر REST API برای مدیریت هتل‌ها و اتاق‌ها
 * این کلاس endpointهای مربوط به لیست هتل‌ها و اتاق‌ها را فراهم می‌کند
 */
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {
	/**
	 * مخزن داده‌های هتل برای دسترسی به اطلاعات هتل‌ها
	 */
	private final HotelRepository hotelRepository;
	
	/**
	 * مخزن داده‌های اتاق برای جستجوی اتاق‌های موجود
	 */
	private final RoomRepository roomRepository;

	/**
	 * سازنده کنترلر که وابستگی‌ها را تزریق می‌کند
	 * @param hotelRepository مخزن هتل‌ها
	 * @param roomRepository مخزن اتاق‌ها
	 */
	public HotelController(HotelRepository hotelRepository, RoomRepository roomRepository) {
		this.hotelRepository = hotelRepository;
		this.roomRepository = roomRepository;
	}

	/**
	 * دریافت لیست تمام هتل‌های موجود در سیستم
	 * @return لیست تمام هتل‌ها
	 */
	@GetMapping
	public List<Hotel> listHotels() {
		return hotelRepository.findAll();
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
	@GetMapping("/{id}/rooms")
	public ResponseEntity<List<Room>> listRooms(@PathVariable Long id,
	                                            @RequestParam(required = false) Boolean available,
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		// اگر درخواست برای اتاق‌های موجود است و تاریخ‌ها ارسال شده‌اند
		if (Boolean.TRUE.equals(available) && from != null && to != null) {
			return ResponseEntity.ok(roomRepository.findAvailableRooms(id, from, to));
		}
		// در غیر این صورت تمام اتاق‌های هتل را برمی‌گرداند
		return hotelRepository.findById(id)
			.map(h -> ResponseEntity.ok(h.getRooms()))
			.orElse(ResponseEntity.notFound().build());
	}
}


