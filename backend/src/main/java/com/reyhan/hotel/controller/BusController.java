package com.reyhan.hotel.controller;

import com.reyhan.hotel.dto.BusReservationRequest;
import com.reyhan.hotel.entity.Bus;
import com.reyhan.hotel.entity.BusReservation;
import com.reyhan.hotel.service.BusService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * کنترلر REST API برای مدیریت اتوبوس و رزرو اتوبوس
 */
@RestController
@RequestMapping("/api/buses")
@CrossOrigin
public class BusController {
    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    /**
     * دریافت لیست تمام اتوبوس‌ها
     */
    @GetMapping
    public List<Bus> listBuses() {
        return busService.findAll();
    }

    /**
     * جستجوی اتوبوس
     */
    @GetMapping("/search")
    public List<Bus> searchBuses(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String busType) {
        return busService.searchBuses(origin, destination, date, busType);
    }

    /**
     * دریافت اتوبوس بر اساس شناسه
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        try {
            Bus bus = busService.findById(id);
            return ResponseEntity.ok(bus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ایجاد رزرو اتوبوس
     */
    @PostMapping("/reservations")
    public ResponseEntity<BusReservation> createReservation(@Valid @RequestBody BusReservationRequest request) {
        try {
            BusReservation reservation = busService.createReservation(
                    request.getBusId(),
                    request.getPassengerName(),
                    request.getPassengerEmail(),
                    request.getNumberOfPassengers()
            );
            return ResponseEntity.created(URI.create("/api/buses/reservations/" + reservation.getId())).body(reservation);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<BusReservation> getReservation(@PathVariable Long id) {
        try {
            BusReservation reservation = busService.getReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

