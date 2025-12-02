package com.reyhan.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * موجودیت اتاق - نمایانگر یک اتاق در هتل
 * هر اتاق متعلق به یک هتل است و می‌تواند رزروهای متعددی داشته باشد
 */
@Entity
public class Room {
	/**
	 * شناسه یکتای اتاق که به صورت خودکار تولید می‌شود
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * شماره اتاق (مثلاً "101" یا "205")
	 */
	private String number;
	
	/**
	 * نوع اتاق (مثلاً "Single", "Double", "Suite")
	 */
	private String type;
	
	/**
	 * ظرفیت اتاق (تعداد نفراتی که می‌توانند در اتاق اقامت کنند)
	 */
	private int capacity;
	
	/**
	 * قیمت هر شب اقامت در این اتاق (به تومان)
	 */
	private double pricePerNight;

	/**
	 * هتلی که این اتاق به آن تعلق دارد
	 * از LAZY loading استفاده می‌کند تا فقط در صورت نیاز هتل بارگذاری شود
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}


