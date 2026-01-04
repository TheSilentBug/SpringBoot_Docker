package com.reyhan.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * موجودیت کاربر - نمایانگر یک کاربر در سیستم
 * این کلاس اطلاعات کاربران ثبت‌نام شده را نگهداری می‌کند
 */
@Entity
public class User {
    /**
     * شناسه یکتای کاربر که به صورت خودکار تولید می‌شود
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * نام کاربر
     */
    private String firstName;

    /**
     * نام خانوادگی کاربر
     */
    private String lastName;

    /**
     * نام کاربری (منحصر به فرد)
     */
    private String username;

    /**
     * ایمیل کاربر (منحصر به فرد)
     */
    private String email;

    /**
     * رمز عبور (باید هش شده باشد)
     */
    private String password;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

