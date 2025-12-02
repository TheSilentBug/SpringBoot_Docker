package com.reyhan.hotel.entity; // پکیج موجودیت‌ها

import java.time.LocalDate; // نوع تاریخ برای بازه رزرو

import jakarta.persistence.Entity; // تعریف موجودیت JPA
import jakarta.persistence.FetchType; // نحوه بارگذاری رابطه
import jakarta.persistence.GeneratedValue; // تولید خودکار کلید
import jakarta.persistence.GenerationType; // استراتژی تولید کلید
import jakarta.persistence.Id; // مشخص کردن کلید اصلی
import jakarta.persistence.JoinColumn; // تعیین ستون کلید خارجی
import jakarta.persistence.ManyToOne; // رابطه با اتاق

/**
 * موجودیت رزرو - نمایانگر یک رزرواسیون اتاق
 * این کلاس اطلاعات مهمان و تاریخ رزرو را نگهداری می‌کند
 */
@Entity // ثبت کلاس به عنوان جدول
public class Reservation { // تعریف موجودیت رزرو
	/**
	 * شناسه یکتای رزرو که به صورت خودکار تولید می‌شود
	 */
	@Id // کلید اصلی
	@GeneratedValue(strategy = GenerationType.IDENTITY) // تولید توسط دیتابیس
	private Long id; // شناسه رزرو

	/**
	 * اتاقی که رزرو شده است
	 * از LAZY loading استفاده می‌کند تا فقط در صورت نیاز اتاق بارگذاری شود
	 */
	@ManyToOne(fetch = FetchType.LAZY) // هر رزرو مربوط به یک اتاق است
	@JoinColumn(name = "room_id") // کلید خارجی به جدول اتاق
	private Room room; // مرجع اتاق رزرو شده

	/**
	 * نام و نام خانوادگی مهمان
	 */
	private String guestName; // نام مهمان
	
	/**
	 * ایمیل مهمان برای ارتباط و تایید رزرو
	 */
	private String guestEmail; // ایمیل مهمان

	/**
	 * تاریخ شروع رزرو (اولین روز اقامت)
	 */
	private LocalDate startDate; // تاریخ شروع اقامت
	
	/**
	 * تاریخ پایان رزرو (آخرین روز اقامت)
	 */
	private LocalDate endDate; // تاریخ پایان اقامت

	public Long getId() { // دریافت شناسه
		return id; // بازگرداندن id
	}

	public Room getRoom() { // دریافت اتاق
		return room; // بازگرداندن مرجع اتاق
	}

	public void setRoom(Room room) { // تنظیم اتاق مرتبط
		this.room = room; // ذخیره مرجع اتاق
	}

	public String getGuestName() { // دریافت نام مهمان
		return guestName; // بازگرداندن نام
	}

	public void setGuestName(String guestName) { // تنظیم نام مهمان
		this.guestName = guestName; // ذخیره مقدار
	}

	public String getGuestEmail() { // دریافت ایمیل مهمان
		return guestEmail; // بازگرداندن ایمیل
	}

	public void setGuestEmail(String guestEmail) { // تنظیم ایمیل
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


