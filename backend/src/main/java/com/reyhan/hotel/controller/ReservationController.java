package com.reyhan.hotel.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reyhan.hotel.dto.ReservationRequest;
import com.reyhan.hotel.entity.Reservation;
import com.reyhan.hotel.repository.ReservationRepository;
import com.reyhan.hotel.service.ReservationService;

import jakarta.validation.Valid;

/**
 * کنترلر REST API برای مدیریت رزروها
 * این کلاس endpointهای مربوط به ایجاد و دریافت رزروها را فراهم می‌کند
 */
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class ReservationController {
	/**
	 * سرویس رزرواسیون برای انجام عملیات کسب‌وکار
	 */
	private final ReservationService reservationService;
	
	/**
	 * مخزن داده‌های رزرو برای دسترسی مستقیم به رزروها
	 */
	private final ReservationRepository reservationRepository;

	/**
	 * سازنده کنترلر که وابستگی‌ها را تزریق می‌کند
	 * @param reservationService سرویس رزرواسیون
	 * @param reservationRepository مخزن رزروها
	 */
	public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
		this.reservationService = reservationService;
		this.reservationRepository = reservationRepository;
	}

	/**
	 * ایجاد یک رزرو جدید
	 * این endpoint درخواست رزرو را دریافت می‌کند، اعتبارسنجی می‌کند و رزرو را ایجاد می‌کند
	 * 
	 * @param request درخواست رزرو شامل اطلاعات مهمان و تاریخ‌ها
	 * @return رزرو ایجاد شده با کد وضعیت 201 (Created)
	 */
	@PostMapping
	public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequest request) {
		Reservation saved = reservationService.createReservation(request);
		return ResponseEntity.created(URI.create("/api/reservations/" + saved.getId())).body(saved);
	}

	/**
	 * دریافت اطلاعات یک رزرو بر اساس شناسه
	 * 
	 * @param id شناسه رزرو
	 * @return رزرو پیدا شده یا 404 اگر رزرو وجود نداشته باشد
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> get(@PathVariable Long id) {
		Optional<Reservation> res = reservationRepository.findById(id);
		return res.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}


