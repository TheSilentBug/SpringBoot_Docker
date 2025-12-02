package com.reyhan.hotel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reyhan.hotel.dto.ReservationRequest;
import com.reyhan.hotel.entity.Reservation;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.ReservationRepository;
import com.reyhan.hotel.repository.RoomRepository;

/**
 * سرویس مدیریت رزرواسیون
 * این کلاس منطق کسب‌وکار مربوط به ایجاد و مدیریت رزروها را پیاده‌سازی می‌کند
 */
@Service
public class ReservationService {
	/**
	 * مخزن داده‌های رزرو برای دسترسی به رزروهای موجود
	 */
	private final ReservationRepository reservationRepository;
	
	/**
	 * مخزن داده‌های اتاق برای دسترسی به اطلاعات اتاق‌ها
	 */
	private final RoomRepository roomRepository;

	/**
	 * سازنده سرویس که وابستگی‌ها را تزریق می‌کند
	 * @param reservationRepository مخزن رزروها
	 * @param roomRepository مخزن اتاق‌ها
	 */
	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
	}

	/**
	 * ایجاد یک رزرو جدید با اعتبارسنجی و بررسی موجود بودن اتاق
	 * این متد تمام بررسی‌های لازم را انجام می‌دهد و سپس رزرو را ذخیره می‌کند
	 * 
	 * @param request درخواست رزرو شامل اطلاعات مهمان و تاریخ‌ها
	 * @return رزرو ایجاد شده
	 * @throws IllegalArgumentException اگر تاریخ‌ها نامعتبر باشند یا اتاق پیدا نشود
	 * @throws IllegalStateException اگر اتاق در بازه زمانی درخواستی موجود نباشد
	 */
	@Transactional
	public Reservation createReservation(ReservationRequest request) {
		// بررسی وجود تاریخ‌ها
		if (request.getStartDate() == null || request.getEndDate() == null) {
			throw new IllegalArgumentException("Dates are required");
		}
		// بررسی منطقی بودن تاریخ‌ها (تاریخ پایان باید بعد از تاریخ شروع باشد)
		if (request.getEndDate().isBefore(request.getStartDate())) {
			throw new IllegalArgumentException("End date must be after start date");
		}

		// پیدا کردن اتاق بر اساس شناسه
		Room room = roomRepository.findById(request.getRoomId())
			.orElseThrow(() -> new IllegalArgumentException("Room not found"));

		// بررسی موجود بودن اتاق در بازه زمانی درخواستی
		long overlapping = reservationRepository.countOverlapping(
			room.getId(), request.getStartDate(), request.getEndDate());
		if (overlapping > 0) {
			throw new IllegalStateException("Room is not available for selected dates");
		}

		// ایجاد رزرو جدید و پر کردن اطلاعات آن
		Reservation reservation = new Reservation();
		reservation.setRoom(room);
		reservation.setGuestName(request.getGuestName());
		reservation.setGuestEmail(request.getGuestEmail());
		reservation.setStartDate(request.getStartDate());
		reservation.setEndDate(request.getEndDate());
		
		// ذخیره رزرو در دیتابیس و برگرداندن آن
		return reservationRepository.save(reservation);
	}
}


