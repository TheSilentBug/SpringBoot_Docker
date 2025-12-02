package com.reyhan.hotel.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * کلاس DTO (Data Transfer Object) برای درخواست رزرو
 * این کلاس داده‌های ورودی از کلاینت را نگهداری می‌کند
 * و شامل اعتبارسنجی‌های لازم است
 */
public class ReservationRequest {
	/**
	 * شناسه اتاقی که می‌خواهیم رزرو کنیم
	 * این فیلد اجباری است و نمی‌تواند null باشد
	 */
	@NotNull
	private Long roomId;
	
	/**
	 * نام و نام خانوادگی مهمان
	 * این فیلد اجباری است و نمی‌تواند خالی باشد
	 */
	@NotBlank
	private String guestName;
	
	/**
	 * ایمیل مهمان
	 * این فیلد اجباری است، باید فرمت ایمیل معتبر داشته باشد و نمی‌تواند خالی باشد
	 */
	@NotBlank
	@Email
	private String guestEmail;
	
	/**
	 * تاریخ شروع رزرو
	 * این فیلد اجباری است و باید امروز یا آینده باشد (نمی‌تواند در گذشته باشد)
	 */
	@NotNull
	@FutureOrPresent
	private LocalDate startDate;
	
	/**
	 * تاریخ پایان رزرو
	 * این فیلد اجباری است و باید امروز یا آینده باشد (نمی‌تواند در گذشته باشد)
	 */
	@NotNull
	@FutureOrPresent
	private LocalDate endDate;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}


