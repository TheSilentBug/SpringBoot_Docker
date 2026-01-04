package com.reyhan.hotel.service;

import com.reyhan.hotel.entity.Train;
import com.reyhan.hotel.entity.TrainReservation;
import com.reyhan.hotel.repository.TrainRepository;
import com.reyhan.hotel.repository.TrainReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * سرویس مدیریت قطار و رزرو قطار
 */
@Service
public class TrainService {
    private final TrainRepository trainRepository;
    private final TrainReservationRepository trainReservationRepository;

    public TrainService(TrainRepository trainRepository, TrainReservationRepository trainReservationRepository) {
        this.trainRepository = trainRepository;
        this.trainReservationRepository = trainReservationRepository;
    }

    /**
     * جستجوی قطار
     */
    public List<Train> searchTrains(String origin, String destination, LocalDate date, String trainClass) {
        if (trainClass != null && !trainClass.isEmpty()) {
            return trainRepository.findByTrainClassAndDepartureDateAndOriginAndDestination(trainClass, date, origin, destination);
        }
        return trainRepository.searchTrains(origin, destination, date);
    }

    /**
     * دریافت تمام قطارها
     */
    public List<Train> findAll() {
        return trainRepository.findAll();
    }

    /**
     * دریافت قطار بر اساس شناسه
     */
    public Train findById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("قطار یافت نشد"));
    }

    /**
     * ایجاد رزرو قطار
     */
    @Transactional
    public TrainReservation createReservation(Long trainId, String passengerName, String passengerEmail, Integer numberOfPassengers) {
        Train train = findById(trainId);

        if (train.getAvailableSeats() < numberOfPassengers) {
            throw new IllegalStateException("تعداد صندلی‌های خالی کافی نیست");
        }

        TrainReservation reservation = new TrainReservation();
        reservation.setTrain(train);
        reservation.setPassengerName(passengerName);
        reservation.setPassengerEmail(passengerEmail);
        reservation.setNumberOfPassengers(numberOfPassengers);
        reservation.setReservationDate(LocalDate.now());

        // کاهش تعداد صندلی‌های خالی
        train.setAvailableSeats(train.getAvailableSeats() - numberOfPassengers);
        trainRepository.save(train);

        return trainReservationRepository.save(reservation);
    }

    /**
     * دریافت رزرو بر اساس شناسه
     */
    public TrainReservation getReservation(Long id) {
        return trainReservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("رزرو یافت نشد"));
    }
}

