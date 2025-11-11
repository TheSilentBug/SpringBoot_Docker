package com.reyhan.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.reyhan.hotel.entity.Hotel;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.HotelRepository;

@Configuration
public class DataLoader {
	@Bean
	@Transactional
	CommandLineRunner loadDemoData(HotelRepository hotelRepository) {
		return args -> {
			if (hotelRepository.count() > 0) return;

			Hotel h1 = new Hotel();
			h1.setName("هتل آرامش");
			h1.setCity("تهران");
			h1.setAddress("خیابان ولیعصر");

			Room r11 = new Room();
			r11.setNumber("101");
			r11.setType("Single");
			r11.setCapacity(1);
			r11.setPricePerNight(1200000);
			r11.setHotel(h1);

			Room r12 = new Room();
			r12.setNumber("102");
			r12.setType("Double");
			r12.setCapacity(2);
			r12.setPricePerNight(1800000);
			r12.setHotel(h1);

			h1.getRooms().add(r11);
			h1.getRooms().add(r12);

			Hotel h2 = new Hotel();
			h2.setName("هتل ساحل");
			h2.setCity("کیش");
			h2.setAddress("بلوار دریا");

			Room r21 = new Room();
			r21.setNumber("201");
			r21.setType("Suite");
			r21.setCapacity(4);
			r21.setPricePerNight(3500000);
			r21.setHotel(h2);

			h2.getRooms().add(r21);

			hotelRepository.save(h1);
			hotelRepository.save(h2);
		};
	}
}


