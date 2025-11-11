package com.reyhan.hotel.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reyhan.hotel.dto.ReservationRequest;
import com.reyhan.hotel.entity.Reservation;
import com.reyhan.hotel.repository.ReservationRepository;
import com.reyhan.hotel.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class ReservationController {
	private final ReservationService reservationService;
	private final ReservationRepository reservationRepository;

	public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
		this.reservationService = reservationService;
		this.reservationRepository = reservationRepository;
	}

	@PostMapping
	public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequest request) {
		Reservation saved = reservationService.createReservation(request);
		return ResponseEntity.created(URI.create("/api/reservations/" + saved.getId())).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reservation> get(@PathVariable Long id) {
		Optional<Reservation> res = reservationRepository.findById(id);
		return res.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}


