package com.reyhan.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * کلاس تست برای کنترلر رزرواسیون
 * این کلاس تست‌های واحد برای API رزرواسیون را انجام می‌دهد
 */
@SpringBootTest
@AutoConfigureMockMvc // پیکربندی خودکار MockMvc برای تست‌های HTTP
@ActiveProfiles("test") // استفاده از پروفایل test برای تنظیمات تست
class ReservationControllerTest {

	/**
	 * MockMvc برای شبیه‌سازی درخواست‌های HTTP در تست‌ها
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * تست دریافت لیست هتل‌ها
	 * این تست بررسی می‌کند که endpoint دریافت لیست هتل‌ها با موفقیت پاسخ می‌دهد
	 */
	@Test
	void hotelsListShouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/hotels"))
			.andExpect(status().isOk()); // انتظار کد وضعیت 200 (OK)
	}

	/**
	 * تست ایجاد رزرو و بررسی تداخل تاریخ
	 * این تست بررسی می‌کند که:
	 * 1. ایجاد یک رزرو با موفقیت انجام می‌شود
	 * 2. ایجاد رزرو دوم با تاریخ‌های تداخل‌دار با خطا مواجه می‌شود (کد 409 Conflict)
	 */
	@Test
	void createReservation_thenConflictOnOverlap() throws Exception {
		// ایجاد یک رزرو برای اتاق با شناسه 1 در تاریخ‌های آینده
		mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"roomId\":1,\"guestName\":\"Test\",\"guestEmail\":\"t@t.com\",\"startDate\":\"2030-01-01\",\"endDate\":\"2030-01-03\"}"))
			.andExpect(status().isCreated()); // انتظار کد وضعیت 201 (Created)

		// رزرو تداخل‌دار باید با خطا مواجه شود (کد 409 Conflict)
		mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"roomId\":1,\"guestName\":\"Test2\",\"guestEmail\":\"t2@t.com\",\"startDate\":\"2030-01-02\",\"endDate\":\"2030-01-04\"}"))
			.andExpect(status().isConflict()); // انتظار کد وضعیت 409 (Conflict)
	}
}

