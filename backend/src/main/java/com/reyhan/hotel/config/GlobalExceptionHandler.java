package com.reyhan.hotel.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * کلاس مدیریت خطاهای سراسری
 * این کلاس تمام خطاهای برنامه را مدیریت می‌کند و پاسخ مناسب را به کلاینت برمی‌گرداند
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * مدیریت خطاهای IllegalArgumentException
	 * این خطاها معمولاً به دلیل ورودی نامعتبر رخ می‌دهند
	 * 
	 * @param ex استثنای رخ داده
	 * @return پاسخ HTTP با کد وضعیت 400 (Bad Request)
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	/**
	 * مدیریت خطاهای IllegalStateException
	 * این خطاها معمولاً به دلیل وضعیت نامعتبر سیستم رخ می‌دهند (مثلاً اتاق موجود نیست)
	 * 
	 * @param ex استثنای رخ داده
	 * @return پاسخ HTTP با کد وضعیت 409 (Conflict)
	 */
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}

	/**
	 * مدیریت خطاهای اعتبارسنجی (Validation)
	 * این خطاها زمانی رخ می‌دهند که داده‌های ورودی معیارهای اعتبارسنجی را برآورده نمی‌کنند
	 * 
	 * @param ex استثنای اعتبارسنجی
	 * @return پاسخ HTTP با کد وضعیت 400 (Bad Request) و جزئیات خطاهای اعتبارسنجی
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", "Validation failed");
		// استخراج جزئیات خطاهای اعتبارسنجی از تمام فیلدها
		body.put("details", ex.getBindingResult().getFieldErrors()
			.stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).toArray());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
}


