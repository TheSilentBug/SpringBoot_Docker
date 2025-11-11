package com.reyhan.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reyhan.hotel.entity.Hotel;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.HotelRepository;
import com.reyhan.hotel.repository.RoomRepository;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {
	private final HotelRepository hotelRepository;
	private final RoomRepository roomRepository;

	public HotelController(HotelRepository hotelRepository, RoomRepository roomRepository) {
		this.hotelRepository = hotelRepository;
		this.roomRepository = roomRepository;
	}

	@GetMapping
	public List<Hotel> listHotels() {
		return hotelRepository.findAll();
	}

	@GetMapping("/{id}/rooms")
	public ResponseEntity<List<Room>> listRooms(@PathVariable Long id,
	                                            @RequestParam(required = false) Boolean available,
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		if (Boolean.TRUE.equals(available) && from != null && to != null) {
			return ResponseEntity.ok(roomRepository.findAvailableRooms(id, from, to));
		}
		return hotelRepository.findById(id)
			.map(h -> ResponseEntity.ok(h.getRooms()))
			.orElse(ResponseEntity.notFound().build());
	}
}


