package com.reyhan.hotel.controller;

import com.reyhan.hotel.dto.UserRegistrationRequest;
import com.reyhan.hotel.entity.User;
import com.reyhan.hotel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * کنترلر REST API برای مدیریت کاربران
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ثبت‌نام کاربر جدید
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // در حالت واقعی باید هش شود

        User saved = userService.register(user);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }
}

