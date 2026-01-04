package com.reyhan.hotel.controller;

import com.reyhan.hotel.dto.TrainReservationRequest;
import com.reyhan.hotel.entity.Train;
import com.reyhan.hotel.entity.TrainReservation;
import com.reyhan.hotel.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * کنترلر REST API برای مدیریت قطار و رزرو قطار
 */
@RestController
@RequestMapping("/api/trains")
@CrossOrigin
public class TrainController {
    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    /**
     * دریافت لیست تمام قطارها
     */
    @GetMapping
    public List<Train> listTrains() {
        return trainService.findAll();
    }

    /**
     * جستجوی قطار
     */
    @GetMapping("/search")
    public List<Train> searchTrains(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String trainClass) {
        return trainService.searchTrains(origin, destination, date, trainClass);
    }

    /**
     * دریافت قطار بر اساس شناسه
     */
    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrain(@PathVariable Long id) {
        try {
            Train train = trainService.findById(id);
            return ResponseEntity.ok(train);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ایجاد رزرو قطار
     */
    @PostMapping("/reservations")
    public ResponseEntity<TrainReservation> createReservation(@Valid @RequestBody TrainReservationRequest request) {
        try {
            TrainReservation reservation = trainService.createReservation(
                    request.getTrainId(),
                    request.getPassengerName(),
                    request.getPassengerEmail(),
                    request.getNumberOfPassengers()
            );
            return ResponseEntity.created(URI.create("/api/trains/reservations/" + reservation.getId())).body(reservation);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<TrainReservation> getReservation(@PathVariable Long id) {
        try {
            TrainReservation reservation = trainService.getReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

