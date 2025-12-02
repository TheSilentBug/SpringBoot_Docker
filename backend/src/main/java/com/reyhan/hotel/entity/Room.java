package com.reyhan.hotel.entity; // پکیج موجودیت‌ها

import jakarta.persistence.Entity; // مشخص می‌کند کلاس جدول دیتابیس است
import jakarta.persistence.FetchType; // تعیین نحوه بارگذاری رابطه
import jakarta.persistence.GeneratedValue; // تولید خودکار کلید
import jakarta.persistence.GenerationType; // استراتژی تولید کلید
import jakarta.persistence.Id; // فیلد کلید اصلی
import jakarta.persistence.JoinColumn; // مشخص کردن ستون کلید خارجی
import jakarta.persistence.ManyToOne; // رابطه چند به یک با هتل

/**
 * موجودیت اتاق - نمایانگر یک اتاق در هتل
 * هر اتاق متعلق به یک هتل است و می‌تواند رزروهای متعددی داشته باشد
 */
@Entity // تعریف موجودیت JPA
public class Room { // کلاس نماینده اتاق
	/**
	 * شناسه یکتای اتاق که به صورت خودکار تولید می‌شود
	 */
	@Id // کلید اصلی
	@GeneratedValue(strategy = GenerationType.IDENTITY) // تولید توسط دیتابیس
	private Long id; // شناسه یکتا اتاق

	/**
	 * شماره اتاق (مثلاً "101" یا "205")
	 */
	private String number; // شماره اتاق
	
	/**
	 * نوع اتاق (مثلاً "Single", "Double", "Suite")
	 */
	private String type; // نوع اتاق
	
	/**
	 * ظرفیت اتاق (تعداد نفراتی که می‌توانند در اتاق اقامت کنند)
	 */
	private int capacity; // ظرفیت نفرات
	
	/**
	 * قیمت هر شب اقامت در این اتاق (به تومان)
	 */
	private double pricePerNight; // هزینه هر شب

	/**
	 * هتلی که این اتاق به آن تعلق دارد
	 * از LAZY loading استفاده می‌کند تا فقط در صورت نیاز هتل بارگذاری شود
	 */
	@ManyToOne(fetch = FetchType.LAZY) // هر اتاق متعلق به یک هتل
	@JoinColumn(name = "hotel_id") // ستون foreign key در جدول اتاق
	private Hotel hotel; // مرجع به هتل مالک

	public Long getId() { // دریافت شناسه
		return id; // بازگرداندن id
	}

	public String getNumber() { // دریافت شماره
		return number; // بازگرداندن شماره
	}

	public void setNumber(String number) { // تنظیم شماره اتاق
		this.number = number; // ذخیره مقدار
	}

	public String getType() { // دریافت نوع اتاق
		return type; // بازگرداندن نوع
	}

	public void setType(String type) { // تنظیم نوع اتاق
		this.type = type; // ذخیره مقدار
	}

	public int getCapacity() { // دریافت ظرفیت
		return capacity; // بازگرداندن ظرفیت
	}

	public void setCapacity(int capacity) { // تنظیم ظرفیت
		this.capacity = capacity; // ذخیره مقدار
	}

	public double getPricePerNight() { // دریافت قیمت هر شب
		return pricePerNight; // بازگرداندن قیمت
	}

	public void setPricePerNight(double pricePerNight) { // تنظیم قیمت هر شب
		this.pricePerNight = pricePerNight; // ذخیره مقدار
	}

	public Hotel getHotel() { // دریافت هتل مالک
		return hotel; // بازگرداندن مرجع هتل
	}

	public void setHotel(Hotel hotel) { // تنظیم هتل مالک
		this.hotel = hotel; // ذخیره مرجع
	}
}


