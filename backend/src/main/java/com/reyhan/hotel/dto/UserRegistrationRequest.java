package com.reyhan.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO برای درخواست ثبت‌نام کاربر
 */
public class UserRegistrationRequest {
    @NotBlank(message = "نام الزامی است")
    private String firstName;

    @NotBlank(message = "نام خانوادگی الزامی است")
    private String lastName;

    @NotBlank(message = "نام کاربری الزامی است")
    @Size(min = 3, message = "نام کاربری باید حداقل 3 کاراکتر باشد")
    private String username;

    @NotBlank(message = "ایمیل الزامی است")
    @Email(message = "ایمیل معتبر نیست")
    private String email;

    @NotBlank(message = "رمز عبور الزامی است")
    @Size(min = 6, message = "رمز عبور باید حداقل 6 کاراکتر باشد")
    private String password;

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

