package com.reyhan.hotel.service;

import com.reyhan.hotel.entity.Flight;
import com.reyhan.hotel.entity.FlightReservation;
import com.reyhan.hotel.repository.FlightRepository;
import com.reyhan.hotel.repository.FlightReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * سرویس مدیریت پرواز و رزرو پرواز
 */
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightReservationRepository flightReservationRepository;

    public FlightService(FlightRepository flightRepository, FlightReservationRepository flightReservationRepository) {
        this.flightRepository = flightRepository;
        this.flightReservationRepository = flightReservationRepository;
    }

    /**
     * جستجوی پرواز
     */
    public List<Flight> searchFlights(String origin, String destination, LocalDate date, LocalDate returnDate, String cabinClass) {
        if (returnDate != null) {
            return flightRepository.searchRoundTripFlights(origin, destination, date, returnDate);
        }
        List<Flight> flights = flightRepository.searchFlights(origin, destination, date);
        if (cabinClass != null && !cabinClass.isEmpty()) {
            return flights.stream()
                    .filter(f -> f.getCabinClass().equals(cabinClass))
                    .toList();
        }
        return flights;
    }

    /**
     * دریافت تمام پروازها
     */
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    /**
     * دریافت پرواز بر اساس شناسه
     */
    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("پرواز یافت نشد"));
    }

    /**
     * ایجاد رزرو پرواز
     */
    @Transactional
    public FlightReservation createReservation(Long flightId, String passengerName, String passengerEmail, Integer numberOfPassengers) {
        Flight flight = findById(flightId);

        if (flight.getAvailableSeats() < numberOfPassengers) {
            throw new IllegalStateException("تعداد صندلی‌های خالی کافی نیست");
        }

        FlightReservation reservation = new FlightReservation();
        reservation.setFlight(flight);
        reservation.setPassengerName(passengerName);
        reservation.setPassengerEmail(passengerEmail);
        reservation.setNumberOfPassengers(numberOfPassengers);
        reservation.setReservationDate(LocalDate.now());

        // کاهش تعداد صندلی‌های خالی
        flight.setAvailableSeats(flight.getAvailableSeats() - numberOfPassengers);
        flightRepository.save(flight);

        return flightReservationRepository.save(reservation);
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    public FlightReservation getReservation(Long id) {
        return flightReservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("رزرو یافت نشد"));
    }
}

