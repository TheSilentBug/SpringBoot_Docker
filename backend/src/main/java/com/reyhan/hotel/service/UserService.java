package com.reyhan.hotel.service;

import com.reyhan.hotel.entity.User;
import com.reyhan.hotel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * سرویس مدیریت کاربران
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ثبت‌نام کاربر جدید
     */
    @Transactional
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("نام کاربری قبلاً استفاده شده است");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("ایمیل قبلاً استفاده شده است");
        }
        return userRepository.save(user);
    }

    /**
     * دریافت کاربر بر اساس شناسه
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("کاربر یافت نشد"));
    }

    /**
     * دریافت کاربر بر اساس نام کاربری
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("کاربر یافت نشد"));
    }
}

