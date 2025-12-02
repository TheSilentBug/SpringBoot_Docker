package com.reyhan.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import com.reyhan.hotel.entity.Hotel;
import com.reyhan.hotel.entity.Reservation;
import com.reyhan.hotel.entity.Room;
import com.reyhan.hotel.repository.HotelRepository;
import com.reyhan.hotel.repository.ReservationRepository;

/**
 * کلاس پیکربندی برای بارگذاری داده‌های اولیه در دیتابیس
 * این کلاس در زمان راه‌اندازی برنامه، داده‌های نمونه را در دیتابیس قرار می‌دهد
 */
@Configuration
public class DataLoader {
	/**
	 * متد بارگذاری داده‌های نمونه
	 * این متد در صورت خالی بودن دیتابیس، هتل‌ها، اتاق‌ها و رزروهای نمونه را ایجاد می‌کند
	 * 
	 * @param hotelRepository مخزن هتل‌ها برای ذخیره داده‌ها
	 * @param reservationRepository مخزن رزروها برای ذخیره رزروهای نمونه
	 * @return CommandLineRunner که در زمان راه‌اندازی اجرا می‌شود
	 */
	@Bean
	@Transactional
	CommandLineRunner loadDemoData(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
		return args -> {
			// اگر دیتابیس قبلاً پر شده باشد، از بارگذاری مجدد جلوگیری می‌کند
			if (hotelRepository.count() > 0) return;

			// ایجاد هتل اول: هتل آرامش در تهران
			Hotel h1 = new Hotel();
			h1.setName("هتل آرامش");
			h1.setCity("تهران");
			h1.setAddress("خیابان ولیعصر، پلاک 123");

			// ایجاد اتاق 101 - تک نفره
			Room r11 = new Room();
			r11.setNumber("101");
			r11.setType("Single");
			r11.setCapacity(1);
			r11.setPricePerNight(1200000);
			r11.setHotel(h1);

			// ایجاد اتاق 102 - دو نفره
			Room r12 = new Room();
			r12.setNumber("102");
			r12.setType("Double");
			r12.setCapacity(2);
			r12.setPricePerNight(1800000);
			r12.setHotel(h1);

			// ایجاد اتاق 103 - دو نفره
			Room r13 = new Room();
			r13.setNumber("103");
			r13.setType("Double");
			r13.setCapacity(2);
			r13.setPricePerNight(1900000);
			r13.setHotel(h1);

			// ایجاد اتاق 201 - سوئیت
			Room r14 = new Room();
			r14.setNumber("201");
			r14.setType("Suite");
			r14.setCapacity(4);
			r14.setPricePerNight(3200000);
			r14.setHotel(h1);

			// اضافه کردن اتاق‌ها به هتل
			h1.getRooms().add(r11);
			h1.getRooms().add(r12);
			h1.getRooms().add(r13);
			h1.getRooms().add(r14);

			// ایجاد هتل دوم: هتل ساحل در کیش
			Hotel h2 = new Hotel();
			h2.setName("هتل ساحل");
			h2.setCity("کیش");
			h2.setAddress("بلوار دریا، خیابان ساحل");

			// ایجاد اتاق 201 - سوئیت
			Room r21 = new Room();
			r21.setNumber("201");
			r21.setType("Suite");
			r21.setCapacity(4);
			r21.setPricePerNight(3500000);
			r21.setHotel(h2);

			// ایجاد اتاق 202 - دو نفره
			Room r22 = new Room();
			r22.setNumber("202");
			r22.setType("Double");
			r22.setCapacity(2);
			r22.setPricePerNight(2200000);
			r22.setHotel(h2);

			// ایجاد اتاق 301 - سوئیت لوکس
			Room r23 = new Room();
			r23.setNumber("301");
			r23.setType("Deluxe Suite");
			r23.setCapacity(6);
			r23.setPricePerNight(4500000);
			r23.setHotel(h2);

			// اضافه کردن اتاق‌ها به هتل
			h2.getRooms().add(r21);
			h2.getRooms().add(r22);
			h2.getRooms().add(r23);

			// ایجاد هتل سوم: هتل پارسیان در اصفهان
			Hotel h3 = new Hotel();
			h3.setName("هتل پارسیان");
			h3.setCity("اصفهان");
			h3.setAddress("خیابان چهارباغ، میدان نقش جهان");

			// ایجاد اتاق‌های هتل پارسیان
			Room r31 = new Room();
			r31.setNumber("101");
			r31.setType("Single");
			r31.setCapacity(1);
			r31.setPricePerNight(1000000);
			r31.setHotel(h3);

			Room r32 = new Room();
			r32.setNumber("102");
			r32.setType("Double");
			r32.setCapacity(2);
			r32.setPricePerNight(1600000);
			r32.setHotel(h3);

			Room r33 = new Room();
			r33.setNumber("201");
			r33.setType("Suite");
			r33.setCapacity(4);
			r33.setPricePerNight(2800000);
			r33.setHotel(h3);

			Room r34 = new Room();
			r34.setNumber("202");
			r34.setType("Double");
			r34.setCapacity(2);
			r34.setPricePerNight(1700000);
			r34.setHotel(h3);

			// اضافه کردن اتاق‌ها به هتل
			h3.getRooms().add(r31);
			h3.getRooms().add(r32);
			h3.getRooms().add(r33);
			h3.getRooms().add(r34);

			// ایجاد هتل چهارم: هتل الماس در شیراز
			Hotel h4 = new Hotel();
			h4.setName("هتل الماس");
			h4.setCity("شیراز");
			h4.setAddress("بلوار زند، خیابان حافظ");

			// ایجاد اتاق‌های هتل الماس
			Room r41 = new Room();
			r41.setNumber("101");
			r41.setType("Single");
			r41.setCapacity(1);
			r41.setPricePerNight(950000);
			r41.setHotel(h4);

			Room r42 = new Room();
			r42.setNumber("102");
			r42.setType("Double");
			r42.setCapacity(2);
			r42.setPricePerNight(1500000);
			r42.setHotel(h4);

			Room r43 = new Room();
			r43.setNumber("201");
			r43.setType("Suite");
			r43.setCapacity(4);
			r43.setPricePerNight(2700000);
			r43.setHotel(h4);

			Room r44 = new Room();
			r44.setNumber("301");
			r44.setType("Deluxe Suite");
			r44.setCapacity(5);
			r44.setPricePerNight(3800000);
			r44.setHotel(h4);

			// اضافه کردن اتاق‌ها به هتل
			h4.getRooms().add(r41);
			h4.getRooms().add(r42);
			h4.getRooms().add(r43);
			h4.getRooms().add(r44);

			// ذخیره تمام هتل‌ها در دیتابیس
			hotelRepository.save(h1);
			hotelRepository.save(h2);
			hotelRepository.save(h3);
			hotelRepository.save(h4);

			// ایجاد هتل پنجم: هتل هما در مشهد
			Hotel h5 = new Hotel();
			h5.setName("هتل هما");
			h5.setCity("مشهد");
			h5.setAddress("بلوار وکیل‌آباد، نزدیک حرم مطهر");

			// ایجاد اتاق‌های هتل هما
			Room r51 = new Room();
			r51.setNumber("101");
			r51.setType("Single");
			r51.setCapacity(1);
			r51.setPricePerNight(1100000);
			r51.setHotel(h5);

			Room r52 = new Room();
			r52.setNumber("102");
			r52.setType("Double");
			r52.setCapacity(2);
			r52.setPricePerNight(1700000);
			r52.setHotel(h5);

			Room r53 = new Room();
			r53.setNumber("201");
			r53.setType("Double");
			r53.setCapacity(2);
			r53.setPricePerNight(1850000);
			r53.setHotel(h5);

			Room r54 = new Room();
			r54.setNumber("202");
			r54.setType("Suite");
			r54.setCapacity(4);
			r54.setPricePerNight(3000000);
			r54.setHotel(h5);

			Room r55 = new Room();
			r55.setNumber("301");
			r55.setType("Deluxe Suite");
			r55.setCapacity(6);
			r55.setPricePerNight(4200000);
			r55.setHotel(h5);

			h5.getRooms().add(r51);
			h5.getRooms().add(r52);
			h5.getRooms().add(r53);
			h5.getRooms().add(r54);
			h5.getRooms().add(r55);

			// ایجاد هتل ششم: هتل بادگیر در یزد
			Hotel h6 = new Hotel();
			h6.setName("هتل بادگیر");
			h6.setCity("یزد");
			h6.setAddress("خیابان قیام، میدان امیرچخماق");

			// ایجاد اتاق‌های هتل بادگیر
			Room r61 = new Room();
			r61.setNumber("101");
			r61.setType("Single");
			r61.setCapacity(1);
			r61.setPricePerNight(900000);
			r61.setHotel(h6);

			Room r62 = new Room();
			r62.setNumber("102");
			r62.setType("Double");
			r62.setCapacity(2);
			r62.setPricePerNight(1400000);
			r62.setHotel(h6);

			Room r63 = new Room();
			r63.setNumber("201");
			r63.setType("Double");
			r63.setCapacity(2);
			r63.setPricePerNight(1550000);
			r63.setHotel(h6);

			Room r64 = new Room();
			r64.setNumber("202");
			r64.setType("Suite");
			r64.setCapacity(4);
			r64.setPricePerNight(2600000);
			r64.setHotel(h6);

			h6.getRooms().add(r61);
			h6.getRooms().add(r62);
			h6.getRooms().add(r63);
			h6.getRooms().add(r64);

			// ایجاد هتل هفتم: هتل لاله در تبریز
			Hotel h7 = new Hotel();
			h7.setName("هتل لاله");
			h7.setCity("تبریز");
			h7.setAddress("خیابان امام خمینی، میدان ساعت");

			// ایجاد اتاق‌های هتل لاله
			Room r71 = new Room();
			r71.setNumber("101");
			r71.setType("Single");
			r71.setCapacity(1);
			r71.setPricePerNight(1050000);
			r71.setHotel(h7);

			Room r72 = new Room();
			r72.setNumber("102");
			r72.setType("Double");
			r72.setCapacity(2);
			r72.setPricePerNight(1650000);
			r72.setHotel(h7);

			Room r73 = new Room();
			r73.setNumber("201");
			r73.setType("Double");
			r73.setCapacity(2);
			r73.setPricePerNight(1750000);
			r73.setHotel(h7);

			Room r74 = new Room();
			r74.setNumber("202");
			r74.setType("Suite");
			r74.setCapacity(4);
			r74.setPricePerNight(2900000);
			r74.setHotel(h7);

			Room r75 = new Room();
			r75.setNumber("301");
			r75.setType("Deluxe Suite");
			r75.setCapacity(5);
			r75.setPricePerNight(4000000);
			r75.setHotel(h7);

			h7.getRooms().add(r71);
			h7.getRooms().add(r72);
			h7.getRooms().add(r73);
			h7.getRooms().add(r74);
			h7.getRooms().add(r75);

			// اضافه کردن اتاق‌های بیشتر به هتل‌های موجود
			// هتل آرامش - اتاق‌های بیشتر
			Room r15 = new Room();
			r15.setNumber("202");
			r15.setType("Double");
			r15.setCapacity(2);
			r15.setPricePerNight(1950000);
			r15.setHotel(h1);

			Room r16 = new Room();
			r16.setNumber("301");
			r16.setType("Suite");
			r16.setCapacity(4);
			r16.setPricePerNight(3300000);
			r16.setHotel(h1);

			h1.getRooms().add(r15);
			h1.getRooms().add(r16);

			// هتل ساحل - اتاق‌های بیشتر
			Room r24 = new Room();
			r24.setNumber("101");
			r24.setType("Single");
			r24.setCapacity(1);
			r24.setPricePerNight(1300000);
			r24.setHotel(h2);

			Room r25 = new Room();
			r25.setNumber("102");
			r25.setType("Double");
			r25.setCapacity(2);
			r25.setPricePerNight(2100000);
			r25.setHotel(h2);

			h2.getRooms().add(r24);
			h2.getRooms().add(r25);

			// ذخیره تمام هتل‌های جدید
			hotelRepository.save(h5);
			hotelRepository.save(h6);
			hotelRepository.save(h7);
			hotelRepository.save(h1); // ذخیره مجدد برای اتاق‌های جدید

			// ایجاد رزروهای نمونه برای نمایش در سیستم
			// رزرو نمونه 1: در هتل آرامش
			Reservation res1 = new Reservation();
			res1.setRoom(r11); // اتاق 101 هتل آرامش
			res1.setGuestName("علی احمدی");
			res1.setGuestEmail("ali.ahmadi@example.com");
			res1.setStartDate(LocalDate.now().plusDays(5)); // 5 روز دیگر
			res1.setEndDate(LocalDate.now().plusDays(7)); // 7 روز دیگر
			reservationRepository.save(res1);

			// رزرو نمونه 2: در هتل ساحل
			Reservation res2 = new Reservation();
			res2.setRoom(r21); // اتاق 201 هتل ساحل
			res2.setGuestName("فاطمه رضایی");
			res2.setGuestEmail("fateme.rezaei@example.com");
			res2.setStartDate(LocalDate.now().plusDays(10)); // 10 روز دیگر
			res2.setEndDate(LocalDate.now().plusDays(12)); // 12 روز دیگر
			reservationRepository.save(res2);

			// رزرو نمونه 3: در هتل پارسیان
			Reservation res3 = new Reservation();
			res3.setRoom(r32); // اتاق 102 هتل پارسیان
			res3.setGuestName("محمد کریمی");
			res3.setGuestEmail("mohammad.karimi@example.com");
			res3.setStartDate(LocalDate.now().plusDays(15)); // 15 روز دیگر
			res3.setEndDate(LocalDate.now().plusDays(18)); // 18 روز دیگر
			reservationRepository.save(res3);

			// رزرو نمونه 4: در هتل الماس
			Reservation res4 = new Reservation();
			res4.setRoom(r42); // اتاق 102 هتل الماس
			res4.setGuestName("زهرا نوری");
			res4.setGuestEmail("zahra.noori@example.com");
			res4.setStartDate(LocalDate.now().plusDays(20)); // 20 روز دیگر
			res4.setEndDate(LocalDate.now().plusDays(22)); // 22 روز دیگر
			reservationRepository.save(res4);

			// رزرو نمونه 5: در هتل هما
			Reservation res5 = new Reservation();
			res5.setRoom(r52); // اتاق 102 هتل هما
			res5.setGuestName("حسین موسوی");
			res5.setGuestEmail("hossein.mousavi@example.com");
			res5.setStartDate(LocalDate.now().plusDays(25)); // 25 روز دیگر
			res5.setEndDate(LocalDate.now().plusDays(27)); // 27 روز دیگر
			reservationRepository.save(res5);
		};
	}
}


