package com.reyhan.hotel.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * موجودیت هتل - نمایانگر یک هتل در سیستم
 * این کلاس اطلاعات اصلی هتل را نگهداری می‌کند
 */
@Entity
public class Hotel {
	/**
	 * شناسه یکتای هتل که به صورت خودکار تولید می‌شود
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * نام هتل
	 */
	private String name;
	
	/**
	 * شهر محل قرارگیری هتل
	 */
	private String city;
	
	/**
	 * آدرس دقیق هتل
	 */
	private String address;

	/**
	 * لیست اتاق‌های این هتل
	 * با حذف هتل، تمام اتاق‌های آن نیز حذف می‌شوند (orphanRemoval = true)
	 */
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}


