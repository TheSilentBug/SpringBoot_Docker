package com.reyhan.hotel.repository;

import com.reyhan.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * رابط دسترسی به داده‌های کاربر
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * پیدا کردن کاربر بر اساس نام کاربری
     */
    Optional<User> findByUsername(String username);

    /**
     * پیدا کردن کاربر بر اساس ایمیل
     */
    Optional<User> findByEmail(String email);

    /**
     * بررسی وجود کاربر با نام کاربری
     */
    boolean existsByUsername(String username);

    /**
     * بررسی وجود کاربر با ایمیل
     */
    boolean existsByEmail(String email);
}

