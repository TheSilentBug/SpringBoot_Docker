package com.reyhan.hotel.config; // تعریف پکیج پیکربندی

import org.springframework.context.annotation.Bean; // برای تعریف Bean در کانتکست Spring
import org.springframework.context.annotation.Configuration; // مشخص می‌کند کلاس یک پیکربندی است

import io.swagger.v3.oas.models.ExternalDocumentation; // مدل برای لینک مستندات خارجی
import io.swagger.v3.oas.models.OpenAPI; // شیء اصلی OpenAPI
import io.swagger.v3.oas.models.info.Info; // مدل اطلاعات متا
import io.swagger.v3.oas.models.info.License; // مدل اطلاعات لایسنس

/**
 * کلاس پیکربندی OpenAPI/Swagger
 * این کلاس تنظیمات مستندات API را برای نمایش در Swagger UI تعریف می‌کند
 */
@Configuration // ثبت کلاس به‌عنوان پیکربندی Spring
public class OpenApiConfig { // تعریف کلاس تنظیمات OpenAPI
	/**
	 * ایجاد و پیکربندی مستندات OpenAPI برای API رزرواسیون هتل
	 * این متد اطلاعات API را برای نمایش در Swagger UI تنظیم می‌کند
	 * 
	 * @return شیء OpenAPI با اطلاعات مستندات
	 */
	@Bean // تعریف Bean تا در کانتکست موجود باشد
	public OpenAPI hotelReservationOpenAPI() { // متد سازنده شیء OpenAPI
		return new OpenAPI() // ایجاد نمونه OpenAPI
			.info(new Info() // تعیین اطلاعات عمومی API
				.title("Hotel Reservation API") // عنوان مستندات
				.description("سامانه رزرواسیون هتل - API") // توضیح کوتاه API
				.version("v1.0.0") // نسخه فعلی API
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))) // افزودن اطلاعات لایسنس
			.externalDocs(new ExternalDocumentation() // تعریف لینک مستندات خارجی
				.description("Documentation") // توضیح لینک
				.url("https://example.com/docs")); // آدرس URL مستندات خارجی
	}
}


