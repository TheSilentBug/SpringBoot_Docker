package com.reyhan.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO برای درخواست رزرو قطار
 */
public class TrainReservationRequest {
    @NotNull(message = "شناسه قطار الزامی است")
    private Long trainId;

    @NotBlank(message = "نام مسافر الزامی است")
    private String passengerName;

    @NotBlank(message = "ایمیل الزامی است")
    @Email(message = "ایمیل معتبر نیست")
    private String passengerEmail;

    @NotNull(message = "تعداد مسافران الزامی است")
    @Min(value = 1, message = "تعداد مسافران باید حداقل 1 باشد")
    private Integer numberOfPassengers;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}

