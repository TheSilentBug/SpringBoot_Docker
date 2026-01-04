package com.reyhan.hotel.controller;

import com.reyhan.hotel.dto.FlightReservationRequest;
import com.reyhan.hotel.entity.Flight;
import com.reyhan.hotel.entity.FlightReservation;
import com.reyhan.hotel.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * کنترلر REST API برای مدیریت پرواز و رزرو پرواز
 */
@RestController
@RequestMapping("/api/flights")
@CrossOrigin
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * دریافت لیست تمام پروازها
     */
    @GetMapping
    public List<Flight> listFlights() {
        return flightService.findAll();
    }

    /**
     * جستجوی پرواز
     */
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            @RequestParam(required = false) String cabinClass) {
        return flightService.searchFlights(origin, destination, date, returnDate, cabinClass);
    }

    /**
     * دریافت پرواز بر اساس شناسه
     */
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable Long id) {
        try {
            Flight flight = flightService.findById(id);
            return ResponseEntity.ok(flight);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ایجاد رزرو پرواز
     */
    @PostMapping("/reservations")
    public ResponseEntity<FlightReservation> createReservation(@Valid @RequestBody FlightReservationRequest request) {
        try {
            FlightReservation reservation = flightService.createReservation(
                    request.getFlightId(),
                    request.getPassengerName(),
                    request.getPassengerEmail(),
                    request.getNumberOfPassengers()
            );
            return ResponseEntity.created(URI.create("/api/flights/reservations/" + reservation.getId())).body(reservation);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<FlightReservation> getReservation(@PathVariable Long id) {
        try {
            FlightReservation reservation = flightService.getReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

