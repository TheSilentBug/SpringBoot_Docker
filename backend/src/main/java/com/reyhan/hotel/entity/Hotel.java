package com.reyhan.hotel.entity; // پکیج موجودیت‌ها

import java.util.ArrayList; // پیاده‌سازی لیست برای نگه‌داشتن اتاق‌ها
import java.util.List; // رابط لیست برای مجموعه اتاق‌ها

import jakarta.persistence.CascadeType; // کنترل عملیات cascade بین هتل و اتاق
import jakarta.persistence.Entity; // مشخص می‌کند کلاس یک موجودیت JPA است
import jakarta.persistence.GeneratedValue; // تولید خودکار کلید اصلی
import jakarta.persistence.GenerationType; // استراتژی تولید شناسه
import jakarta.persistence.Id; // تعیین کلید اصلی
import jakarta.persistence.OneToMany; // رابطه یک‌به‌چند با اتاق‌ها

/**
 * موجودیت هتل - نمایانگر یک هتل در سیستم
 * این کلاس اطلاعات اصلی هتل را نگهداری می‌کند
 */
@Entity // ثبت کلاس به عنوان جدول دیتابیس
public class Hotel { // تعریف موجودیت هتل
	/**
	 * شناسه یکتای هتل که به صورت خودکار تولید می‌شود
	 */
	@Id // مشخص کردن فیلد به عنوان کلید اصلی
	@GeneratedValue(strategy = GenerationType.IDENTITY) // سپردن تولید شناسه به دیتابیس
	private Long id; // شناسه یکتا هتل

	/**
	 * نام هتل
	 */
	private String name; // نام هتل
	
	/**
	 * شهر محل قرارگیری هتل
	 */
	private String city; // شهر هتل
	
	/**
	 * آدرس دقیق هتل
	 */
	private String address; // آدرس کامل هتل

	/**
	 * لیست اتاق‌های این هتل
	 * با حذف هتل، تمام اتاق‌های آن نیز حذف می‌شوند (orphanRemoval = true)
	 */
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true) // رابطه با اتاق‌ها
	private List<Room> rooms = new ArrayList<>(); // لیست اتاق‌های متعلق به هتل

	public Long getId() { // دریافت شناسه
		return id; // بازگرداندن id
	}

	public String getName() { // دریافت نام
		return name; // بازگرداندن نام
	}

	public void setName(String name) { // تنظیم نام
		this.name = name; // ذخیره مقدار
	}

	public String getCity() { // دریافت شهر
		return city; // بازگرداندن شهر
	}

	public void setCity(String city) { // تنظیم شهر
		this.city = city; // ذخیره مقدار
	}

	public String getAddress() { // دریافت آدرس
		return address; // بازگرداندن آدرس
	}

	public void setAddress(String address) { // تنظیم آدرس
		this.address = address; // ذخیره مقدار
	}

	public List<Room> getRooms() { // دریافت لیست اتاق‌ها
		return rooms; // بازگرداندن لیست
	}

	public void setRooms(List<Room> rooms) { // جایگزینی لیست اتاق‌ها
		this.rooms = rooms; // ذخیره لیست جدید
	}
}


