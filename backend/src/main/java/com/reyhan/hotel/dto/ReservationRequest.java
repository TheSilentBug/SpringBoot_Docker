package com.reyhan.hotel.dto; // پکیج DTO ها

import java.time.LocalDate; // نوع تاریخ برای شروع/پایان رزرو

import jakarta.validation.constraints.Email; // اعتبارسنجی فرمت ایمیل
import jakarta.validation.constraints.FutureOrPresent; // الزام تاریخ امروز یا آینده
import jakarta.validation.constraints.NotBlank; // جلوگیری از رشته خالی
import jakarta.validation.constraints.NotNull; // جلوگیری از null بودن مقدار

/**
 * کلاس DTO (Data Transfer Object) برای درخواست رزرو
 * این کلاس داده‌های ورودی از کلاینت را نگهداری می‌کند
 * و شامل اعتبارسنجی‌های لازم است
 */
public class ReservationRequest { // کلاس انتقال داده رزرو
	/**
	 * شناسه اتاقی که می‌خواهیم رزرو کنیم
	 * این فیلد اجباری است و نمی‌تواند null باشد
	 */
	@NotNull // فیلد اجباری
	private Long roomId; // شناسه اتاق انتخاب شده
	
	/**
	 * نام و نام خانوادگی مهمان
	 * این فیلد اجباری است و نمی‌تواند خالی باشد
	 */
	@NotBlank // باید مقدار غیر خالی داشته باشد
	private String guestName; // نام کامل مهمان
	
	/**
	 * ایمیل مهمان
	 * این فیلد اجباری است، باید فرمت ایمیل معتبر داشته باشد و نمی‌تواند خالی باشد
	 */
	@NotBlank // ایمیل خالی نباشد
	@Email // ایمیل باید معتبر باشد
	private String guestEmail; // آدرس ایمیل مهمان
	
	/**
	 * تاریخ شروع رزرو
	 * این فیلد اجباری است و باید امروز یا آینده باشد (نمی‌تواند در گذشته باشد)
	 */
	@NotNull // تاریخ شروع خالی نباشد
	@FutureOrPresent // تاریخ باید امروز یا آینده باشد
	private LocalDate startDate; // تاریخ ورود مهمان
	
	/**
	 * تاریخ پایان رزرو
	 * این فیلد اجباری است و باید امروز یا آینده باشد (نمی‌تواند در گذشته باشد)
	 */
	@NotNull // تاریخ پایان خالی نباشد
	@FutureOrPresent // تاریخ پایان باید در آینده باشد
	private LocalDate endDate; // تاریخ خروج مهمان

	public Long getRoomId() { // دریافت شناسه اتاق
		return roomId; // بازگرداندن مقدار
	}

	public void setRoomId(Long roomId) { // تنظیم شناسه اتاق
		this.roomId = roomId; // ذخیره مقدار جدید
	}

	public String getGuestName() { // دریافت نام مهمان
		return guestName; // بازگرداندن نام
	}

	public void setGuestName(String guestName) { // ثبت نام مهمان
		this.guestName = guestName; // ذخیره مقدار
	}

	public String getGuestEmail() { // دریافت ایمیل مهمان
		return guestEmail; // بازگرداندن ایمیل
	}

	public void setGuestEmail(String guestEmail) { // تنظیم ایمیل مهمان
		this.guestEmail = guestEmail; // ذخیره مقدار
	}

	public LocalDate getStartDate() { // دریافت تاریخ شروع
		return startDate; // بازگرداندن مقدار
	}

	public void setStartDate(LocalDate startDate) { // تنظیم تاریخ شروع
		this.startDate = startDate; // ذخیره مقدار
	}

	public LocalDate getEndDate() { // دریافت تاریخ پایان
		return endDate; // بازگرداندن مقدار
	}

	public void setEndDate(LocalDate endDate) { // تنظیم تاریخ پایان
		this.endDate = endDate; // ذخیره مقدار
	}
}


