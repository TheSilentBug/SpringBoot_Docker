package com.reyhan.hotel.service; // پکیج سرویس‌ها

import org.springframework.stereotype.Service; // علامت‌گذاری کلاس به عنوان سرویس Spring
import org.springframework.transaction.annotation.Transactional; // مدیریت تراکنش دیتابیس

import com.reyhan.hotel.dto.ReservationRequest; // DTO ورودی
import com.reyhan.hotel.entity.Reservation; // موجودیت رزرو
import com.reyhan.hotel.entity.Room; // موجودیت اتاق
import com.reyhan.hotel.repository.ReservationRepository; // مخزن رزرو
import com.reyhan.hotel.repository.RoomRepository; // مخزن اتاق

/**
 * سرویس مدیریت رزرواسیون
 * این کلاس منطق کسب‌وکار مربوط به ایجاد و مدیریت رزروها را پیاده‌سازی می‌کند
 */
@Service // ثبت سرویس در کانتکست
public class ReservationService { // کلاس منطق رزرو
	/**
	 * مخزن داده‌های رزرو برای دسترسی به رزروهای موجود
	 */
	private final ReservationRepository reservationRepository; // وابستگی مخزن رزرو
	
	/**
	 * مخزن داده‌های اتاق برای دسترسی به اطلاعات اتاق‌ها
	 */
	private final RoomRepository roomRepository; // وابستگی مخزن اتاق

	/**
	 * سازنده سرویس که وابستگی‌ها را تزریق می‌کند
	 * @param reservationRepository مخزن رزروها
	 * @param roomRepository مخزن اتاق‌ها
	 */
	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) { // سازنده با تزریق
		this.reservationRepository = reservationRepository; // مقداردهی مخزن رزرو
		this.roomRepository = roomRepository; // مقداردهی مخزن اتاق
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
	@Transactional // تضمین می‌کند عملیات در یک تراکنش انجام شود
	public Reservation createReservation(ReservationRequest request) { // متد ایجاد رزرو
		if (request.getStartDate() == null || request.getEndDate() == null) { // بررسی پر بودن تاریخ‌ها
			throw new IllegalArgumentException("Dates are required"); // خطا در صورت نبود تاریخ
		}
		if (request.getEndDate().isBefore(request.getStartDate())) { // بررسی ترتیب صحیح تاریخ‌ها
			throw new IllegalArgumentException("End date must be after start date"); // خطا برای بازه نامعتبر
		}

		Room room = roomRepository.findById(request.getRoomId()) // جستجوی اتاق
			.orElseThrow(() -> new IllegalArgumentException("Room not found")); // خطا اگر اتاق موجود نباشد

		long overlapping = reservationRepository.countOverlapping( // شمارش رزروهای متداخل
			room.getId(), request.getStartDate(), request.getEndDate());
		if (overlapping > 0) { // اگر اتاق رزرو قبلی دارد
			throw new IllegalStateException("Room is not available for selected dates"); // اعلام عدم موجودی
		}

		Reservation reservation = new Reservation(); // ایجاد موجودیت جدید
		reservation.setRoom(room); // نسبت دادن اتاق
		reservation.setGuestName(request.getGuestName()); // ذخیره نام مهمان
		reservation.setGuestEmail(request.getGuestEmail()); // ذخیره ایمیل
		reservation.setStartDate(request.getStartDate()); // ثبت تاریخ شروع
		reservation.setEndDate(request.getEndDate()); // ثبت تاریخ پایان
		
		return reservationRepository.save(reservation); // ذخیره و بازگرداندن رزرو
	}
}


