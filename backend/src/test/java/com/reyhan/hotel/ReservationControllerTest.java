package com.reyhan.hotel; // پکیج تست‌ها

import org.junit.jupiter.api.Test; // فریم‌ورک تست JUnit 5
import org.springframework.beans.factory.annotation.Autowired; // تزریق وابستگی در تست
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // تنظیم خودکار MockMvc
import org.springframework.boot.test.context.SpringBootTest; // بارگذاری کانتکست کامل
import org.springframework.http.MediaType; // مقداردهی نوع محتوا
import org.springframework.test.context.ActiveProfiles; // انتخاب پروفایل تست
import org.springframework.test.web.servlet.MockMvc; // ابزار شبیه‌سازی درخواست HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // ساخت درخواست GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // ساخت درخواست POST
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // اعتبارسنجی وضعیت پاسخ

/**
 * کلاس تست برای کنترلر رزرواسیون
 * این کلاس تست‌های واحد برای API رزرواسیون را انجام می‌دهد
 */
@SpringBootTest // اجرای تست با کل کانتکست Spring
@AutoConfigureMockMvc // پیکربندی خودکار MockMvc برای تست‌های HTTP
@ActiveProfiles("test") // استفاده از پروفایل test برای تنظیمات تست
class ReservationControllerTest { // کلاس تست کنترلر رزرو

	/**
	 * MockMvc برای شبیه‌سازی درخواست‌های HTTP در تست‌ها
	 */
	@Autowired // تزریق MockMvc
	private MockMvc mockMvc; // شبیه‌ساز درخواست HTTP

	/**
	 * تست دریافت لیست هتل‌ها
	 * این تست بررسی می‌کند که endpoint دریافت لیست هتل‌ها با موفقیت پاسخ می‌دهد
	 */
	@Test // نشانه‌گذاری متد تست
	void hotelsListShouldReturnOk() throws Exception { // سناریوی تست لیست هتل‌ها
		mockMvc.perform(get("/api/hotels")) // ارسال درخواست GET
			.andExpect(status().isOk()); // انتظار کد وضعیت 200 (OK)
	}

	/**
	 * تست ایجاد رزرو و بررسی تداخل تاریخ
	 * این تست بررسی می‌کند که:
	 * 1. ایجاد یک رزرو با موفقیت انجام می‌شود
	 * 2. ایجاد رزرو دوم با تاریخ‌های تداخل‌دار با خطا مواجه می‌شود (کد 409 Conflict)
	 */
	@Test // نشانه‌گذاری متد تست دوم
	void createReservation_thenConflictOnOverlap() throws Exception { // سناریوی ایجاد رزرو و تداخل
		mockMvc.perform(post("/api/reservations") // درخواست ایجاد رزرو اول
				.contentType(MediaType.APPLICATION_JSON) // تعیین نوع محتوا JSON
				.content("{\"roomId\":1,\"guestName\":\"Test\",\"guestEmail\":\"t@t.com\",\"startDate\":\"2030-01-01\",\"endDate\":\"2030-01-03\"}")) // بدنه درخواست
			.andExpect(status().isCreated()); // انتظار کد وضعیت 201 (Created)

		mockMvc.perform(post("/api/reservations") // درخواست رزرو تداخل‌دار
				.contentType(MediaType.APPLICATION_JSON) // نوع محتوا JSON
				.content("{\"roomId\":1,\"guestName\":\"Test2\",\"guestEmail\":\"t2@t.com\",\"startDate\":\"2030-01-02\",\"endDate\":\"2030-01-04\"}")) // بدنه با تاریخ‌های متداخل
			.andExpect(status().isConflict()); // انتظار کد وضعیت 409 (Conflict)
	}
}

