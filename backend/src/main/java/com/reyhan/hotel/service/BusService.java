package com.reyhan.hotel.service;

import com.reyhan.hotel.entity.Bus;
import com.reyhan.hotel.entity.BusReservation;
import com.reyhan.hotel.repository.BusRepository;
import com.reyhan.hotel.repository.BusReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * سرویس مدیریت اتوبوس و رزرو اتوبوس
 */
@Service
public class BusService {
    private final BusRepository busRepository;
    private final BusReservationRepository busReservationRepository;

    public BusService(BusRepository busRepository, BusReservationRepository busReservationRepository) {
        this.busRepository = busRepository;
        this.busReservationRepository = busReservationRepository;
    }

    /**
     * جستجوی اتوبوس
     */
    public List<Bus> searchBuses(String origin, String destination, LocalDate date, String busType) {
        if (busType != null && !busType.isEmpty()) {
            return busRepository.findByBusTypeAndDepartureDateAndOriginAndDestination(busType, date, origin, destination);
        }
        return busRepository.searchBuses(origin, destination, date);
    }

    /**
     * دریافت تمام اتوبوس‌ها
     */
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    /**
     * دریافت اتوبوس بر اساس شناسه
     */
    public Bus findById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("اتوبوس یافت نشد"));
    }

    /**
     * ایجاد رزرو اتوبوس
     */
    @Transactional
    public BusReservation createReservation(Long busId, String passengerName, String passengerEmail, Integer numberOfPassengers) {
        Bus bus = findById(busId);

        if (bus.getAvailableSeats() < numberOfPassengers) {
            throw new IllegalStateException("تعداد صندلی‌های خالی کافی نیست");
        }

        BusReservation reservation = new BusReservation();
        reservation.setBus(bus);
        reservation.setPassengerName(passengerName);
        reservation.setPassengerEmail(passengerEmail);
        reservation.setNumberOfPassengers(numberOfPassengers);
        reservation.setReservationDate(LocalDate.now());

        // کاهش تعداد صندلی‌های خالی
        bus.setAvailableSeats(bus.getAvailableSeats() - numberOfPassengers);
        busRepository.save(bus);

        return busReservationRepository.save(reservation);
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    public BusReservation getReservation(Long id) {
        return busReservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("رزرو یافت نشد"));
    }
}

