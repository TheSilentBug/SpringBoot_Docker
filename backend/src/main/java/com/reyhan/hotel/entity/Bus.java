package com.reyhan.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * موجودیت اتوبوس - نمایانگر یک سرویس اتوبوس
 * این کلاس اطلاعات مسیر، زمان، قیمت و نوع اتوبوس را نگهداری می‌کند
 */
@Entity
public class Bus {
    /**
     * شناسه یکتای اتوبوس
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * شهر مبدأ
     */
    private String origin;

    /**
     * شهر مقصد
     */
    private String destination;

    /**
     * تاریخ حرکت
     */
    private LocalDate departureDate;

    /**
     * ساعت حرکت
     */
    private LocalTime departureTime;

    /**
     * ساعت رسیدن
     */
    private LocalTime arrivalTime;

    /**
     * نام شرکت اتوبوسرانی
     */
    private String company;

    /**
     * نوع اتوبوس (معمولی، VIP، خواب، توریستی)
     */
    private String busType;

    /**
     * تعداد صندلی‌های خالی
     */
    private Integer availableSeats;

    /**
     * قیمت بلیط (به تومان)
     */
    private Double price;

    /**
     * ترمینال مبدأ
     */
    private String originTerminal;

    /**
     * ترمینال مقصد
     */
    private String destinationTerminal;

    public Long getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOriginTerminal() {
        return originTerminal;
    }

    public void setOriginTerminal(String originTerminal) {
        this.originTerminal = originTerminal;
    }

    public String getDestinationTerminal() {
        return destinationTerminal;
    }

    public void setDestinationTerminal(String destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }
}

