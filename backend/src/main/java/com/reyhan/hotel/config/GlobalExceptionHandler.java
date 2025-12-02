package com.reyhan.hotel.config; // تعریف پکیج مربوط به تنظیمات سراسری

import java.util.HashMap; // پیاده‌سازی Map برای ذخیره پیام خطا
import java.util.Map; // رابط Map برای نگه‌داری داده‌های پاسخ

import org.springframework.http.HttpStatus; // وضعیت‌های استاندارد HTTP
import org.springframework.http.ResponseEntity; // ساخت پاسخ HTTP همراه بدنه
import org.springframework.web.bind.MethodArgumentNotValidException; // استثنای اعتبارسنجی ورودی
import org.springframework.web.bind.annotation.ControllerAdvice; // تعریف هندلر سراسری خطا
import org.springframework.web.bind.annotation.ExceptionHandler; // مشخص کردن متد برای مدیریت خطا

/**
 * کلاس مدیریت خطاهای سراسری
 * این کلاس تمام خطاهای برنامه را مدیریت می‌کند و پاسخ مناسب را به کلاینت برمی‌گرداند
 */
@ControllerAdvice // ثبت کلاس برای مدیریت خطاهای کل برنامه
public class GlobalExceptionHandler { // کلاس مسئول هندل کردن خطاها
	/**
	 * مدیریت خطاهای IllegalArgumentException
	 * این خطاها معمولاً به دلیل ورودی نامعتبر رخ می‌دهند
	 * 
	 * @param ex استثنای رخ داده
	 * @return پاسخ HTTP با کد وضعیت 400 (Bad Request)
	 */
	@ExceptionHandler(IllegalArgumentException.class) // متد برای خطاهای ورودی نامعتبر
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) { // ساخت پاسخ HTTP
		Map<String, Object> body = new HashMap<>(); // بدنه پاسخ به صورت Map
		body.put("error", ex.getMessage()); // قرار دادن پیام خطا در بدنه
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // بازگرداندن وضعیت 400
	}

	/**
	 * مدیریت خطاهای IllegalStateException
	 * این خطاها معمولاً به دلیل وضعیت نامعتبر سیستم رخ می‌دهند (مثلاً اتاق موجود نیست)
	 * 
	 * @param ex استثنای رخ داده
	 * @return پاسخ HTTP با کد وضعیت 409 (Conflict)
	 */
	@ExceptionHandler(IllegalStateException.class) // متد برای وضعیت‌های نامعتبر
	public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException ex) { // پاسخ برای Conflict
		Map<String, Object> body = new HashMap<>(); // ساخت بدنه خطا
		body.put("error", ex.getMessage()); // ثبت پیام خطا
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // بازگرداندن وضعیت 409
	}

	/**
	 * مدیریت خطاهای اعتبارسنجی (Validation)
	 * این خطاها زمانی رخ می‌دهند که داده‌های ورودی معیارهای اعتبارسنجی را برآورده نمی‌کنند
	 * 
	 * @param ex استثنای اعتبارسنجی
	 * @return پاسخ HTTP با کد وضعیت 400 (Bad Request) و جزئیات خطاهای اعتبارسنجی
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class) // متد برای خطاهای اعتبارسنجی
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) { // پاسخ خطای ولیدیشن
		Map<String, Object> body = new HashMap<>(); // بدنه برای ذخیره پیام‌ها
		body.put("error", "Validation failed"); // پیام کلی خطا
		body.put("details", ex.getBindingResult().getFieldErrors() // استخراج لیست خطاهای فیلد
			.stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).toArray()); // تبدیل به آرایه توضیحات
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // بازگرداندن وضعیت 400
	}
}


