package com.reyhan.hotel.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * موجودیت رزرو - نمایانگر یک رزرواسیون اتاق
 * این کلاس اطلاعات مهمان و تاریخ رزرو را نگهداری می‌کند
 */
@Entity
public class Reservation {
	/**
	 * شناسه یکتای رزرو که به صورت خودکار تولید می‌شود
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * اتاقی که رزرو شده است
	 * از LAZY loading استفاده می‌کند تا فقط در صورت نیاز اتاق بارگذاری شود
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	/**
	 * نام و نام خانوادگی مهمان
	 */
	private String guestName;
	
	/**
	 * ایمیل مهمان برای ارتباط و تایید رزرو
	 */
	private String guestEmail;

	/**
	 * تاریخ شروع رزرو (اولین روز اقامت)
	 */
	private LocalDate startDate;
	
	/**
	 * تاریخ پایان رزرو (آخرین روز اقامت)
	 */
	private LocalDate endDate;

	public Long getId() {
		return id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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


