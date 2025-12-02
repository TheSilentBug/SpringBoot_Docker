package com.reyhan.hotel.controller; // پکیج کنترلرهای رزرو

import java.net.URI; // ساخت آدرس برای Location هدر
import java.util.Optional; // مدیریت نتیجه اختیاری از دیتابیس

import org.springframework.http.ResponseEntity; // ساخت پاسخ HTTP
import org.springframework.web.bind.annotation.CrossOrigin; // مجوز CORS
import org.springframework.web.bind.annotation.GetMapping; // هندلر GET
import org.springframework.web.bind.annotation.PathVariable; // استخراج متغیر از مسیر
import org.springframework.web.bind.annotation.PostMapping; // هندلر POST
import org.springframework.web.bind.annotation.RequestBody; // خواندن بدنه JSON
import org.springframework.web.bind.annotation.RequestMapping; // تعیین مسیر پایه
import org.springframework.web.bind.annotation.RestController; // تعریف کنترلر REST

import com.reyhan.hotel.dto.ReservationRequest; // DTO ورودی رزرو
import com.reyhan.hotel.entity.Reservation; // موجودیت رزرو
import com.reyhan.hotel.repository.ReservationRepository; // مخزن CRUD رزرو
import com.reyhan.hotel.service.ReservationService; // سرویس منطق رزرو

import jakarta.validation.Valid; // اعتبارسنجی ورودی‌ها

/**
 * کنترلر REST API برای مدیریت رزروها
 * این کلاس endpointهای مربوط به ایجاد و دریافت رزروها را فراهم می‌کند
 */
@RestController // پاسخ‌ها JSON هستند
@RequestMapping("/api/reservations") // مسیر پایه برای رزرو
@CrossOrigin // مجوز دسترسی از فرانت‌اند
public class ReservationController { // کلاس کنترلر رزرو
	/**
	 * سرویس رزرواسیون برای انجام عملیات کسب‌وکار
	 */
	private final ReservationService reservationService; // وابستگی به سرویس کسب‌وکار
	
	/**
	 * مخزن داده‌های رزرو برای دسترسی مستقیم به رزروها
	 */
	private final ReservationRepository reservationRepository; // وابستگی به مخزن داده

	/**
	 * سازنده کنترلر که وابستگی‌ها را تزریق می‌کند
	 * @param reservationService سرویس رزرواسیون
	 * @param reservationRepository مخزن رزروها
	 */
	public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) { // سازنده با تزریق وابستگی
		this.reservationService = reservationService; // مقداردهی سرویس
		this.reservationRepository = reservationRepository; // مقداردهی مخزن
	}

	/**
	 * ایجاد یک رزرو جدید
	 * این endpoint درخواست رزرو را دریافت می‌کند، اعتبارسنجی می‌کند و رزرو را ایجاد می‌کند
	 * 
	 * @param request درخواست رزرو شامل اطلاعات مهمان و تاریخ‌ها
	 * @return رزرو ایجاد شده با کد وضعیت 201 (Created)
	 */
	@PostMapping // هندلر POST /api/reservations
	public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequest request) { // متد ایجاد رزرو
		Reservation saved = reservationService.createReservation(request); // فراخوانی منطق کسب‌وکار
		return ResponseEntity.created(URI.create("/api/reservations/" + saved.getId())).body(saved); // پاسخ 201 به همراه بدنه
	}

	/**
	 * دریافت اطلاعات یک رزرو بر اساس شناسه
	 * 
	 * @param id شناسه رزرو
	 * @return رزرو پیدا شده یا 404 اگر رزرو وجود نداشته باشد
	 */
	@GetMapping("/{id}") // هندلر GET برای دریافت رزرو خاص
	public ResponseEntity<Reservation> get(@PathVariable Long id) { // پارامتر شناسه از مسیر
		Optional<Reservation> res = reservationRepository.findById(id); // جستجوی رزرو
		return res.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); // بازگرداندن نتیجه یا 404
	}
}


