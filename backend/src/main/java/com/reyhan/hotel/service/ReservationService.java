package com.reyhan.hotel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reyhan.hotel.dto.ReservationRequest;
import com.reyhan.hotel.entity.Reservation;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.ReservationRepository;
import com.reyhan.hotel.repository.RoomRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;

	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
	}

	@Transactional
	public Reservation createReservation(ReservationRequest request) {
		if (request.getStartDate() == null || request.getEndDate() == null) {
			throw new IllegalArgumentException("Dates are required");
		}
		if (request.getEndDate().isBefore(request.getStartDate())) {
			throw new IllegalArgumentException("End date must be after start date");
		}

		Room room = roomRepository.findById(request.getRoomId())
			.orElseThrow(() -> new IllegalArgumentException("Room not found"));

		long overlapping = reservationRepository.countOverlapping(
			room.getId(), request.getStartDate(), request.getEndDate());
		if (overlapping > 0) {
			throw new IllegalStateException("Room is not available for selected dates");
		}

		Reservation reservation = new Reservation();
		reservation.setRoom(room);
		reservation.setGuestName(request.getGuestName());
		reservation.setGuestEmail(request.getGuestEmail());
		reservation.setStartDate(request.getStartDate());
		reservation.setEndDate(request.getEndDate());
		return reservationRepository.save(reservation);
	}
}


