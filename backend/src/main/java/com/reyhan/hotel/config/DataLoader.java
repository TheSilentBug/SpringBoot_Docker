package com.reyhan.hotel.config; // پکیج تنظیمات و داده‌های اولیه

import org.springframework.boot.CommandLineRunner; // اینترفیس برای اجرای کد هنگام استارت
import org.springframework.context.annotation.Bean; // برای تعریف Bean
import org.springframework.context.annotation.Configuration; // مشخص کردن کلاس پیکربندی
import org.springframework.transaction.annotation.Transactional; // تضمین تراکنش هنگام درج داده

import java.time.LocalDate; // نوع تاریخ برای رزروها

import com.reyhan.hotel.entity.Hotel; // موجودیت هتل
import com.reyhan.hotel.entity.Reservation; // موجودیت رزرو
import com.reyhan.hotel.entity.Room; // موجودیت اتاق
import com.reyhan.hotel.repository.HotelRepository; // مخزن هتل
import com.reyhan.hotel.repository.ReservationRepository; // مخزن رزرو

/**
 * کلاس پیکربندی برای بارگذاری داده‌های اولیه در دیتابیس
 * این کلاس در زمان راه‌اندازی برنامه، داده‌های نمونه را در دیتابیس قرار می‌دهد
 */
@Configuration // ثبت کلاس به عنوان پیکربندی Spring
public class DataLoader { // کلاس مسئول درج داده‌های نمونه
	/**
	 * متد بارگذاری داده‌های نمونه
	 * این متد در صورت خالی بودن دیتابیس، هتل‌ها، اتاق‌ها و رزروهای نمونه را ایجاد می‌کند
	 * 
	 * @param hotelRepository مخزن هتل‌ها برای ذخیره داده‌ها
	 * @param reservationRepository مخزن رزروها برای ذخیره رزروهای نمونه
	 * @return CommandLineRunner که در زمان راه‌اندازی اجرا می‌شود
	 */
	@Bean // تعریف Bean برای اجرا پس از راه‌اندازی
	@Transactional // تضمین می‌کند کل فرآیند درج در یک تراکنش باشد
	CommandLineRunner loadDemoData(HotelRepository hotelRepository, ReservationRepository reservationRepository) { // تزریق مخازن لازم
		return args -> { // پیاده‌سازی CommandLineRunner به‌صورت Lambda
			if (hotelRepository.count() > 0) return; // اگر قبلاً داده‌ای وجود دارد، از درج مجدد خودداری می‌کند

			// ایجاد هتل اول: هتل آرامش در تهران
			Hotel h1 = new Hotel(); // ایجاد نمونه هتل اول
			h1.setName("هتل آرامش"); // تعیین نام هتل
			h1.setCity("تهران"); // تعیین شهر هتل
			h1.setAddress("خیابان ولیعصر، پلاک 123"); // آدرس هتل

			// ایجاد اتاق 101 - تک نفره
			Room r11 = new Room(); // ساخت اتاق 101
			r11.setNumber("101"); // شماره اتاق
			r11.setType("Single"); // نوع اتاق
			r11.setCapacity(1); // ظرفیت یک نفره
			r11.setPricePerNight(1200000); // قیمت هر شب
			r11.setHotel(h1); // انتساب اتاق به هتل اول

			// ایجاد اتاق 102 - دو نفره
			Room r12 = new Room(); // ساخت اتاق 102
			r12.setNumber("102"); // تنظیم شماره
			r12.setType("Double"); // نوع دو نفره
			r12.setCapacity(2); // ظرفیت دو نفر
			r12.setPricePerNight(1800000); // قیمت هر شب
			r12.setHotel(h1); // اتصال به هتل آرامش

			// ایجاد اتاق 103 - دو نفره
			Room r13 = new Room(); // ساخت اتاق 103
			r13.setNumber("103"); // شماره اتاق
			r13.setType("Double"); // نوع دو نفره
			r13.setCapacity(2); // ظرفیت دو نفر
			r13.setPricePerNight(1900000); // قیمت
			r13.setHotel(h1); // نسبت به هتل اول

			// ایجاد اتاق 201 - سوئیت
			Room r14 = new Room(); // ساخت اتاق 201
			r14.setNumber("201"); // شماره اتاق
			r14.setType("Suite"); // نوع سوئیت
			r14.setCapacity(4); // ظرفیت چهار نفره
			r14.setPricePerNight(3200000); // قیمت
			r14.setHotel(h1); // اتصال به هتل اول

			// اضافه کردن اتاق‌ها به هتل
			h1.getRooms().add(r11); // افزودن اتاق 101 به لیست هتل
			h1.getRooms().add(r12); // افزودن اتاق 102
			h1.getRooms().add(r13); // افزودن اتاق 103
			h1.getRooms().add(r14); // افزودن سوئیت 201

			// ایجاد هتل دوم: هتل ساحل در کیش
			Hotel h2 = new Hotel(); // ایجاد هتل دوم
			h2.setName("هتل ساحل"); // نام هتل
			h2.setCity("کیش"); // شهر هتل
			h2.setAddress("بلوار دریا، خیابان ساحل"); // آدرس هتل

			// ایجاد اتاق 201 - سوئیت
			Room r21 = new Room(); // اتاق 201 هتل ساحل
			r21.setNumber("201"); // شماره اتاق
			r21.setType("Suite"); // نوع سوئیت
			r21.setCapacity(4); // ظرفیت چهار نفره
			r21.setPricePerNight(3500000); // قیمت هر شب
			r21.setHotel(h2); // نسبت به هتل دوم

			// ایجاد اتاق 202 - دو نفره
			Room r22 = new Room(); // اتاق 202
			r22.setNumber("202"); // شماره اتاق
			r22.setType("Double"); // نوع دو نفره
			r22.setCapacity(2); // ظرفیت
			r22.setPricePerNight(2200000); // قیمت
			r22.setHotel(h2); // اتصال به هتل ساحل

			// ایجاد اتاق 301 - سوئیت لوکس
			Room r23 = new Room(); // اتاق 301
			r23.setNumber("301"); // شماره اتاق
			r23.setType("Deluxe Suite"); // نوع سوئیت لوکس
			r23.setCapacity(6); // ظرفیت شش نفره
			r23.setPricePerNight(4500000); // قیمت هر شب
			r23.setHotel(h2); // انتساب به هتل دوم

			// اضافه کردن اتاق‌ها به هتل
			h2.getRooms().add(r21); // افزودن اتاق 201 به هتل ساحل
			h2.getRooms().add(r22); // افزودن اتاق 202
			h2.getRooms().add(r23); // افزودن سوئیت 301

			// ایجاد هتل سوم: هتل پارسیان در اصفهان
			Hotel h3 = new Hotel(); // ساخت هتل سوم
			h3.setName("هتل پارسیان"); // نام هتل
			h3.setCity("اصفهان"); // شهر محل هتل
			h3.setAddress("خیابان چهارباغ، میدان نقش جهان"); // آدرس دقیق

			// ایجاد اتاق‌های هتل پارسیان
			Room r31 = new Room(); // اتاق 101 پارسیان
			r31.setNumber("101"); // شماره اتاق
			r31.setType("Single"); // نوع تک‌نفره
			r31.setCapacity(1); // ظرفیت یک نفر
			r31.setPricePerNight(1000000); // قیمت
			r31.setHotel(h3); // انتساب به هتل سوم

			Room r32 = new Room(); // اتاق 102
			r32.setNumber("102"); // شماره اتاق
			r32.setType("Double"); // نوع دو نفره
			r32.setCapacity(2); // ظرفیت
			r32.setPricePerNight(1600000); // قیمت
			r32.setHotel(h3); // اتصال به هتل پارسیان

			Room r33 = new Room(); // اتاق 201
			r33.setNumber("201"); // شماره اتاق
			r33.setType("Suite"); // نوع سوئیت
			r33.setCapacity(4); // ظرفیت
			r33.setPricePerNight(2800000); // قیمت
			r33.setHotel(h3); // نسبت به هتل سوم

			Room r34 = new Room(); // اتاق 202
			r34.setNumber("202"); // شماره
			r34.setType("Double"); // نوع
			r34.setCapacity(2); // ظرفیت
			r34.setPricePerNight(1700000); // قیمت
			r34.setHotel(h3); // انتساب

			// اضافه کردن اتاق‌ها به هتل
			h3.getRooms().add(r31); // افزودن اتاق 101
			h3.getRooms().add(r32); // افزودن اتاق 102
			h3.getRooms().add(r33); // افزودن سوئیت 201
			h3.getRooms().add(r34); // افزودن اتاق 202

			// ایجاد هتل چهارم: هتل الماس در شیراز
			Hotel h4 = new Hotel(); // ساخت هتل چهارم
			h4.setName("هتل الماس"); // نام هتل
			h4.setCity("شیراز"); // شهر شیراز
			h4.setAddress("بلوار زند، خیابان حافظ"); // آدرس

			// ایجاد اتاق‌های هتل الماس
			Room r41 = new Room(); // اتاق 101
			r41.setNumber("101"); // شماره
			r41.setType("Single"); // نوع
			r41.setCapacity(1); // ظرفیت
			r41.setPricePerNight(950000); // قیمت
			r41.setHotel(h4); // اتصال به هتل الماس

			Room r42 = new Room(); // اتاق 102
			r42.setNumber("102"); // شماره
			r42.setType("Double"); // نوع
			r42.setCapacity(2); // ظرفیت
			r42.setPricePerNight(1500000); // قیمت
			r42.setHotel(h4); // نسبت به هتل چهارم

			Room r43 = new Room(); // اتاق 201
			r43.setNumber("201"); // شماره
			r43.setType("Suite"); // نوع سوئیت
			r43.setCapacity(4); // ظرفیت
			r43.setPricePerNight(2700000); // قیمت
			r43.setHotel(h4); // انتساب

			Room r44 = new Room(); // اتاق 301
			r44.setNumber("301"); // شماره
			r44.setType("Deluxe Suite"); // نوع سوئیت لوکس
			r44.setCapacity(5); // ظرفیت
			r44.setPricePerNight(3800000); // قیمت
			r44.setHotel(h4); // نسبت

			// اضافه کردن اتاق‌ها به هتل
			h4.getRooms().add(r41); // افزودن اتاق 101
			h4.getRooms().add(r42); // افزودن اتاق 102
			h4.getRooms().add(r43); // افزودن سوئیت 201
			h4.getRooms().add(r44); // افزودن سوئیت 301

			// ذخیره تمام هتل‌ها در دیتابیس
			hotelRepository.save(h1); // ذخیره هتل اول
			hotelRepository.save(h2); // ذخیره هتل دوم
			hotelRepository.save(h3); // ذخیره هتل سوم
			hotelRepository.save(h4); // ذخیره هتل چهارم

			// ایجاد هتل پنجم: هتل هما در مشهد
			Hotel h5 = new Hotel(); // نمونه‌سازی هتل پنجم
			h5.setName("هتل هما"); // تعیین نام
			h5.setCity("مشهد"); // تعیین شهر
			h5.setAddress("بلوار وکیل‌آباد، نزدیک حرم مطهر"); // ثبت آدرس

			// ایجاد اتاق‌های هتل هما
			Room r51 = new Room(); // اتاق 101
			r51.setNumber("101"); // شماره اتاق
			r51.setType("Single"); // نوع تک‌نفره
			r51.setCapacity(1); // ظرفیت
			r51.setPricePerNight(1100000); // قیمت
			r51.setHotel(h5); // انتساب به هتل پنج

			Room r52 = new Room(); // اتاق 102
			r52.setNumber("102"); // شماره اتاق
			r52.setType("Double"); // نوع
			r52.setCapacity(2); // ظرفیت
			r52.setPricePerNight(1700000); // قیمت
			r52.setHotel(h5); // انتساب

			Room r53 = new Room(); // اتاق 201
			r53.setNumber("201"); // شماره
			r53.setType("Double"); // نوع دو نفره
			r53.setCapacity(2); // ظرفیت
			r53.setPricePerNight(1850000); // قیمت
			r53.setHotel(h5); // اتصال

			Room r54 = new Room(); // اتاق 202
			r54.setNumber("202"); // شماره
			r54.setType("Suite"); // نوع سوئیت
			r54.setCapacity(4); // ظرفیت
			r54.setPricePerNight(3000000); // قیمت
			r54.setHotel(h5); // انتساب

			Room r55 = new Room(); // اتاق 301
			r55.setNumber("301"); // شماره
			r55.setType("Deluxe Suite"); // نوع سوئیت لوکس
			r55.setCapacity(6); // ظرفیت
			r55.setPricePerNight(4200000); // قیمت
			r55.setHotel(h5); // نسبت

			h5.getRooms().add(r51); // افزودن اتاق 101 به لیست هتل
			h5.getRooms().add(r52); // افزودن اتاق 102
			h5.getRooms().add(r53); // افزودن اتاق 201
			h5.getRooms().add(r54); // افزودن سوئیت 202
			h5.getRooms().add(r55); // افزودن سوئیت 301

			// ایجاد هتل ششم: هتل بادگیر در یزد
			Hotel h6 = new Hotel(); // ساخت هتل ششم
			h6.setName("هتل بادگیر"); // نام
			h6.setCity("یزد"); // شهر
			h6.setAddress("خیابان قیام، میدان امیرچخماق"); // آدرس

			// ایجاد اتاق‌های هتل بادگیر
			Room r61 = new Room(); // اتاق 101
			r61.setNumber("101"); // شماره
			r61.setType("Single"); // نوع
			r61.setCapacity(1); // ظرفیت
			r61.setPricePerNight(900000); // قیمت
			r61.setHotel(h6); // نسبت

			Room r62 = new Room(); // اتاق 102
			r62.setNumber("102"); // شماره
			r62.setType("Double"); // نوع
			r62.setCapacity(2); // ظرفیت
			r62.setPricePerNight(1400000); // قیمت
			r62.setHotel(h6); // اتصال

			Room r63 = new Room(); // اتاق 201
			r63.setNumber("201"); // شماره
			r63.setType("Double"); // نوع
			r63.setCapacity(2); // ظرفیت
			r63.setPricePerNight(1550000); // قیمت
			r63.setHotel(h6); // انتساب

			Room r64 = new Room(); // اتاق 202
			r64.setNumber("202"); // شماره
			r64.setType("Suite"); // نوع سوئیت
			r64.setCapacity(4); // ظرفیت
			r64.setPricePerNight(2600000); // قیمت
			r64.setHotel(h6); // اتصال

			h6.getRooms().add(r61); // افزودن اتاق 101
			h6.getRooms().add(r62); // افزودن اتاق 102
			h6.getRooms().add(r63); // افزودن اتاق 201
			h6.getRooms().add(r64); // افزودن سوئیت 202

			// ایجاد هتل هفتم: هتل لاله در تبریز
			Hotel h7 = new Hotel(); // ساخت هتل هفتم
			h7.setName("هتل لاله"); // نام هتل
			h7.setCity("تبریز"); // شهر
			h7.setAddress("خیابان امام خمینی، میدان ساعت"); // آدرس

			// ایجاد اتاق‌های هتل لاله
			Room r71 = new Room(); // اتاق 101
			r71.setNumber("101"); // شماره
			r71.setType("Single"); // نوع
			r71.setCapacity(1); // ظرفیت
			r71.setPricePerNight(1050000); // قیمت
			r71.setHotel(h7); // نسبت

			Room r72 = new Room(); // اتاق 102
			r72.setNumber("102"); // شماره
			r72.setType("Double"); // نوع
			r72.setCapacity(2); // ظرفیت
			r72.setPricePerNight(1650000); // قیمت
			r72.setHotel(h7); // اتصال

			Room r73 = new Room(); // اتاق 201
			r73.setNumber("201"); // شماره
			r73.setType("Double"); // نوع
			r73.setCapacity(2); // ظرفیت
			r73.setPricePerNight(1750000); // قیمت
			r73.setHotel(h7); // انتساب

			Room r74 = new Room(); // اتاق 202
			r74.setNumber("202"); // شماره
			r74.setType("Suite"); // نوع سوئیت
			r74.setCapacity(4); // ظرفیت
			r74.setPricePerNight(2900000); // قیمت
			r74.setHotel(h7); // انتساب

			Room r75 = new Room(); // اتاق 301
			r75.setNumber("301"); // شماره
			r75.setType("Deluxe Suite"); // نوع سوئیت لوکس
			r75.setCapacity(5); // ظرفیت
			r75.setPricePerNight(4000000); // قیمت
			r75.setHotel(h7); // ارتباط

			h7.getRooms().add(r71); // افزودن اتاق 101
			h7.getRooms().add(r72); // افزودن اتاق 102
			h7.getRooms().add(r73); // افزودن اتاق 201
			h7.getRooms().add(r74); // افزودن سوئیت 202
			h7.getRooms().add(r75); // افزودن سوئیت 301

			// اضافه کردن اتاق‌های بیشتر به هتل‌های موجود
			// هتل آرامش - اتاق‌های بیشتر
			Room r15 = new Room(); // اتاق جدید 202
			r15.setNumber("202"); // شماره
			r15.setType("Double"); // نوع
			r15.setCapacity(2); // ظرفیت
			r15.setPricePerNight(1950000); // قیمت
			r15.setHotel(h1); // انتساب به هتل آرامش

			Room r16 = new Room(); // اتاق 301
			r16.setNumber("301"); // شماره
			r16.setType("Suite"); // نوع سوئیت
			r16.setCapacity(4); // ظرفیت
			r16.setPricePerNight(3300000); // قیمت
			r16.setHotel(h1); // نسبت

			h1.getRooms().add(r15); // افزودن اتاق جدید به لیست
			h1.getRooms().add(r16); // افزودن سوئیت جدید

			// هتل ساحل - اتاق‌های بیشتر
			Room r24 = new Room(); // اتاق 101
			r24.setNumber("101"); // شماره
			r24.setType("Single"); // نوع
			r24.setCapacity(1); // ظرفیت
			r24.setPricePerNight(1300000); // قیمت
			r24.setHotel(h2); // نسبت

			Room r25 = new Room(); // اتاق 102
			r25.setNumber("102"); // شماره
			r25.setType("Double"); // نوع
			r25.setCapacity(2); // ظرفیت
			r25.setPricePerNight(2100000); // قیمت
			r25.setHotel(h2); // انتساب

			h2.getRooms().add(r24); // افزودن اتاق جدید به هتل ساحل
			h2.getRooms().add(r25); // افزودن اتاق دوم

			// ذخیره تمام هتل‌های جدید
			hotelRepository.save(h5); // ذخیره هتل پنجم
			hotelRepository.save(h6); // ذخیره هتل ششم
			hotelRepository.save(h7); // ذخیره هتل هفتم
			hotelRepository.save(h1); // ذخیره مجدد برای اتاق‌های جدید

			// ایجاد رزروهای نمونه برای نمایش در سیستم
			// رزرو نمونه 1: در هتل آرامش
			Reservation res1 = new Reservation(); // ساخت رزرو نمونه اول
			res1.setRoom(r11); // اتاق 101 هتل آرامش
			res1.setGuestName("علی احمدی"); // تنظیم نام مهمان رزرو 1
			res1.setGuestEmail("ali.ahmadi@example.com"); // تنظیم ایمیل مهمان رزرو 1
			res1.setStartDate(LocalDate.now().plusDays(5)); // 5 روز دیگر
			res1.setEndDate(LocalDate.now().plusDays(7)); // 7 روز دیگر
			reservationRepository.save(res1); // ذخیره رزرو 1

			// رزرو نمونه 2: در هتل ساحل
			Reservation res2 = new Reservation(); // ساخت رزرو دوم
			res2.setRoom(r21); // اتاق 201 هتل ساحل
			res2.setGuestName("فاطمه رضایی"); // نام مهمان رزرو 2
			res2.setGuestEmail("fateme.rezaei@example.com"); // ایمیل مهمان رزرو 2
			res2.setStartDate(LocalDate.now().plusDays(10)); // 10 روز دیگر
			res2.setEndDate(LocalDate.now().plusDays(12)); // 12 روز دیگر
			reservationRepository.save(res2); // ذخیره رزرو 2

			// رزرو نمونه 3: در هتل پارسیان
			Reservation res3 = new Reservation(); // ساخت رزرو سوم
			res3.setRoom(r32); // اتاق 102 هتل پارسیان
			res3.setGuestName("محمد کریمی"); // نام مهمان رزرو 3
			res3.setGuestEmail("mohammad.karimi@example.com"); // ایمیل مهمان رزرو 3
			res3.setStartDate(LocalDate.now().plusDays(15)); // 15 روز دیگر
			res3.setEndDate(LocalDate.now().plusDays(18)); // 18 روز دیگر
			reservationRepository.save(res3); // ذخیره رزرو 3

			// رزرو نمونه 4: در هتل الماس
			Reservation res4 = new Reservation(); // ساخت رزرو چهارم
			res4.setRoom(r42); // اتاق 102 هتل الماس
			res4.setGuestName("زهرا نوری"); // نام مهمان رزرو 4
			res4.setGuestEmail("zahra.noori@example.com"); // ایمیل مهمان رزرو 4
			res4.setStartDate(LocalDate.now().plusDays(20)); // 20 روز دیگر
			res4.setEndDate(LocalDate.now().plusDays(22)); // 22 روز دیگر
			reservationRepository.save(res4); // ذخیره رزرو 4

			// رزرو نمونه 5: در هتل هما
			Reservation res5 = new Reservation(); // ساخت رزرو پنجم
			res5.setRoom(r52); // اتاق 102 هتل هما
			res5.setGuestName("حسین موسوی"); // نام مهمان رزرو 5
			res5.setGuestEmail("hossein.mousavi@example.com"); // ایمیل مهمان رزرو 5
			res5.setStartDate(LocalDate.now().plusDays(25)); // 25 روز دیگر
			res5.setEndDate(LocalDate.now().plusDays(27)); // 27 روز دیگر
			reservationRepository.save(res5); // ذخیره رزرو 5
		};
	}
}


